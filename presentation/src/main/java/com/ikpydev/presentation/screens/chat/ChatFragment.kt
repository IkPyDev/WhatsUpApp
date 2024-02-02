package com.ikpydev.presentation.screens.chat

import android.Manifest
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.devlomi.record_view.OnRecordListener
import com.ikpydev.domain.model.Chat
import com.ikpydev.domain.model.Message
import com.ikpydev.presentation.base.BaseFragment
import com.ikpydev.presentation.databinding.FragmentChatBinding
import com.ikpydev.presentation.screens.chat.ChatViewModel.Input
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.InputStream


class ChatFragment(
    private val chat: Chat
) : BaseFragment<FragmentChatBinding>(FragmentChatBinding::inflate) {
    private val viewModel: ChatViewModel by viewModel()
    private val adapter = ChatAdapter()
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

    private fun renderLoading(loading: Boolean) = with(binding) {

        Log.d("TAG", "renderLoading: $loading")

    }



    private fun renderMessage(messages: List<Message>) {
        adapter.submitList(messages)
    }

    private fun initUi() = with(binding) {

        Glide.with(root).load(chat.user.avatar).apply(RequestOptions.circleCropTransform())
            .into(avatar)
        name.text = chat.user.name
        messages.adapter = adapter
        phone.text = chat.user.phone

        recordButton.setRecordView(recordView)

        recordButton.setScaleUpTo(1f)
        recordView.setSoundEnabled(false)





        recordView.setOnRecordListener(object : OnRecordListener {

            override fun onStart() {
                if (b().not()){
                    ActivityCompat.requestPermissions(
                        requireActivity(), arrayOf(
                            Manifest.permission.RECORD_AUDIO,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ),
                        0
                    )
                }else {
                    funRecordBinding(false)
                    viewModel.mediaRecordStart()
                }
            }

            override fun onCancel() {
                funRecordBinding(true)
                viewModel.mediaRecorderCanceled()
            }

            override fun onFinish(recordTime: Long, limitReached: Boolean) {
                funRecordBinding(true)

                viewModel.mediaRecordStop(requireActivity())
            }

            override fun onLessThanSecond() {
                funRecordBinding(true)

                viewModel.mediaRecorderCanceled()
            }

            override fun onLock() {
                //
            }
        })




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
                    send.isVisible = false
                    recordButton.isVisible = true

                } else {
                    send.isVisible = true
                    recordButton.isVisible = false
                }

            }
        })


        send.setOnClickListener {


            if (message.text.isBlank().not() && message.text.isEmpty().not()) {
                viewModel.processInput(Input.SendMessage(message.text.toString()))
                message.text = null

            }

        }


    }


    private fun FragmentChatBinding.funRecordBinding(state: Boolean) {
        gallery.isVisible = state
        message.isVisible = state
        recordView.isVisible = state.not()
    }


    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            it ?: return@registerForActivityResult
            val stream: InputStream = requireActivity().contentResolver.openInputStream(it)
                ?: return@registerForActivityResult
            viewModel.processInput(Input.SendImage(it, stream))
        }

    private fun b(): Boolean {
        val recordPermissionAvailable =
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.RECORD_AUDIO
            ) == PERMISSION_GRANTED
        return recordPermissionAvailable
    }

    private fun permissionRequest() {
        ActivityCompat.requestPermissions(
            requireActivity(), arrayOf(
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ),
            0
        )
    }

    override fun onDestroyView() {
        viewModel.mediaRecorderDestroy()
        super.onDestroyView()
    }
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