package com.ikpydev.presentation.screens.group

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ikpydev.domain.model.MessageGroup
import com.ikpydev.domain.model.Type
import com.ikpydev.domain.model.TypeGroup
import com.ikpydev.presentation.R
import com.ikpydev.presentation.databinding.ItemImageInChatBinding
import com.ikpydev.presentation.databinding.ItemImageOutChatBinding
import com.ikpydev.presentation.databinding.ItemTextInChatBinding
import com.ikpydev.presentation.databinding.ItemTextInGroupBinding
import com.ikpydev.presentation.databinding.ItemTextOutChatBinding
import com.ikpydev.presentation.databinding.ItemVoiceInChatBinding
import com.ikpydev.presentation.databinding.ItemVoiceOutChatBinding

class GroupAdapter : ListAdapter<MessageGroup, RecyclerView.ViewHolder>(DIFF_UTIL) {

    inner class GroupTextInViewHolder(private val binding: ItemTextInGroupBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: MessageGroup) = with(binding) {
            text.text = message.message
            val avatar = message.avatar ?: R.drawable.ic_groups_24
            Glide.with(root).load(avatar).into(binding.avatar)

        }
    }

    inner class TextOutViewHolder(private val binding: ItemTextOutChatBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: MessageGroup) {
            binding.text.text = message.message
        }
    }

    inner class ImageUploadViewHolder(private val binding: ItemImageOutChatBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: MessageGroup) = with(binding) {
            Glide.with(root).load(message.image).into(image)
        }
    }

    inner class VoiceOutViewHolder(private val binding: ItemVoiceOutChatBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: MessageGroup) = with(binding) {
            voicePlayerView.setAudio(message.voice.toString())
        }
    }

    inner class VoiceInViewHolder(private val binding: ItemVoiceInChatBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: MessageGroup) = with(binding) {
            voicePlayerView.setAudio(message.voice.toString())
        }
    }

    inner class ImageInViewHolder(private val binding: ItemImageInChatBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: MessageGroup) = with(binding) {
            Glide.with(root).load(message.image).into(image)
        }
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<MessageGroup>() {
            override fun areItemsTheSame(oldItem: MessageGroup, newItem: MessageGroup) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: MessageGroup, newItem: MessageGroup): Boolean {
                return oldItem.message == newItem.message
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (TypeGroup.values()[viewType]) {
            TypeGroup.text_in -> GroupTextInViewHolder(
                ItemTextInGroupBinding.inflate(inflater, parent, false)
            )

            TypeGroup.text_out -> TextOutViewHolder(
                ItemTextOutChatBinding.inflate(inflater, parent, false)
            )

            TypeGroup.image_upload -> ImageUploadViewHolder(
                ItemImageOutChatBinding.inflate(inflater, parent, false)
            )

            TypeGroup.image_in -> ImageInViewHolder(
                ItemImageInChatBinding.inflate(inflater, parent, false)
            )

            TypeGroup.image_out -> ImageUploadViewHolder(
                ItemImageOutChatBinding.inflate(inflater, parent, false)
            )

            TypeGroup.voice_upload -> VoiceOutViewHolder(
                ItemVoiceOutChatBinding.inflate(inflater, parent, false)
            )

            TypeGroup.voice_in -> VoiceInViewHolder(
                ItemVoiceInChatBinding.inflate(inflater, parent, false)
            )

            TypeGroup.voice_out -> VoiceOutViewHolder(
                ItemVoiceOutChatBinding.inflate(inflater, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is GroupTextInViewHolder -> holder.bind(getItem(position))
            is TextOutViewHolder -> holder.bind(getItem(position))
            is ImageUploadViewHolder -> holder.bind(getItem(position))
            is ImageInViewHolder -> holder.bind(getItem(position))
            is VoiceOutViewHolder -> holder.bind(getItem(position))
            is VoiceInViewHolder -> holder.bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int) = getItem(position).type.ordinal
}