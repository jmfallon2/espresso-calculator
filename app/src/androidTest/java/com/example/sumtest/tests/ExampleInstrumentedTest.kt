package com.example.sumtest.tests

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.PositionAssertions.isLeftOf
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isClickable
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.sumtest.R
import com.example.sumtest.SumActivity
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matcher

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(SumActivity::class.java)

    @Test
    fun useAppContext() {
        assertInitialState()
    }

    private fun assertInitialState(){
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.sumtest", appContext.packageName)
        assertMultiMatcherToId(withId(R.id.firstNumber), listOf(withText(""), isDisplayed()))
        assertMultiMatcherToId(withId(R.id.secondNumber), listOf(withText(""), isDisplayed()))
        assertMultiMatcherToId(withId(R.id.cta), listOf(withText("ADD"), isDisplayed(), isClickable()))
        assertMultiMatcherToId(withId(R.id.result), listOf(withText(""), not(isDisplayed())))
        assertMultiMatcherToId(withId(R.id.error), listOf(withText(""), not(isDisplayed())))
    }

    private fun assertMultiMatcherToId(viewMatcher: Matcher<View>, matcherList: List<Matcher<View>>){
        matcherList.forEach{ matcher ->
            onView(viewMatcher).check(matches(matcher))
        }
    }
}