package com.lms.lmsassignment.view.child_tabs.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lms.lmsassignment.data.model.BatsmenBowlersAllRounders
import com.lms.lmsassignment.databinding.ItemTopPlayersBinding

import coil.load
import com.lms.lmsassignment.R

class TopPlayersAdapter(
    private val batsmenList: List<BatsmenBowlersAllRounders>,
    private val bowlersList: List<BatsmenBowlersAllRounders>,
    private val allRoundersList: List<BatsmenBowlersAllRounders>
) : RecyclerView.Adapter<TopPlayersAdapter.TopPlayersViewHolder>() {

    private val dataList = mutableListOf<BatsmenBowlersAllRounders>()

    init {
        // Combine the lists into the final data list in the desired order
        dataList.addAll(batsmenList)
        dataList.addAll(bowlersList)
        dataList.addAll(allRoundersList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopPlayersViewHolder {
        val binding =
            ItemTopPlayersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopPlayersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopPlayersViewHolder, position: Int) {
        val player = dataList[position]
        holder.bind(player, position)
    }

    override fun getItemCount(): Int = 3

    inner class TopPlayersViewHolder(private val binding: ItemTopPlayersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(player: BatsmenBowlersAllRounders, position: Int) {
            binding.apply {
                if (position == 0) {
                    // First item, display Title section
                    tvTitle.text = "Top Batsmen"
                    tvTopWR1.text = "World Rank: ${player.WorldRank}"
                    tvTopWR2.text = "World Rank: ${player.WorldRank}"
                    tvTopName.text = player.UserName
                    tvTopNR.text = "National Rank: ${player.NationalRank}"
                    ivTopProfile.load(player.UserPicture) {
                        crossfade(true)
                        placeholder(R.drawable.lms)
                    }

                    val idList = listOf(
                        layoutTP2.tvNumber,
                        layoutTP3.tvNumber,
                        layoutTP4.tvNumber,
                        layoutTP5.tvNumber,
                        layoutTP6.tvNumber,
                    )
                    for (i in 1..5) {
                        idList[i].text = "0${i + 1}"
                    }
                } else if (position == 1) {
                    // First item, display Title section
                    tvTitle.text = "Top Bowler"
                    tvTopWR1.text = "World Rank: ${player.WorldRank}"
                    tvTopWR2.text = "World Rank: ${player.WorldRank}"
                    tvTopName.text = player.UserName
                    tvTopNR.text = "National Rank: ${player.NationalRank}"
                    ivTopProfile.load(player.UserPicture) {
                        crossfade(true)
                        placeholder(R.drawable.lms)
                    }
                } else {
                    tvTitle.text = "All Rounders"
                    // For other players, use the layout with included rows
                    val playerRowBinding =
                        layoutTP2 // Access the corresponding layout (use `layoutTP3`, etc., for others)
                    playerRowBinding.tvName2.text = player.UserName
                    playerRowBinding.tvRank.text = "Nat/World Rank"
                    playerRowBinding.tvData2.text = "${player.NationalRank}/${player.WorldRank}"
                    playerRowBinding.ivCircularImage2.load(player.UserPicture) {
                        crossfade(true)
                        placeholder(R.drawable.lms)
                    }
                }
            }
        }
    }
}
