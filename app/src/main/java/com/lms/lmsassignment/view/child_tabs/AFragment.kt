package com.lms.lmsassignment.view.child_tabs

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.lms.lmsassignment.R
import com.lms.lmsassignment.data.model.SummaryResponse
import com.lms.lmsassignment.databinding.FragmentABinding
import com.lms.lmsassignment.utils.AppUiState
import com.lms.lmsassignment.view_model.LMSViewModel

class AFragment : Fragment() {

    private lateinit var binding: FragmentABinding
    private val viewModel: LMSViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentABinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.summary.observe(viewLifecycleOwner){
            when(it){
                is AppUiState.Loading -> {
                    Log.d("AFragment", "Loading..")

                }
                is AppUiState.Loaded -> {
                    Log.d("AFragment", "Loaded..")
                    val data = it.data as SummaryResponse
                    Log.d("SumNetCall", "${data.description}")
                }
                is AppUiState.Error -> {
                    Log.d("AFragment", "Error..")
                    Log.d("AFragment", "E ${it.message}")

                }
            }
        }
    }
}