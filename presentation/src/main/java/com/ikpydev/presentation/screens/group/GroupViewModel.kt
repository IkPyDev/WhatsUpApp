package com.ikpydev.presentation.screens.group

import android.app.Activity
import android.media.MediaRecorder
import android.os.Environment
import android.util.Log
import com.ikpydev.domain.model.Chat
import com.ikpydev.domain.model.GroupChat
import com.ikpydev.domain.model.MessageGroup
import com.ikpydev.domain.usecase.group.GetGroupsMessagesUseCase
import com.ikpydev.domain.usecase.group.SendGroupsMessagesUseCase
import com.ikpydev.presentation.base.BaseViewModel
import com.ikpydev.presentation.screens.group.GroupViewModel.*
import java.io.File
import java.util.Date

class GroupViewModel(
    private val getGroupsMessagesUseCase: GetGroupsMessagesUseCase,
    private val sendGroupsMessagesUseCase: SendGroupsMessagesUseCase
):BaseViewModel<State,Input,Effect>() {

    private var mediaRecord: MediaRecorder? = null
    private var audioPath: String = ""
    private var isRecordingPaused: Boolean = false

    data class State(
        val messagesGroup: List<MessageGroup> = emptyList(),
        val loading: Boolean = false,
        val groupsChat: GroupChat? = null,
        val error:Boolean = false
    )

    sealed class Input {
        object GetMessage : Input()
        data class SendMessage(val message: String) : Input()
        data class SendGroupChat(val groupsChat: GroupChat):Input()
//        data class SendImage(val image: Uri, val stream: InputStream) : Input()
//        data class SendVoice(val voice: Uri, val stream: InputStream) : Input()
        data class SetGroupChat(val groupsChat: GroupChat) : Input()
    }

    sealed class Effect {
        object ErrorSending : Effect()
        object ErrorGetting : Effect()
    }
    override fun processInput(input: Input) {
        when (input) {
            Input.GetMessage -> getMessage()
            is Input.SendMessage -> senMessage(input.message)
            is Input.SetGroupChat -> setUser(input.groupsChat)
//            is Input.SendImage -> sendImage(input.image, input.stream)
//            is Input.SendVoice -> sendVoice(input.voice, input.stream)
            is Input.SendGroupChat -> setGroupChat(input.groupsChat)
        }
    }

    private fun senMessage(message: String) = sendGroupsMessagesUseCase.invoke(current.groupsChat!!,message)
        .subscribe({},{
            Log.d("TAG", "senMessage: ${it.message} ")
             emitEffects(Effect.ErrorSending)
        })

    private fun setGroupChat(groupChat: GroupChat) {
        updateState { it.copy(groupsChat = groupChat) }
        Log.d("TAG", "setGroupChat: $groupChat ")
        getMessage()
    }

    private fun getMessage()=getGroupsMessagesUseCase.invoke(current.groupsChat!!.group._id!!)
        .doOnSubscribe {
            updateState { it.copy(loading = true, error = false) }
        }
        .doOnNext { messagesGroup ->
            updateState { it.copy(messagesGroup = messagesGroup) }
        }
        .doOnEach {
            updateState { it.copy(loading = false, error = false) }
        }
        .subscribe({}, {e ->
            Log.d("TAG", "getMessage: ${e.message} ")
                       updateState { it.copy(loading = false, error = true) }
        }, {
            updateState { it.copy(loading = false, error = false) }
        })


    override fun getDefaultState() = State()

    private fun setUser(groupsChat: GroupChat) {
        updateState { it.copy(groupsChat = groupsChat) }
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
//            sendVoiceMes(File(audioPath).toUri(), requireActivity)

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
}