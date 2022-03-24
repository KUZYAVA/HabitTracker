package com.kuzyava.habittrackerapp.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

private val TAB_TITLES = arrayOf(
    "Хорошие",
    "Плохие"
)

class HomePagerAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        return TrackerListFragment.newInstance(position)
    }

    override fun getPageTitle(position: Int): CharSequence {
        return TAB_TITLES[position]
    }
}