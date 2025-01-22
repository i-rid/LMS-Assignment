package com.lms.lmsassignment.view.child_tabs.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lms.lmsassignment.data.model.BattingResponse
import com.lms.lmsassignment.databinding.ItemBattingBinding

class BattingAdapter : ListAdapter<BattingResponse, BattingAdapter.BattingViewHolder>(BattingDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BattingViewHolder {
        val binding = ItemBattingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BattingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BattingViewHolder, position: Int) {
        val battingResponse = getItem(position)
        holder.bind(battingResponse)
    }

    class BattingViewHolder(private val binding: ItemBattingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: BattingResponse) {
            binding.apply {
                tvName.text = item.UserName
                tvInnings.text = item.Innings.toString()
                tvRuns.text = item.Runs.toString()
                tvAvg.text = String.format("%.2f", item.Average)
                tvSR.text = String.format("%.2f", item.StrikeRate)
                tvHS.text = item.HighestScore.toString()
                tv50s.text = item.Fifties.toString()
                tvNationalRank.text = item.NationalRank.toString()
                tvWorldRank.text = item.WorldRank.toString()
            }
        }
    }

    class BattingDiffCallback : DiffUtil.ItemCallback<BattingResponse>() {
        override fun areItemsTheSame(oldItem: BattingResponse, newItem: BattingResponse): Boolean {
            return oldItem.UserId == newItem.UserId
        }

        override fun areContentsTheSame(oldItem: BattingResponse, newItem: BattingResponse): Boolean {
            return oldItem == newItem
        }
    }
}
