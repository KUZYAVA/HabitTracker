package com.kuzyava.habittrackerapp.ui.list

import android.content.Context
import  android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.kuzyava.habittrackerapp.MainActivity
import com.kuzyava.habittrackerapp.R
import com.kuzyava.habittrackerapp.adapter.HabitsAdapter
import com.kuzyava.habittrackerapp.data.model.Habit
import com.kuzyava.habittrackerapp.databinding.FragmentListBinding
import com.kuzyava.habittrackerapp.ui.detail.DetailFragment
import javax.inject.Inject

class ListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding

    @Inject
    lateinit var viewModel: ListViewModel
    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).listComponent.inject(this)
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

        val clickListener: (Habit) -> Unit = {
            val id = it.uid
            val bundle = bundleOf(DetailFragment.ID_KEY to id)
            findNavController().navigate(R.id.action_to_detail, bundle)
        }
        val tickListener: (Habit) -> Unit = { viewModel.executeHabit(it) }
        val adapter = HabitsAdapter(clickListener, tickListener)

        viewModel.toast.observe(viewLifecycleOwner) {
            if (it != "") {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                viewModel.toast.value = ""
            }
        }

        val section = arguments?.getInt(SECTION) ?: 0
        viewModel.habits.observe(viewLifecycleOwner)
        { list ->
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
