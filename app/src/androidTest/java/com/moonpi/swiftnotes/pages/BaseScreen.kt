package com.moonpi.swiftnotes.pages

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.view.View
import com.moonpi.swiftnotes.R
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.endsWith
import org.hamcrest.Matcher

abstract class BaseScreen {
    private val moreOptionsButtonMatcher = withContentDescription("More options")

    fun clickBack() {
        onView(allOf(withParent(withId(R.id.toolbarEdit)), withClassName(endsWith("ImageButton")))).perform(click())
    }

    fun clickMoreOptionsButton() {
        onView(moreOptionsButtonMatcher).perform(click())
    }

    fun checkIsViewDisplayed(viewMatcher: Matcher<View>) {
        onView(viewMatcher).check(matches(isDisplayed()))
    }
}