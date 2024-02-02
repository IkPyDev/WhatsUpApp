package com.ikpydev.presentation.screens.group

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.devlomi.record_view.OnRecordListener
import com.github.terrakok.cicerone.Router
import com.ikpydev.domain.model.GroupChat
import com.ikpydev.domain.model.MessageGroup
import com.ikpydev.presentation.R
import com.ikpydev.presentation.base.BaseFragment
import com.ikpydev.presentation.databinding.FragmentChatBinding
import com.ikpydev.presentation.databinding.FragmentGroupBinding
import com.ikpydev.presentation.screens.chat.ChatViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.InputStream


class GroupFragment(
    private val group: GroupChat
) :
    BaseFragment<FragmentGroupBinding>(FragmentGroupBinding::inflate) {
        private val viewModel:GroupViewModel by viewModel()

        private lateinit var adapter: GroupAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.processInput(GroupViewModel.Input.SendGroupChat(group))
        viewModel.mediaRecorderInit()
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        initUi()


    }
    private fun observeViewModel() {


        viewModel.state.observe(::renderError) { it.error }
        viewModel.state.observe(::renderGroup) { it.messagesGroup }
        viewModel.state.observe(::renderLoading) { it.loading }
    }

    private fun renderLoading(loading: Boolean) {
        Log.d("TAG", "renderLoading: $loading ")
    }

    private fun renderGroup(groupChat: List<MessageGroup>) {
        adapter.submitList(groupChat)

    }

    private fun renderError(error: Boolean) {
        Log.d("TAG", "renderError: $error ")
    }

    private fun initUi()=with(binding) {
        adapter = GroupAdapter()
        messages.adapter = adapter

        val icon = group.group.avatar ?: R.drawable.ic_groups_24
        Glide.with(root).load(icon).apply(RequestOptions.circleCropTransform()).into(avatar)
        name.text = group.group.name

        back.setOnClickListener {

        }


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
                viewModel.processInput(GroupViewModel.Input.SendMessage(message.text.toString()))
                message.text = null

            }

        }
    }

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            it ?: return@registerForActivityResult
            val stream: InputStream = requireActivity().contentResolver.openInputStream(it)
                ?: return@registerForActivityResult
            viewModel.processInput(GroupViewModel.Input.SendImage(it, stream))
        }

    private fun b(): Boolean {
        val recordPermissionAvailable =
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED
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

    private fun FragmentGroupBinding.funRecordBinding(state: Boolean) {
        gallery.isVisible = state
        message.isVisible = state
        recordView.isVisible = state.not()
    }




}