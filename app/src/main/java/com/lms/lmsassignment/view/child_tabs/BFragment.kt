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
import com.lms.lmsassignment.data.model.BattingResponse
import com.lms.lmsassignment.databinding.FragmentABinding
import com.lms.lmsassignment.databinding.FragmentBBinding
import com.lms.lmsassignment.databinding.FragmentFeaturedBinding
import com.lms.lmsassignment.utils.AppUiState
import com.lms.lmsassignment.view.child_tabs.adapter.BattingAdapter
import com.lms.lmsassignment.view_model.LMSViewModel

class BFragment : Fragment() {

    private lateinit var binding: FragmentBBinding
    private val viewModel : LMSViewModel by activityViewModels()
    private val battingAdapter : BattingAdapter by lazy { BattingAdapter() }

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


        binding.cbFormerPlayers.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {

            } else {

            }
        }

        viewModel.battingList.observe(viewLifecycleOwner){
            when(it){
                is AppUiState.Loading -> {
                    Log.d("BFragment", "Loading..")
                }
                is AppUiState.Loaded -> {
                    Log.d("BFragment", "Loaded..")

                    val data = it.data as List<BattingResponse>
                    battingAdapter.submitList(data)
                    Log.d("BFragment", "Loaded..${data[0].UserName}")

                }
                is AppUiState.Error -> {
                    Log.d("BFragment", "Error..")
                    Log.d("BFragment", "Error ${it.message}")
                }
            }
        }
    }

}