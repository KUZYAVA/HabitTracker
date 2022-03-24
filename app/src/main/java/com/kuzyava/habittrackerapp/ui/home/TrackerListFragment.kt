package com.kuzyava.habittrackerapp.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.kuzyava.habittrackerapp.R
import com.kuzyava.habittrackerapp.adapter.HabitsAdapter
import com.kuzyava.habittrackerapp.databinding.FragmentListBinding
import com.kuzyava.habittrackerapp.model.HabitsLab
import com.kuzyava.habittrackerapp.ui.detail.DetailFragment

class TrackerListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding
    private lateinit var adapter: HabitsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)

        val habitsList = if (arguments?.getInt(SECTION_NUMBER) ?: 0 == 0) {
            HabitsLab.habits.filter { it.type == 0 }
        } else {
            HabitsLab.habits.filter { it.type == 1 }
        }

        adapter = HabitsAdapter(habitsList) {
            val position = HabitsLab.habits.indexOf(it)
            val bundle = bundleOf(DetailFragment.POSITION_KEY to position)
            findNavController().navigate(R.id.action_to_detail, bundle)
        }
        binding.recyclerView.adapter = adapter

        val dividerItemDecoration = DividerItemDecoration(context, RecyclerView.VERTICAL)
        getDrawable(requireContext(), R.drawable.divider)?.let {
            dividerItemDecoration.setDrawable(it)
        }
        binding.recyclerView.addItemDecoration(dividerItemDecoration)

        return binding.root
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }

    companion object {
        private const val SECTION_NUMBER = "section_number"

        fun newInstance(sectionNumber: Int): TrackerListFragment {
            return TrackerListFragment().apply {
                arguments = Bundle().apply {
                    putInt(SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}
