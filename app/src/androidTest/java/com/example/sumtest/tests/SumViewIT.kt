package com.example.sumtest.tests

import android.view.View
import androidx.test.espresso.Espresso.onIdle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isClickable
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.sumtest.Error
import com.example.sumtest.SumActivity
import com.example.sumtest.framework.SumActivity.Companion.CTA
import com.example.sumtest.framework.SumActivity.Companion.ERROR
import com.example.sumtest.framework.SumActivity.Companion.EXPECTED_CTA_HINT
import com.example.sumtest.framework.SumActivity.Companion.EXPECTED_NUMBER_HINT
import com.example.sumtest.framework.SumActivity.Companion.FIRST_NUMBER
import com.example.sumtest.framework.SumActivity.Companion.LOADING
import com.example.sumtest.framework.SumActivity.Companion.RESULT
import com.example.sumtest.framework.SumActivity.Companion.SECOND_NUMBER
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matcher
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class SumViewIT {

    @get:Rule
    val activityRule = ActivityScenarioRule(SumActivity::class.java)

    @Test
    fun onStartUp() {
        assertInitialState()
    }

    @Test
    fun onNonIntValue() {
        val specialChar = "£$%"
        val stringVal = "a string"
        setupAndClick(specialChar, stringVal)
        assertState("", Error.InvalidInput.message, specialChar, stringVal)
        setupAndClick(stringVal, specialChar)
        assertState("", Error.InvalidInput.message, stringVal, specialChar)
    }

    @Test
    fun onEmptyValue() {
        val validNum = "34"
        setupAndClick("", "")
        assertState("", Error.EmptyInput.message, "", "")
        setupAndClick(validNum, "")
        assertState("", Error.EmptyInput.message, validNum, "")
    }

    @Test
    fun onHighLowValue() {
        val min = "-1001"
        val max = "1001"
        val validNum = "34"
        setupAndClick(min, validNum)
//  Unsure if Overflow message is supposed to be a bug
        assertState("", Error.Overflow.message, min, validNum)
        setupAndClick(validNum, max)
        assertState("", Error.Overflow.message, validNum, max)
    }

    @Test
    fun onValidValues() {
        val validNum = "34"
        val validNum2 = "-64"
        setupAndClick(validNum, validNum2)
        assertState(validNum + validNum2, "", validNum, validNum2)
    }

    private fun setupAndClick(firstNumberVal: String, secondNumberVal: String) {
        onIdle()
        onView(FIRST_NUMBER).perform(replaceText(firstNumberVal))
        onView(SECOND_NUMBER).perform(replaceText(secondNumberVal))
        onView(CTA).perform(click())
        onView(LOADING).check(matches(isDisplayed()))
    }

    private fun assertState(
        expectedResult: String,
        expectedError: String,
        expectedFirstNum: String,
        expectedSecondNum: String
    ) {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val resultResDisplay = if (expectedResult == "") not(isDisplayed()) else isDisplayed()
        val resultErrDisplay = if (expectedError == "") not(isDisplayed()) else isDisplayed()

        assertEquals("com.example.sumtest", appContext.packageName)
//      Assert Expected number values display
        assertMultiMatcherToId(
            FIRST_NUMBER, listOf(
                withText(expectedFirstNum), isDisplayed(),
                withHint(EXPECTED_NUMBER_HINT)
            )
        )
        assertMultiMatcherToId(
            SECOND_NUMBER, listOf(
                withText(expectedSecondNum), isDisplayed(),
                withHint(EXPECTED_NUMBER_HINT)
            )
        )
        assertMultiMatcherToId(
            CTA,
            listOf(withText(EXPECTED_CTA_HINT), isDisplayed(), isClickable())
        )
//      Assert values after loading bar terminates
        InstrumentationRegistry.getInstrumentation().waitForIdle {
            if (expectedResult != "" || expectedError != "") {
                onView(LOADING).check(matches(not(isDisplayed())))
                assertMultiMatcherToId(
                    FIRST_NUMBER, listOf(
                        withText(""), isDisplayed(),
                        withHint(EXPECTED_NUMBER_HINT)
                    )
                )
                assertMultiMatcherToId(
                    SECOND_NUMBER, listOf(
                        withText(""), isDisplayed(),
                        withHint(EXPECTED_NUMBER_HINT)
                    )
                )
            }
            assertMultiMatcherToId(RESULT, listOf(withText(expectedResult), resultResDisplay))
            assertMultiMatcherToId(ERROR, listOf(withText(expectedError), resultErrDisplay))
        }
    }

    private fun assertInitialState() {
        assertState("", "", "", "")
        onView(LOADING).check(matches(not(isDisplayed())))
    }

    private fun assertMultiMatcherToId(
        viewMatcher: Matcher<View>,
        matcherList: List<Matcher<View>>
    ) {
        matcherList.forEach { matcher ->
            onView(viewMatcher).check(matches(matcher))
        }
    }
}