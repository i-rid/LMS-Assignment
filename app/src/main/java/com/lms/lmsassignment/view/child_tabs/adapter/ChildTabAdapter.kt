package com.lms.lmsassignment.view.child_tabs.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lms.lmsassignment.view.child_tabs.AFragment
import com.lms.lmsassignment.view.child_tabs.BFragment
import com.lms.lmsassignment.view.child_tabs.CFragment

class ChildTabAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    private val fragments = listOf<Fragment>(
        AFragment(),
        BFragment(),
        CFragment()
    )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}