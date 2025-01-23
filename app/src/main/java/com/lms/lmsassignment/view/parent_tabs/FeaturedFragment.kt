package com.lms.lmsassignment.view.parent_tabs

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import coil.load
import com.lms.lmsassignment.R
import com.lms.lmsassignment.data.model.SummaryResponse
import com.lms.lmsassignment.data.model.TeamAndSponsor
import com.lms.lmsassignment.view.child_tabs.adapter.ChildTabAdapter
import com.lms.lmsassignment.databinding.FragmentFeaturedBinding
import com.lms.lmsassignment.utils.AppUiState
import com.lms.lmsassignment.utils.gone
import com.lms.lmsassignment.utils.visible
import com.lms.lmsassignment.view_model.LMSViewModel
import kotlin.math.truncate

class FeaturedFragment : Fragment() {

    private lateinit var binding: FragmentFeaturedBinding
    private val viewModel : LMSViewModel by  activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeaturedBinding.inflate(inflater, container, false)

        val childTabAdapter = ChildTabAdapter(this)
        binding.viewPager.adapter = childTabAdapter

        binding.btnSummary.setOnClickListener {
            binding.viewPager.currentItem = 0
            setButtonColor(binding.btnSummary, binding.btnBatting, binding.btnBowling)
        }

        binding.btnBatting.setOnClickListener {
            binding.viewPager.currentItem = 1
            setButtonColor(binding.btnBatting, binding.btnSummary, binding.btnBowling)
        }

        binding.btnBowling.setOnClickListener {
            binding.viewPager.currentItem = 2
            setButtonColor(binding.btnBowling, binding.btnSummary, binding.btnBatting)
        }

        return  binding.root
    }

    fun setButtonColor(selected: Button, unselected1: Button, unselected2: Button) {
        selected.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.lms_primary_bg))
        unselected1.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.lms_primary))
        unselected2.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.lms_primary))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.summary.observe(viewLifecycleOwner){
            when(it){
                is AppUiState.Loading -> {
                    binding.progressBar.visible()
                    Log.d("FeatureFragment", "Loading..SumNCall")

                }
                is AppUiState.Loaded -> {
                    Log.d("FeatureFragment", "Loaded..SumNCall")
                    val data = it.data as SummaryResponse
                    Log.d("SumNCall", "FinSumRes ${data}")

                    setupTeamAndSponsor(data.teamAndSponsor)
                    binding.progressBar.gone()
                }
                is AppUiState.Error -> {
                    Log.d("FeatureFragment", "Error..SumNCall")
                    Log.d("FeatureFragment", "E ${it.message}")
                    binding.progressBar.gone()
                }
            }
        }

    }

    private fun setupTeamAndSponsor(data: TeamAndSponsor) {
        binding.tvTeamName.text = data.TeamName
        binding.tvSponsorName.text = "Team Sponsor"

        binding.ivTeam.load(data.TeamLogo) {
            crossfade(true)
            placeholder(R.drawable.lms)
        }
        binding.ivTeamSponsor.load(data.SponsorLogo) {
            crossfade(true)
            placeholder(R.drawable.lms)
        }
    }
}