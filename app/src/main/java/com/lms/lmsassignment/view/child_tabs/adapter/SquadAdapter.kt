package com.lms.lmsassignment.view.child_tabs.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.lms.lmsassignment.R
import com.lms.lmsassignment.data.model.SquadResponse
import com.lms.lmsassignment.databinding.ItemSquadBinding

class SquadAdapter : ListAdapter<SquadResponse, SquadAdapter.SquadViewHolder>(SquadDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SquadViewHolder {
        val binding = ItemSquadBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SquadViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SquadViewHolder, position: Int) {
        val squad = getItem(position)
        holder.bind(squad)
    }

    class SquadViewHolder(private val binding: ItemSquadBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SquadResponse) {
            binding.apply {
                // Set the title
                tvTitle.text = item.UserName

                // Parse PlayerInfo and assign to TextViews
                if (item.PlayerInfo.equals("N/A", ignoreCase = true)) {
                    tvPInfo1.text = ""
                    tvPInfo2.text = ""
                } else {
                    val infoParts = item.PlayerInfo.split("-").map { it.trim() }
                    tvPInfo1.text = infoParts.getOrNull(0) ?: ""
                    tvPInfo2.text = infoParts.getOrNull(1) ?: ""
                }

                // Load the profile picture using Coil
                ivProfile.load(item.UserPicture) {
                    placeholder(R.drawable.lms)
                    error(R.drawable.lms)
                }
            }
        }
    }

    class SquadDiffCallback : DiffUtil.ItemCallback<SquadResponse>() {
        override fun areItemsTheSame(oldItem: SquadResponse, newItem: SquadResponse): Boolean {
            return oldItem.UserId == newItem.UserId
        }

        override fun areContentsTheSame(oldItem: SquadResponse, newItem: SquadResponse): Boolean {
            return oldItem == newItem
        }
    }
}
