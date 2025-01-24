package com.lms.lmsassignment.view.child_tabs.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lms.lmsassignment.data.model.BowlingResponse
import com.lms.lmsassignment.databinding.ItemBowlingBinding

class BowlingAdapter :
    ListAdapter<BowlingResponse, BowlingAdapter.BowlingViewHolder>(BowlingDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BowlingViewHolder {
        val binding = ItemBowlingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BowlingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BowlingViewHolder, position: Int) {
        val bowlingItem = getItem(position)
        holder.bind(bowlingItem)
    }

    inner class BowlingViewHolder(private val binding: ItemBowlingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: BowlingResponse) {
            binding.tvName.text = item.FirstName
            binding.tvOvers.text = item.Overs.toString()
            binding.tvWickets.text = item.Wickets.toString()
            binding.tvAvg.text = item.Average.toString()
            binding.tvEco.text = item.Economy.toString()
            binding.tvBest.text = item.Best
            binding.tv3fa.text = item.ThreeFA.toString()
            binding.tvNationalRank.text = item.NationalRank.toString()
            binding.tvWorldRank.text = item.WorldRank.toString()
        }
    }

    class BowlingDiffCallback : DiffUtil.ItemCallback<BowlingResponse>() {
        override fun areItemsTheSame(oldItem: BowlingResponse, newItem: BowlingResponse): Boolean {
            return oldItem.UserId == newItem.UserId
        }

        override fun areContentsTheSame(oldItem: BowlingResponse, newItem: BowlingResponse): Boolean {
            return oldItem == newItem
        }
    }
}