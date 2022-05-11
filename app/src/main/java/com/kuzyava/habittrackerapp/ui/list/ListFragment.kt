package com.kuzyava.habittrackerapp.ui.list

import  android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.kuzyava.habittrackerapp.HabitsApplication
import com.kuzyava.habittrackerapp.R
import com.kuzyava.habittrackerapp.adapter.HabitsAdapter
import com.kuzyava.habittrackerapp.databinding.FragmentListBinding
import com.kuzyava.habittrackerapp.ui.detail.DetailFragment

class ListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding
    private lateinit var viewModel: ListViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val store = requireParentFragment().viewModelStore
        viewModel = ViewModelProvider(store, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return ListViewModel((activity?.application as HabitsApplication).repository) as T
            }
        })[ListViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)

        val dividerItemDecoration = DividerItemDecoration(context, RecyclerView.VERTICAL)
        getDrawable(requireContext(), R.drawable.divider)?.let {
            dividerItemDecoration.setDrawable(it)
        }
        binding.recyclerView.addItemDecoration(dividerItemDecoration)

        val adapter = HabitsAdapter {
            val id = it.uid
            val bundle = bundleOf(DetailFragment.ID_KEY to id)
            findNavController().navigate(R.id.action_to_detail, bundle)
        }

        val section = arguments?.getInt(SECTION) ?: 0
        viewModel.habits.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list.filter { it.type == section })
        }
        binding.recyclerView.adapter = adapter
        return binding.root
    }


    companion object {
        private const val SECTION = "section"

        fun newInstance(section: Int): ListFragment {
            return ListFragment().apply {
                arguments = Bundle().apply {
                    putInt(SECTION, section)
                }
            }
        }
    }
}
