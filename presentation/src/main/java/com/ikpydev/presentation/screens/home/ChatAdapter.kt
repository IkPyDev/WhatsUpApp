package com.ikpydev.presentation.screens.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ikpydev.domain.model.Chat
import com.ikpydev.presentation.R
import com.ikpydev.presentation.databinding.ItemChatsBinding

class ChatAdapter(private val chats: List<Chat>) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemChatsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(chat: Chat) = with(binding) {
            Glide.with(root).load(R.drawable.ic_person).into(avatar)
            name.text = chat.user.name
            phone.text = chat.user.phone

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemChatsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = chats.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(chats[position])
    }
}