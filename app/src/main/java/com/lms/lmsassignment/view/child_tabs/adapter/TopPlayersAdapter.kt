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

                when (position) {
                    0 -> {
                        val playerBatsmen = batsmenList[0]
                        // First item, display Title section
                        tvTitle.text = "Top Batsmen"
                        tvTopWR1.text = "World Rank: ${playerBatsmen.WorldRank}"
                        tvTopWR2.text = "World Rank: ${playerBatsmen.WorldRank}"
                        tvTopName.text = playerBatsmen.UserName
                        tvTopNR.text = "National Rank: ${playerBatsmen.NationalRank}"
                        ivTopProfile.load(playerBatsmen.UserPicture) {
                            crossfade(true)
                            placeholder(R.drawable.ic_profile)
                        }

                        val rest = batsmenList.drop(1)
                        val idList = listOf(
                            layoutTP2,
                            layoutTP3,
                            layoutTP4,
                            layoutTP5,
                            layoutTP6,
                        )
                        try {
                            for (index in idList.indices) {
                                idList[index].tvNumber.text = "0${index + 2}"

                                idList[index].tvName2.text = rest[index].UserName
                                idList[index].ivImage2.load(rest[index].UserPicture) { placeholder(R.drawable.ic_profile) }
                                idList[index].tvData2.text =
                                    rest[index].NationalRank.toString() + "/" + rest[index].WorldRank.toString()
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                    }

                    1 -> {
                        val playerBowler = bowlersList[0]
                        val rest = bowlersList.drop(1)
                        // First item, display Title section
                        tvTitle.text = "Top Bowler"
                        tvTopWR1.text = "World Rank: ${playerBowler.WorldRank}"
                        tvTopWR2.text = "World Rank: ${playerBowler.WorldRank}"
                        tvTopName.text = playerBowler.UserName
                        tvTopNR.text = "National Rank: ${playerBowler.NationalRank}"
                        ivTopProfile.load(playerBowler.UserPicture) {
                            crossfade(true)
                            placeholder(R.drawable.ic_profile)
                        }

                        val idList = listOf(
                            layoutTP2,
                            layoutTP3,
                            layoutTP4,
                            layoutTP5,
                            layoutTP6,
                        )
                        try {
                            for (index in idList.indices) {
                                idList[index].tvNumber.text = "0${index + 2}"

                                idList[index].tvName2.text = rest[index].UserName
                                idList[index].ivImage2.load(rest[index].UserPicture) { placeholder(R.drawable.ic_profile) }
                                idList[index].tvData2.text =
                                    rest[index].NationalRank.toString() + "/" + rest[index].WorldRank.toString()
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                    }

                    else -> {
                        val playerAR = allRoundersList[0]
                        val rest = allRoundersList.drop(1)

                        tvTitle.text = "All Rounders"
                        tvTopWR1.text = "World Rank: ${playerAR.WorldRank}"
                        tvTopWR2.text = "World Rank: ${playerAR.WorldRank}"
                        tvTopName.text = playerAR.UserName
                        tvTopNR.text = "National Rank: ${playerAR.NationalRank}"
                        ivTopProfile.load(playerAR.UserPicture) {
                            crossfade(true)
                            placeholder(R.drawable.ic_profile)
                        }

                        val idList = listOf(
                            layoutTP2,
                            layoutTP3,
                            layoutTP4,
                            layoutTP5,
                            layoutTP6,
                        )
                        try {
                            for (index in idList.indices) {
                                idList[index].tvNumber.text = "0${index + 2}"

                                idList[index].tvName2.text = rest[index].UserName
                                idList[index].ivImage2.load(rest[index].UserPicture) { placeholder(R.drawable.ic_profile) }
                                idList[index].tvData2.text =
                                    rest[index].NationalRank.toString() + "/" + rest[index].WorldRank.toString()
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                    }
                }
            }
        }
    }
}
