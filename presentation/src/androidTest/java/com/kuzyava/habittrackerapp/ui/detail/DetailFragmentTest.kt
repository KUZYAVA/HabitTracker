package com.kuzyava.habittrackerapp.ui.detail

import android.widget.EditText
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kuzyava.habittrackerapp.R
import junit.framework.TestCase
import org.hamcrest.Matchers.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class DetailFragmentTest : TestCase() {
    private lateinit var scenario: FragmentScenario<DetailFragment>

    @Before
    fun setup() {
        scenario = launchFragmentInContainer(themeResId = R.style.Theme_HabitTracker)
        scenario.moveToState(Lifecycle.State.STARTED)
    }

    @Test
    fun addHabitWithoutFullInfo() {
        onView(
            allOf(
                isDescendantOfA(withId(R.id.tfDesc)),
                isAssignableFrom(EditText::class.java)
            )
        ).perform(typeText("yea"))
        onView(
            allOf(
                isDescendantOfA(withId(R.id.tfAmount)),
                isAssignableFrom(EditText::class.java)
            )
        ).perform(typeText("10"))
        onView(withId(R.id.radioButton2))
            .perform(click())
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.btnSave)).perform(click())
        onView(withId(R.id.errorMessage)).check(matches(withText(R.string.errorMessage)))
    }

    @Test
    fun addHabitWithFullInfo() {
        onView(
            allOf(
                isDescendantOfA(withId(R.id.tfTitle)),
                isAssignableFrom(EditText::class.java)
            )
        ).perform(typeText("hello"))
        onView(
            allOf(
                isDescendantOfA(withId(R.id.tfDesc)),
                isAssignableFrom(EditText::class.java)
            )
        ).perform(typeText("yea"))
        onView(
            allOf(
                isDescendantOfA(withId(R.id.tfAmount)),
                isAssignableFrom(EditText::class.java)
            )
        ).perform(typeText("10"))
        onView(
            allOf(
                isDescendantOfA(withId(R.id.tfPeriodicity)),
                isAssignableFrom(EditText::class.java)
            )
        ).perform(typeText("10"))
        onView(withId(R.id.radioButton2))
            .perform(click())
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.btnSave)).perform(click())
        onView(withId(R.id.errorMessage)).check(matches(withText(R.string.goodMessage)))
    }

    @Test
    fun replaceText() {
        onView(
            allOf(
                isDescendantOfA(withId(R.id.tfTitle)),
                isAssignableFrom(EditText::class.java)
            )
        ).perform(
            typeText("hello"),
            replaceText("how are u?")
        )
            .check(matches(withText("how are u?")))
    }

    @Test
    fun checkRadioButtons(){
        onView(withId(R.id.radioButton2))
            .perform(click())
        onView(withId(R.id.radioButton1))
            .perform(click()).check(matches(isChecked()))
        onView(withId(R.id.radioButton2)).check(matches(isNotChecked()))
    }
}