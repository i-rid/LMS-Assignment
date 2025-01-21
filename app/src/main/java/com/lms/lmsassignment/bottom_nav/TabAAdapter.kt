package com.lms.lmsassignment.bottom_nav

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabAAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    private val fragmentList = listOf(
        FeaturedFragment(),
        ProFragment()
    )

    private val fragmentTitles = listOf(
        "Featured",
        "Pro"
    )

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position]

    fun getTabTitle(position: Int): String = fragmentTitles[position]
}