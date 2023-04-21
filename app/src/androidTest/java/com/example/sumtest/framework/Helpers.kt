package com.example.sumtest.framework

import android.view.View
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher


fun hasTextInputLayoutHintText(expectedErrorText: String): Any = object : TypeSafeMatcher<View>() {

    override fun describeTo(description: Description?) { }

    override fun matchesSafely(item: View?): Boolean {
        if (item !is TextInputLayout) return false
        val error = item.hint ?: return false
        val hint = error.toString()
        return expectedErrorText == hint
    }
}