package com.kuzyava.habittrackerapp.ui.list

import  android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.kuzyava.habittrackerapp.R
import com.kuzyava.habittrackerapp.adapter.HabitsAdapter
import com.kuzyava.habittrackerapp.databinding.FragmentListBinding
import com.kuzyava.habittrackerapp.ui.detail.DetailFragment

class ListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding
    private lateinit var viewModel: ListViewModel
    private lateinit var adapter: HabitsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val store = requireParentFragment().viewModelStore
        viewModel = ViewModelProvider(store, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ListViewModel() as T
            }
        })[ListViewModel::class.java]
    }

    private var section: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)

        adapter = HabitsAdapter {
            val position = viewModel.getHabits().indexOf(it)
            val bundle = bundleOf(DetailFragment.POSITION_KEY to position)
            findNavController().navigate(R.id.action_to_detail, bundle)
        }
        binding.recyclerView.adapter = adapter

        section = arguments?.getBoolean(SECTION) ?: true
        viewModel.habitsList.observe(viewLifecycleOwner) { list ->
            adapter.setHabits(list.filter { it.type == section })
        }

        val dividerItemDecoration = DividerItemDecoration(context, RecyclerView.VERTICAL)
        getDrawable(requireContext(), R.drawable.divider)?.let {
            dividerItemDecoration.setDrawable(it)
        }
        binding.recyclerView.addItemDecoration(dividerItemDecoration)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.load()
    }

    companion object {
        private const val SECTION = "section"

        fun newInstance(section: Boolean): ListFragment {
            return ListFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(SECTION, section)
                }
            }
        }
    }

}
