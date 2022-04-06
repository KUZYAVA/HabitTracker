package com.kuzyava.habittrackerapp.ui.home

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.kuzyava.habittrackerapp.ui.list.ListFragment

private val TAB_TITLES = arrayOf(
    "Хорошие",
    "Плохие",
)

class HomePagerAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm) {
    override fun getCount() = 2

    override fun getItem(position: Int) = ListFragment.newInstance(position==0)

    override fun getPageTitle(position: Int) = TAB_TITLES[position]
}