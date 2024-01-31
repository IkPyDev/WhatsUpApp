//package com.ikpydev.presentation.screens.home
//
//import android.annotation.SuppressLint
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.ikpydev.domain.model.Chat
//import com.ikpydev.domain.model.GroupChat
//import com.ikpydev.presentation.R
//import com.ikpydev.presentation.databinding.ItemChatsBinding
//import com.ikpydev.presentation.databinding.ItemGroupChatsBinding
//
//class HomePagerAdapter(
//    private var chats: List<Chat>,
//    private var groupChats: List<GroupChat>,
//    private val onClickChat: (chat: Chat) -> Unit,
//    private val onClickGroupChat: (groupChat: GroupChat) -> Unit
//) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//
//    companion object {
//        private const val VIEW_TYPE_CHAT = 0
//        private const val VIEW_TYPE_GROUP_CHAT = 1
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        return if (position < chats.size) VIEW_TYPE_CHAT else VIEW_TYPE_GROUP_CHAT
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        return if (viewType == VIEW_TYPE_CHAT) {
//            val binding =
//                ItemChatsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//            ChatViewHolder(binding, onClickChat)
//        } else {
//            val binding =
//                ItemGroupChatsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//            GroupChatViewHolder(binding, onClickGroupChat)
//        }
//    }
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        if (getItemViewType(position) == VIEW_TYPE_CHAT) {
//            (holder as ChatViewHolder).bind(chats[position])
//        } else {
//            (holder as GroupChatViewHolder).bind(groupChats[position])
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return chats.size + groupChats.size
//    }
//
//    class ChatViewHolder(
//        private val binding: ItemChatsBinding,
//        private val onClick: (chat: Chat) -> Unit
//    ) : RecyclerView.ViewHolder(binding.root) {
//        fun bind(chat: Chat) = with(binding) {
//            val icon = if (chat.user.avatar != null) chat.user.avatar else R.drawable.ic_person
//            Glide.with(root).load(icon).into(avatar)
//            name.text = chat.user.name
//            phone.text = chat.user.phone
//            root.setOnClickListener {
//                onClick(chat)
//            }
//        }
//    }
//
//    class GroupChatViewHolder(
//        private val binding: ItemGroupChatsBinding,
//        private val onClick: (groupChat: GroupChat) -> Unit
//    ) : RecyclerView.ViewHolder(binding.root) {
//        fun bind(groupChat: GroupChat) = with(binding) {
//
//            val icon =
//                if (groupChat.group.avatar != null) groupChat.group.avatar else R.drawable.ic_groups_24
//            Glide.with(root).load(icon).into(avatar)
//            name.text = groupChat.group.name
//            root.setOnClickListener {
//                onClick(groupChat)
//            }
//            // bind group chat data to views
//            // similar to the ChatViewHolder
//        }
//    }
//
//    @SuppressLint("NotifyDataSetChanged")
//    fun updateChats(chats: List<Chat>) {
//        this.chats = chats
//        notifyDataSetChanged()
//    }
//
//    @SuppressLint("NotifyDataSetChanged")
//    fun updateGroupChats(groupChats: List<GroupChat>) {
//        this.groupChats = groupChats
//        notifyDataSetChanged()
//    }
//
//}
//
