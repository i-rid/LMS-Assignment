package com.lms.lmsassignment.view.child_tabs

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.lms.lmsassignment.R
import com.lms.lmsassignment.data.model.BattingResponse
import com.lms.lmsassignment.data.model.BowlingResponse
import com.lms.lmsassignment.databinding.FragmentCBinding
import com.lms.lmsassignment.utils.AppUiState
import com.lms.lmsassignment.view.child_tabs.adapter.BowlingAdapter
import com.lms.lmsassignment.view_model.LMSViewModel

class CFragment : Fragment() {

    private lateinit var binding: FragmentCBinding
    private val viewModel : LMSViewModel by activityViewModels()
    private val bowlingAdapter: BowlingAdapter by lazy { BowlingAdapter() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvBowling.adapter = bowlingAdapter

        viewModel.bowlingList.observe(viewLifecycleOwner){
            when(it){
                is AppUiState.Loading -> {
                    Log.d("BFragment", "Loading..")
                }
                is AppUiState.Loaded -> {
                    Log.d("BFragment", "Loaded..")

                    val data = it.data as List<BowlingResponse>
                    bowlingAdapter.submitList(data)

                    Log.d("BFragment", "Loaded..${data}")

                }
                is AppUiState.Error -> {
                    Log.d("BFragment", "Error..")
                    Log.d("BFragment", "Error ${it.message}")
                }
            }
        }
    }

}