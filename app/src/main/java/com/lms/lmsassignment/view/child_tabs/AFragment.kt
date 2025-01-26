package com.lms.lmsassignment.view.child_tabs

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.lms.lmsassignment.R
import com.lms.lmsassignment.data.model.HonoursAndAwards
import com.lms.lmsassignment.data.model.RankAndForms
import com.lms.lmsassignment.data.model.RecentResults
import com.lms.lmsassignment.data.model.SquadResponse
import com.lms.lmsassignment.data.model.SummaryResponse
import com.lms.lmsassignment.data.model.UpComingFixturesResponse
import com.lms.lmsassignment.data.model.WinsAndLoses
import com.lms.lmsassignment.databinding.FragmentABinding
import com.lms.lmsassignment.utils.AppUiState
import com.lms.lmsassignment.utils.gone
import com.lms.lmsassignment.utils.toDayDateMonth
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentABinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvSquadList.adapter = squadAdapter
        binding.rvVideos.adapter = videoAdapter
        binding.rvSquadList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvVideos.layoutManager = LinearLayoutManager(requireContext())

        viewModel.summary.observe(viewLifecycleOwner) {
            when (it) {
                is AppUiState.Loading -> {
                    binding.layoutBigCards.gone()
                    binding.layoutSmallCards.gone()
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
                    setupDescription(data.teamAndSponsor.TeamDescription)
                    setupHonours(data.honoursAndAwards)
                    setupRecentResults(data.recentResults)
                    setupUpComingFix(data.upComingFixturesList)
                    videoAdapter.submitList(data.recentVideosList)

                    val topPlayersAdapter = TopPlayersAdapter(
                        data.batsmenList,
                        data.bowlersList,
                        data.allRoundersList
                    )
                    binding.rvTopPlayers.adapter = topPlayersAdapter
                    binding.rvTopPlayers.layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

                    binding.videoProgressBar.gone()
                    binding.playersProgressBar.gone()
                }

                is AppUiState.Error -> {
                    binding.layoutBigCards.gone()
                    binding.layoutSmallCards.gone()
                    binding.videoProgressBar.gone()
                    binding.playersProgressBar.gone()
                    Log.d("AFragment", "Error..SumNCall")
                    Log.d("AFragment", "E ${it.message}")

                }
            }
        }
        viewModel.squadList.observe(viewLifecycleOwner) {
            when (it) {
                is AppUiState.Loading -> {
                    binding.squadProgressBar.visible()
                    Log.d("AFragment", "Loading..Squad")
                }

                is AppUiState.Loaded -> {
                    Log.d("AFragment", "Loaded..Squad")

                    val data = it.data as List<SquadResponse>

                    squadAdapter.submitList(data.take(3))
                    Log.d("AFragment", "Loaded..Squad ${data[0].FirstName}")
                    binding.squadProgressBar.gone()
                }

                is AppUiState.Error -> {
                    Log.d("AFragment", "Error..Squad")
                    Log.d("AFragment", "Error..${it.message}")
                    binding.squadProgressBar.gone()
                }
            }
        }
    }

    private fun setupUpComingFix(upComingFixturesList: List<UpComingFixturesResponse>) {
        val idList = listOf(
            binding.layoutUpComingFix.layoutUpComingFix1,
            binding.layoutUpComingFix.layoutUpComingFix2,
            binding.layoutUpComingFix.layoutUpComingFix3,
            binding.layoutUpComingFix.layoutUpComingFix4,
            binding.layoutUpComingFix.layoutUpComingFix5
        )

        try {
            for (index in idList.indices) {
                idList[index].apply {
                    tvDate.text = upComingFixturesList[index].dateTime.toDayDateMonth()
                    ivLeft.load(upComingFixturesList[index].teamLogo) { placeholder(R.drawable.lms) }
                    ivRight.load(upComingFixturesList[index].oppLogo) { placeholder(R.drawable.lms) }
                    tvTeamLeft.text = upComingFixturesList[index].teamName
                    tvTeamRight.text = upComingFixturesList[index].oppTeamName
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        binding.layoutUpComingFix.layoutUpComingFix1.ivTickLeft.visible()
        binding.layoutUpComingFix.layoutUpComingFix2.ivTickRight.visible()
        binding.layoutUpComingFix.layoutUpComingFix3.ivTickLeft.visible()
        binding.layoutUpComingFix.layoutUpComingFix4.ivTickRight.visible()
        binding.layoutUpComingFix.layoutUpComingFix5.ivTickLeft.visible()
    }

    private fun setupHonours(honoursAndAwards: HonoursAndAwards) {
        binding.layoutHonours.ivChamps.text = honoursAndAwards.Champion.toString()
        binding.layoutHonours.ivRunnersUp.text = honoursAndAwards.RunnersUp.toString()
    }

    private fun setupRecentResults(recentResults: List<RecentResults>) {
        val idList = listOf(
            binding.layoutResults.layoutResults1,
            binding.layoutResults.layoutResults2,
            binding.layoutResults.layoutResults3,
            binding.layoutResults.layoutResults4,
            binding.layoutResults.layoutResults5
        )

        try {
            for (index in idList.indices) {
                idList[index].apply {
                    tvDate.text = recentResults[index].dateTime.toDayDateMonth()
                    ivLeft.load(recentResults[index].teamLogo) { placeholder(R.drawable.lms) }
                    ivRight.load(recentResults[index].oppLogo) { placeholder(R.drawable.lms) }
                    tvTeamLeft.text = recentResults[index].teamName
                    tvTeamRight.text = recentResults[index].oppTeamName
                    tvResult.text = recentResults[index].matchInfo
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        binding.layoutResults.layoutResults1.ivTickLeft.visible()
        binding.layoutResults.layoutResults2.ivTickRight.visible()
        binding.layoutResults.layoutResults3.ivTickLeft.visible()
        binding.layoutResults.layoutResults4.ivTickRight.visible()
        binding.layoutResults.layoutResults5.ivTickLeft.visible()
    }

    private fun setupDescription(teamDescription: String) {
        binding.layoutDesc.tvDesc.text = teamDescription
    }

    private fun setupWinsAndLosses(winsAndLoses: WinsAndLoses) {
        binding.layoutBigCards.visible()
        binding.layoutBigCards1.tvTitle.text = "Matches"
        binding.layoutBigCards2.tvTitle.text = "Win Ratio"
        binding.layoutBigCards3.tvTitle.text = "Wins"
        binding.layoutBigCards4.tvTitle.text = "Loses"
        binding.layoutBigCards1.tvValue.text = winsAndLoses.gamesPlayed.toString()
        binding.layoutBigCards2.tvValue.text = winsAndLoses.WinRatio.toString()
        binding.layoutBigCards3.tvValue.text = winsAndLoses.Wins.toString()
        binding.layoutBigCards4.tvValue.text = winsAndLoses.Loses.toString()
    }

    private fun setupRankAndForms(rankAndForms: RankAndForms) {
        binding.layoutSmallCards.visible()
        binding.layoutSmallCards1.tvTitle.text = "City Rank:"
        binding.layoutSmallCards2.tvTitle.text = "National Rank:"
        binding.layoutSmallCards3.tvTitle.text = "World Rank:"
        binding.layoutSmallCards4.tvTitle.text = "Form:"
        binding.layoutSmallCards1.tvValue.text = rankAndForms.RegionalRank.toString()
        binding.layoutSmallCards2.tvValue.text = rankAndForms.CountryRank.toString()
        binding.layoutSmallCards3.tvValue.text = rankAndForms.WorldRank.toString()
        binding.layoutSmallCards4.tvValue.text = rankAndForms.Form.toString()
    }
}