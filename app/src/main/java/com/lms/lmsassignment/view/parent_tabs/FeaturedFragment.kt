package com.lms.lmsassignment.view.parent_tabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lms.lmsassignment.view.child_tabs.adapter.ChildTabAdapter
import com.lms.lmsassignment.databinding.FragmentFeaturedBinding

class FeaturedFragment : Fragment() {

    private lateinit var binding: FragmentFeaturedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeaturedBinding.inflate(inflater, container, false)

        val childTabAdapter = ChildTabAdapter(this)
        binding.viewPager.adapter = childTabAdapter

        binding.btnSummary.setOnClickListener{ binding.viewPager.currentItem = 0}
        binding.btnBatting.setOnClickListener { binding.viewPager.currentItem = 1 }
        binding.btnBowling.setOnClickListener { binding.viewPager.currentItem = 2 }

        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}