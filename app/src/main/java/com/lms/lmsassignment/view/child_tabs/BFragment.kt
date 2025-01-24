package com.lms.lmsassignment.view.child_tabs

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.lms.lmsassignment.data.model.BattingResponse
import com.lms.lmsassignment.databinding.FragmentBBinding
import com.lms.lmsassignment.utils.AppUiState
import com.lms.lmsassignment.utils.gone
import com.lms.lmsassignment.utils.visible
import com.lms.lmsassignment.view.child_tabs.adapter.BattingAdapter
import com.lms.lmsassignment.view.child_tabs.adapter.DividerItemDecoration
import com.lms.lmsassignment.view_model.LMSViewModel

class BFragment : Fragment() {

    private lateinit var binding: FragmentBBinding
    private val viewModel : LMSViewModel by activityViewModels()
    private val battingAdapter : BattingAdapter by lazy { BattingAdapter() }
    private var battingList : List<BattingResponse> ?= null
    private var battingListFiltered : List<BattingResponse> ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvBatting.adapter = battingAdapter
        binding.rvBatting.addItemDecoration(DividerItemDecoration(requireContext()))


        binding.cbFormerPlayers.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.ivArrow.visible()
            } else {
                binding.ivArrow.gone()
            }
        }

        viewModel.battingList.observe(viewLifecycleOwner){
            when(it){
                is AppUiState.Loading -> {
                    Log.d("BFragment", "Loading..")
                    binding.progressBar.visible()
                }
                is AppUiState.Loaded -> {

                    Log.d("BFragment", "Loaded..")

                    val data = it.data as List<BattingResponse>

                    battingAdapter.submitList(data)
                    Log.d("BFragment", "Loaded..${data[0].FirstName}")
                    binding.progressBar.gone()
                }
                is AppUiState.Error -> {
                    binding.progressBar.gone()
                    Log.d("BFragment", "Error..")
                    Log.d("BFragment", "Error ${it.message}")
                }
            }
        }
    }

}