package com.ikpydev.presentation.screens.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ikpydev.domain.model.Message
import com.ikpydev.domain.model.Type
import com.ikpydev.presentation.databinding.ItemImageInChatBinding
import com.ikpydev.presentation.databinding.ItemImageOutChatBinding
import com.ikpydev.presentation.databinding.ItemTextInChatBinding
import com.ikpydev.presentation.databinding.ItemTextOutChatBinding

class ChatAdapter : ListAdapter<Message, RecyclerView.ViewHolder>(DIFF_UTIL) {

    inner class TextInViewHolder(private val binding: ItemTextInChatBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) = with(binding) {
            binding.text.text = message.message
        }
    }

    inner class TextOutViewHolder(private val binding: ItemTextOutChatBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) = with(binding) {
            binding.text.text = message.message
        }
    }

    inner class ImageUploadViewHolder(private val binding: ItemImageOutChatBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) = with(binding) {
            Glide.with(root).load(message.image).into(image)
        }
    }

    inner class ImageInViewHolder(private val binding: ItemImageInChatBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) = with(binding) {
            Glide.with(root).load(message.image).into(image)
        }
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Message>() {
            override fun areItemsTheSame(oldItem: Message, newItem: Message) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
                return oldItem.message == newItem.message
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (Type.values()[viewType]) {
            Type.text_in -> TextInViewHolder(
                ItemTextInChatBinding.inflate(inflater, parent, false)
            )

            Type.text_out -> TextOutViewHolder(
                ItemTextOutChatBinding.inflate(inflater, parent, false)
            )

            Type.image_upload -> ImageUploadViewHolder(
                ItemImageOutChatBinding.inflate(inflater, parent, false)
            )

            Type.image_in -> ImageInViewHolder(
                ItemImageInChatBinding.inflate(inflater, parent, false)
            )

            Type.image_out -> ImageUploadViewHolder(
                ItemImageOutChatBinding.inflate(inflater, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TextInViewHolder -> holder.bind(getItem(position))
            is TextOutViewHolder -> holder.bind(getItem(position))
            is ImageUploadViewHolder -> holder.bind(getItem(position))
            is ImageInViewHolder -> holder.bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int) = getItem(position).type.ordinal
}