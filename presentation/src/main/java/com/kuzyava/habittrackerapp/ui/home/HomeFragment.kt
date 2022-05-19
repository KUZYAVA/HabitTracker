package com.kuzyava.habittrackerapp.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputEditText
import com.kuzyava.habittrackerapp.HabitsApplication
import com.kuzyava.habittrackerapp.R
import com.kuzyava.habittrackerapp.di.ListComponent
import com.kuzyava.habittrackerapp.di.components
import com.kuzyava.habittrackerapp.domain.FilterType
import com.kuzyava.habittrackerapp.domain.SortType
import com.kuzyava.habittrackerapp.ui.list.ListViewModel
import javax.inject.Inject

class HomeFragment : Fragment() {
    val listComponent: ListComponent by components {
        (activity?.application as HabitsApplication).applicationComponent.listComponent()
            .create()
    }

    @Inject
    lateinit var viewModel: ListViewModel
    override fun onAttach(context: Context) {
        super.onAttach(context)
        listComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val pagerAdapter = HomePagerAdapter(childFragmentManager)
        val viewPager = view.findViewById<ViewPager>(R.id.pager)
        viewPager.adapter = pagerAdapter

        val editSearch = view.findViewById<TextInputEditText>(R.id.editSearch)
        val radioGroup = view.findViewById<RadioGroup>(R.id.rgSort)

        view.findViewById<TabLayout>(R.id.tab_layout).setupWithViewPager(viewPager)
        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            findNavController().navigate(R.id.action_to_detail)
        }

        editSearch.addTextChangedListener {
            val text = it.toString()
            if (text.isNotEmpty() && text != "") {
                if (radioGroup.checkedRadioButtonId != R.id.sortDefault) {
                    radioGroup.check(R.id.sortDefault)
                }
                viewModel.getHabits(FilterType(text))
            } else viewModel.getHabits(SortType.SortDefault)
        }

        radioGroup.check(R.id.sortDefault)
        radioGroup.setOnCheckedChangeListener { g, id ->
            if (id != R.id.sortDefault && view.findViewById<RadioButton>(g.checkedRadioButtonId).isChecked) {
                editSearch.clearFocus()
                editSearch.setText("")
            }
            when (id) {
                R.id.sortDefault -> viewModel.getHabits(SortType.SortDefault)
                R.id.sortAmount1 -> viewModel.getHabits(SortType.SortByAmount)
                R.id.sortAmount2 -> viewModel.getHabits(SortType.SortByAmountDesc)
                R.id.sortPeriod1 -> viewModel.getHabits(SortType.SortByPeriod)
                R.id.sortPeriod2 -> viewModel.getHabits(SortType.SortByPeriodDesc)
            }
        }
        return view
    }
}