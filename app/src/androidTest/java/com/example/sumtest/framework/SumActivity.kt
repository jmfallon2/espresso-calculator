package com.example.sumtest.framework

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers
import com.example.sumtest.R
import org.hamcrest.Matcher

//Inherit from Parent activities if logical
class SumActivity {
    companion object {
        val FIRST_NUMBER: Matcher<View> = ViewMatchers.withId(R.id.firstNumber)
        val SECOND_NUMBER: Matcher<View> = ViewMatchers.withId(R.id.secondNumber)
        val CTA: Matcher<View> = ViewMatchers.withId(R.id.cta)
        val RESULT: Matcher<View> = ViewMatchers.withId(R.id.result)
        val ERROR: Matcher<View> = ViewMatchers.withId(R.id.error)
        val LOADING: Matcher<View> = ViewMatchers.withId(R.id.loading)
        const val EXPECTED_NUMBER_HINT = "Enter value"
        const val EXPECTED_CTA_HINT = "ADD"
    }
}

