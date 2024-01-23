package com.ikpydev.presentation.screens.chat

import android.Manifest
import android.annotation.SuppressLint
import android.media.MediaRecorder
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.ikpydev.domain.model.Chat
import com.ikpydev.domain.model.Message
import com.ikpydev.presentation.R
import com.ikpydev.presentation.base.BaseFragment
import com.ikpydev.presentation.databinding.FragmentChatBinding
import com.ikpydev.presentation.screens.chat.ChatViewModel.Input
import com.ikpydev.presentation.utils.showAlert
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.coroutines.delay
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.InputStream
import java.util.Date

class ChatFragment(
    private val chat: Chat
) : BaseFragment<FragmentChatBinding>(FragmentChatBinding::inflate) {
    private val viewModel: ChatViewModel by viewModel()
    private val adapter = ChatAdapter()
    private var audioPath: String = ""
    private var mediaState: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.processInput(Input.SetChat(chat))

        viewModel.mediaRecorderInit()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()



        viewModel.state.observe(::renderMessage) { it.messages }

        viewModel.state.observe(::renderLoading) { it.loading }
    }

    private fun renderLoading(loading: Boolean)= with(binding) {


        progress.root.isVisible = loading

    }

//    private fun setUpRecord() {
//        try {
//            mediaRecord = MediaRecorder()
//            mediaRecord.setAudioSource(MediaRecorder.AudioSource.MIC)
//
//            mediaRecord.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
//            mediaRecord.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
//
//            val file = File(
//                Environment.getExternalStorageDirectory().absoluteFile,
//                "Messenger/Media/Recording"
//            )
//            if (!file.exists()) {
//                file.mkdirs()
//            }
//            audioPath = file.absolutePath + File.separator + System.currentTimeMillis() + ".3gp"
//            mediaRecord.setOutputFile(audioPath)
//
//            // Prepare and start recording
//            mediaRecord.prepare()
//            mediaRecord.start()
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//    }

//    private fun setUpRecord() {
//        mediaRecord = MediaRecorder()
//        mediaRecord.setAudioSource(MediaRecorder.AudioSource.MIC)
//        mediaRecord.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
//        mediaRecord.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
//
//        val file = File(
//            Environment.getExternalStorageDirectory().absoluteFile,
//            "Messenger/Media/Recording"
//        )
//        if (!file.exists()) {
//            file.mkdir()
//        }
//        audioPath = file.absolutePath + File.separator + System.currentTimeMillis() + "3gp"
//        mediaRecord.setOutputFile(audioPath)
//    }




    private fun renderMessage(messages: List<Message>) {
        adapter.submitList(messages)
    }

    @SuppressLint("ResourceType")
    private fun initUi() = with(binding) {

        Glide.with(root).load(chat.user.avatar).into(avatar)
        name.text = chat.user.name
        messages.adapter = adapter
        phone.text = chat.user.phone



        gallery.setOnClickListener {
            galleryLauncher.launch("image/*")
        }

        message.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

                if (message.text.toString().isEmpty()) {
                    send.setImageResource(R.drawable.ic_audio)
                    send.tag = R.drawable.ic_audio
                } else {
                    send.setImageResource(R.drawable.ic_send)
                    send.tag = R.drawable.ic_send
                }

            }
        })

        send.tag = R.drawable.ic_audio
        send.setOnClickListener {
            if (send.tag == R.drawable.ic_audio) {


                Dexter.withContext(context).withPermissions(
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ).withListener(object : MultiplePermissionsListener {
                    @SuppressLint("ResourceType")
                    override fun onPermissionsChecked(permission: MultiplePermissionsReport?) {
                        if (permission?.areAllPermissionsGranted() == true) {

                            try {
                                send.tag = R.drawable.ic_stop
                                send.setImageResource(R.drawable.ic_stop)

                                send.backgroundTintList =
                                    resources.getColorStateList(R.drawable.stop_recoding)

                                viewModel.mediaRecordStart()
                                message.isEnabled = false
                                this@ChatFragment.mediaState = true


                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        } else {
                            showAlert(
                                requireContext(),
                                "Permission denied",
                                "Kindly allow access to all required permission"
                            )
                        }

                    }


                    override fun onPermissionRationaleShouldBeShown(
                        p0: MutableList<PermissionRequest>?,
                        p1: PermissionToken?
                    ) {

                    }


                }).check()
            } else if (send.tag == R.drawable.ic_stop) {

                if (mediaState) {
                    mediaState = false
                    message.isEnabled = true
                    viewModel.mediaRecordStop(requireActivity())
                    send.tag = R.drawable.ic_audio
                    send.setImageResource(R.drawable.ic_audio)
                    send.backgroundTintList =
                        resources.getColorStateList(R.drawable.stop_recoding_defuld)

                }


            } else if (send.tag == R.drawable.ic_send) {
                if (message.text.isBlank().not() && message.text.isEmpty().not()) {
                    viewModel.processInput(Input.SendMessage(message.text.toString()))
                    message.text = null

                }

            }

        }

    }


    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            it ?: return@registerForActivityResult
            val stream: InputStream = requireActivity().contentResolver.openInputStream(it)
                ?: return@registerForActivityResult
            viewModel.processInput(Input.SendImage(it, stream))
        }
}