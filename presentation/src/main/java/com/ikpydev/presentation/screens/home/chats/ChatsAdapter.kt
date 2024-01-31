package com.ikpydev.presentation.screens.home.chats

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ikpydev.domain.model.Chat
import com.ikpydev.presentation.R
import com.ikpydev.presentation.databinding.ItemChatsBinding

class ChatsAdapter(private var chats: List<Chat>, private val onClick: (chat: Chat) -> Unit) :
    RecyclerView.Adapter<ChatsAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemChatsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(chat: Chat) = with(binding) {
            val icon =
                if (chat.user.avatar != null) chat.user.avatar else R.drawable.ic_person
            Glide.with(root).load(icon).into(avatar)
            name.text = chat.user.name
            phone.text = chat.user.phone
            root.setOnClickListener {
                onClick(chat)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemChatsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )


    override fun getItemCount() = chats.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(chats[position])
    }


    fun updateChats(chats: List<Chat>) {
        this.chats = chats
        this.notifyDataSetChanged()
    }
}