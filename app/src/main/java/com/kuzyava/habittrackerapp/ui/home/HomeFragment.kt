package com.kuzyava.habittrackerapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kuzyava.habittrackerapp.R
import com.kuzyava.habittrackerapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val pagerAdapter = HomePagerAdapter(childFragmentManager)
        val viewPager = binding.pager
        viewPager.adapter = pagerAdapter
        binding.tabLayout.setupWithViewPager(viewPager)

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_to_detail)
        }
        return binding.root
    }


}