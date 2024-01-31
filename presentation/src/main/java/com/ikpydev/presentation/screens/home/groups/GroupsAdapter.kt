package com.ikpydev.presentation.screens.home.groups

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ikpydev.domain.model.GroupChat
import com.ikpydev.presentation.R
import com.ikpydev.presentation.databinding.ItemGroupsBinding

class GroupsAdapter(
    private var groups: List<GroupChat>,
    private val onClick: (groups: GroupChat) -> Unit
    ) : RecyclerView.Adapter<GroupsAdapter.ViewHolder>() {
        inner class ViewHolder(private val binding: ItemGroupsBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(group: GroupChat) = with(binding) {
                val icon =
                    if (group.group.avatar != null) group.group.avatar else R.drawable.ic_groups_24
                Glide.with(root).load(icon).into(avatar)
                name.text = group.group.name
                root.setOnClickListener {
                    onClick(group)
                }

            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
            ItemGroupsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

        override fun getItemCount() = groups.size
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(groups[position])
        }

        @SuppressLint("NotifyDataSetChanged")
        fun renderGroupChats(groupChats: List<GroupChat>) {

            groups = groupChats
            notifyDataSetChanged()

        }

    }
