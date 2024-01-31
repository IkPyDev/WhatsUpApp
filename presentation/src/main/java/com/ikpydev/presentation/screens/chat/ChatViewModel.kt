package com.ikpydev.presentation.screens.chat

import android.app.Activity
import android.content.Context
import android.media.MediaRecorder
import android.net.Uri
import android.os.Environment
import androidx.core.net.toUri
import com.ikpydev.domain.model.Chat
import com.ikpydev.domain.model.Message
import com.ikpydev.domain.model.Type
import com.ikpydev.domain.usecase.chat.GetMessageUseCase
import com.ikpydev.domain.usecase.chat.SendImageUseCase
import com.ikpydev.domain.usecase.chat.SendMessageUseCase
import com.ikpydev.domain.usecase.chat.SendVoiceUseCase
import com.ikpydev.presentation.base.BaseViewModel
import com.ikpydev.presentation.screens.chat.ChatViewModel.Effect
import com.ikpydev.presentation.screens.chat.ChatViewModel.Input
import com.ikpydev.presentation.screens.chat.ChatViewModel.State
import java.io.File
import java.io.InputStream
import java.util.Date


class ChatViewModel(
    private val getMessageUseCase: GetMessageUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val sendVoiceUseCase: SendVoiceUseCase,
    private val sendImageUseCase: SendImageUseCase
) : BaseViewModel<State, Input, Effect>() {

    private var mediaRecord: MediaRecorder? = null
    private var audioPath: String = ""
    private var isRecordingPaused: Boolean = false


    data class State(
        val messages: List<Message> = emptyList(),
        val loading: Boolean = false,
        val chat: Chat? = null
    )

    sealed class Input {
        object GetMessage : Input()
        data class SendMessage(val message: String) : Input()
        data class SendImage(val image: Uri, val stream: InputStream) : Input()
        data class SendVoice(val voice: Uri, val stream: InputStream) : Input()
        data class SetChat(val chat: Chat) : Input()
    }

    sealed class Effect {
        object ErrorSending : Effect()
        object ErrorGetting : Effect()
    }

    override fun getDefaultState() = State()
    private fun getMessage() = getMessageUseCase(current.chat!!.user.id)
        .doOnSubscribe {
            updateState { it.copy(loading = true) }
        }
        .doOnNext { message ->
            updateState { it.copy(messages = message) }
        }
        .doOnEach {
            updateState { it.copy(loading = false) }
        }
        .subscribe({}, {
            emitEffects(Effect.ErrorGetting)
        }, {
            updateState { it.copy(loading = false) }
        })

    private fun senMessage(message: String) = sendMessageUseCase(current.chat!!.user, message)
        .subscribe({}, {
            emitEffects(Effect.ErrorSending)
        })

    override fun processInput(input: Input) {
        when (input) {
            Input.GetMessage -> getMessage()
            is Input.SendMessage -> senMessage(input.message)
            is Input.SetChat -> setUser(input.chat)
            is Input.SendImage -> sendImage(input.image, input.stream)
            is Input.SendVoice -> sendVoice(input.voice, input.stream)
        }
    }

    private fun sendImage(image: Uri, stream: InputStream) {

        val message =
            Message(id = image.toString(), time = Date(), type = Type.image_upload, image = image)
        val messages = current.messages.toMutableList()
        messages.add(message)
        updateState { it.copy(messages = messages) }
            sendImageUseCase(current.chat!!.user, stream)
                .subscribe({}, {})

    }

    private fun sendVoice(voice: Uri, stream: InputStream) {

        val message =
            Message(id = voice.toString(), time = Date(), type = Type.voice_upload, voice = voice)
        val messages = current.messages.toMutableList()
        messages.add(message)
        updateState { it.copy(messages = messages) }

            sendVoiceUseCase(current.chat!!.user, stream)
                .subscribe({}, {})

    }

    private fun setUser(chat: Chat) {
        updateState { it.copy(chat = chat) }
        getMessage()
    }

    fun mediaRecorderInit() {
        audioPath =
            Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS
            ).absolutePath + "/temp-" + Date().time + ".mp3"
        mediaRecord = MediaRecorder()
        mediaRecord?.setOutputFile(audioPath)
    }

    fun mediaRecordStart() {
        try {
            mediaRecord = MediaRecorder()
            mediaRecord?.setOutputFile(audioPath)
            mediaRecord?.setAudioSource(MediaRecorder.AudioSource.MIC)
            mediaRecord?.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            mediaRecord?.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            mediaRecord?.prepare()
            mediaRecord?.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun mediaRecordStop(requireActivity: Activity) {
        try {
            mediaRecord?.stop()
            mediaRecord?.reset()
            mediaRecord?.release()
            sendVoiceMes(File(audioPath).toUri(), requireActivity)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun mediaRecorderPause() {
        try {
            mediaRecord?.pause()
            isRecordingPaused = true
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun mediaRecorderPlay() {
        try {
            if (isRecordingPaused)
                mediaRecord?.resume()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun mediaRecorderCanceled() {
        try {
            mediaRecord?.reset()
            File(audioPath).delete()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun mediaRecorderDestroy() {
        mediaRecord?.release()
        mediaRecord = null
    }

    private fun sendVoiceMes(uri: Uri?, context: Context) {
        uri ?: return
        val stream: InputStream = context.contentResolver.openInputStream(uri) ?: return
        processInput(Input.SendVoice(uri, stream))
    }


}