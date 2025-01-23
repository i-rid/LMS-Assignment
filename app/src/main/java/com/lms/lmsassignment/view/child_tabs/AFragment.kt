package com.lms.lmsassignment.view.child_tabs

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lms.lmsassignment.R
import com.lms.lmsassignment.data.model.RankAndForms
import com.lms.lmsassignment.data.model.SquadResponse
import com.lms.lmsassignment.data.model.SummaryResponse
import com.lms.lmsassignment.data.model.TeamAndSponsor
import com.lms.lmsassignment.data.model.WinsAndLoses
import com.lms.lmsassignment.databinding.FragmentABinding
import com.lms.lmsassignment.utils.AppUiState
import com.lms.lmsassignment.utils.gone
import com.lms.lmsassignment.utils.visible
import com.lms.lmsassignment.view.child_tabs.adapter.SquadAdapter
import com.lms.lmsassignment.view.child_tabs.adapter.TopPlayersAdapter
import com.lms.lmsassignment.view.child_tabs.adapter.VideoAdapter
import com.lms.lmsassignment.view_model.LMSViewModel

class AFragment : Fragment() {

    private lateinit var binding: FragmentABinding
    private val viewModel: LMSViewModel by activityViewModels()
    private val squadAdapter: SquadAdapter by lazy { SquadAdapter() }
    private val videoAdapter: VideoAdapter by lazy { VideoAdapter() }
    private lateinit var topPlayersAdapter: TopPlayersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentABinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvSquadList.adapter = squadAdapter
        binding.rvVideos.adapter = videoAdapter
        binding.rvSquadList.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)
        binding.rvVideos.layoutManager = LinearLayoutManager(requireContext())

        viewModel.summary.observe(viewLifecycleOwner){
            when(it){
                is AppUiState.Loading -> {
                    binding.layoutBigCards.root.gone()
                    binding.layoutSmallCards.root.gone()
                    binding.videoProgressBar.visible()
                    binding.playersProgressBar.visible()
                    Log.d("AFragment", "Loading..SumNCall")

                }
                is AppUiState.Loaded -> {
                    Log.d("AFragment", "Loaded..SumNCall")
                    val data = it.data as SummaryResponse
                    Log.d("SumNCall", "FinSumRes ${data}")

                    setupWinsAndLosses(data.winsAndLoses)
                    setupRankAndForms(data.rankAndForms)
                    videoAdapter.submitList(data.recentVideosList)

                    val topPlayersAdapter = TopPlayersAdapter(
                        data.batsmenList,
                        data.bowlersList,
                        data.allRoundersList
                    )
                    binding.rvTopPlayers.adapter = topPlayersAdapter
                    binding.rvTopPlayers.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)

                    binding.videoProgressBar.gone()
                    binding.playersProgressBar.gone()
                }
                is AppUiState.Error -> {
                    binding.layoutBigCards.root.gone()
                    binding.layoutSmallCards.root.gone()
                    binding.videoProgressBar.gone()
                    binding.playersProgressBar.gone()
                    Log.d("AFragment", "Error..SumNCall")
                    Log.d("AFragment", "E ${it.message}")

                }
            }
        }
        viewModel.squadList.observe(viewLifecycleOwner){
            when(it){
                is AppUiState.Loading ->{
                    binding.squadProgressBar.visible()
                    Log.d("AFragment","Loading..Squad")
                }
                is AppUiState.Loaded ->{
                    Log.d("AFragment","Loaded..Squad")

                    val data = it.data as List<SquadResponse>

                    squadAdapter.submitList(data.take(3))
                    Log.d("AFragment","Loaded..Squad ${data[0].UserName}")
                    binding.squadProgressBar.gone()
                }
                is AppUiState.Error ->{
                    Log.d("AFragment","Error..Squad")
                    Log.d("AFragment","Error..${it.message}")
                    binding.squadProgressBar.gone()
                }
            }
        }
    }

    private fun setupWinsAndLosses(winsAndLoses: WinsAndLoses) {
        binding.layoutBigCards.root.visible()
        binding.layoutBigCards.tvB1.text = winsAndLoses.gamesPlayed.toString()
        binding.layoutBigCards.tvB2.text = winsAndLoses.WinRatio.toString()
        binding.layoutBigCards.tvB3.text = winsAndLoses.Wins.toString()
        binding.layoutBigCards.tvB4.text = winsAndLoses.Loses.toString()
    }
    private fun setupRankAndForms(rankAndForms: RankAndForms) {
        binding.layoutSmallCards.root.visible()
        binding.layoutSmallCards.tvB1.text = rankAndForms.RegionalRank.toString()
        binding.layoutSmallCards.tvB2.text = rankAndForms.CountryRank.toString()
        binding.layoutSmallCards.tvB3.text = rankAndForms.WorldRank.toString()
        binding.layoutSmallCards.tvB4.text = rankAndForms.Form.toString()
    }
}