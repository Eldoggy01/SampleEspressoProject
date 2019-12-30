package com.moonpi.swiftnotes.pages

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.BoundedMatcher
import android.support.test.espresso.matcher.ViewMatchers.*
import android.view.View
import android.widget.AdapterView
import com.moonpi.swiftnotes.R
import org.hamcrest.CoreMatchers
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.json.JSONException
import org.json.JSONObject


class MainScreen : BaseScreen() {
    //FIXME постараться избавиться от текстовок при поиске и вынести их в check()

    private val titleMatcher = allOf(withText("Swiftnotes"))
    private val newNoteMatcher = withId(R.id.newNote)
    private val searchButtonMatcher = withId(R.id.action_search)
    private val noNotesLabelMatcher = allOf(withId(R.id.noNotes), isDisplayed())
    private val noteTitleMatcher = allOf(withId(R.id.titleView), isDisplayed())
    private val noteBodyMatcher = allOf(withId(R.id.bodyView), isDisplayed())
    private val backupNotesButtonMatcher = allOf(withId(R.id.title), withText("Backup notes"))
    private val restoreNotesButtonMatcher = allOf(withId(R.id.title), withText("Restore notes"))
    private val rateAppButtonMatcher = allOf(withId(R.id.title), withText("Rate app"))
    private val deleteMatcher = withId(R.id.action_delete)
    private val deleteYesMatcher = allOf(withClassName(CoreMatchers.endsWith("Button")), withText("OK"))
    private val deleteNoMatcher = allOf(withClassName(CoreMatchers.endsWith("Button")), withText("CANCEL"))


    fun clickDelete() {
        onView(deleteMatcher).perform(click())
    }

    fun clickOkOnDeleteAlert() {
        onView(deleteYesMatcher).perform(click())
    }

    fun checkTitleDisplayed() {
        onView(titleMatcher).check(matches(isDisplayed()))
    }

    fun clickNewNote() {
        onView(newNoteMatcher).perform(click())
    }

    fun checkBackupNotesButtonDisplayed() {
        onView(backupNotesButtonMatcher).check(matches(isDisplayed()))
    }

    fun checkRestoreNotesButtonDisplayed() {
        onView(restoreNotesButtonMatcher).check(matches(isDisplayed()))
    }

    fun checkRateAppButtonDisplayed() {
        onView(rateAppButtonMatcher).check(matches(isDisplayed()))
    }

    fun clickSearchButton() {
        onView(searchButtonMatcher).perform(click())
    }

    fun checkNoNotesLabelHasText(expectedText: String) {
        onView(noNotesLabelMatcher).check(matches(withText(expectedText)))
    }

    fun checkNoteTitleHasText(expectedText: String) {
        onView(noteTitleMatcher).check(matches(withText(expectedText)))
    }

    fun checkNoteBodyHasText(expectedText: String) {
        onView(noteBodyMatcher).check(matches(withText(expectedText)))
    }

//FIXME? На данный момент в случае неудачного поиска выбрасывается исключение с не очень очевидным описанием.
    fun checkRowExist(titleString: String, bodyString: String) {
        onView(withId(R.id.listView)).check(matches(withAdaptedData(withTitleAndBody(titleString, bodyString))))
    }

    private fun withTitleAndBody(titleString: String, bodyString: String): Matcher<Any> {
        return object : BoundedMatcher<Any, JSONObject>(JSONObject::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("Matching to JSONObject")
            }

            override fun matchesSafely(jsonObject: JSONObject): Boolean {
                return try {
                    bodyString == jsonObject.getString("body") &&
                            titleString == jsonObject.getString("title")
                } catch (e: JSONException) {
                    false
                }
            }
        }
    }

    private fun withAdaptedData(dataMatcher: Matcher<Any>): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("with class name: ")
                dataMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                if (view !is AdapterView<*>) {
                    return false
                }
                val adapter = view.adapter
                for (i in 0 until adapter.count) {
                    if (dataMatcher.matches(adapter.getItem(i))) {
                        return true
                    }
                }
                return false
            }
        }
    }
}