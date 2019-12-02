package com.moonpi.swiftnotes.pages

//import android.support.test.espresso.Espresso.onData
//import android.support.test.espresso.Espresso.onView
//import android.support.test.espresso.action.ViewActions.*
//import android.support.test.espresso.assertion.ViewAssertions.matches
//import android.support.test.espresso.matcher.ViewMatchers.*
//import com.moonpi.swiftnotes.R
//import org.hamcrest.CoreMatchers.*
//import org.hamcrest.Matchers.hasEntry
//import org.json.JSONArray


//import org.hamcrest.CoreMatchers.*


import android.support.test.espresso.Espresso.onData
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.longClick
import android.support.test.espresso.assertion.ViewAssertions.doesNotExist
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.BoundedMatcher
import android.support.test.espresso.matcher.ViewMatchers.*
import com.moonpi.swiftnotes.R
import org.hamcrest.CoreMatchers
import org.hamcrest.Description
import org.hamcrest.Matchers.*
import org.json.JSONException
import org.json.JSONObject


class MainScreen : BaseScreen() {
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

    fun checkIsTitleDisplayed() {
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

    //FIXME Метод работает. Но если вдруг передать данные несуществующей
    //     * записи, то после того, как матчер ничего не находит,Espresso выбрасывает неочевидное исключение
    //     * которое ни о чем не говорит. PerformException: Error performing 'load adapter data' on view
    /**
     * Метод для проверки наличия записи в длинном списке записей. Е
     **/
    fun checkRowExist(titleString: String, bodyString: String) {
        onData(object : BoundedMatcher<Any?, JSONObject>(JSONObject::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("Matching to JSONObject")
            }

            override fun matchesSafely(jsonObject: JSONObject): Boolean {
                return try {
                    titleString == jsonObject.getString("title")
                    bodyString == jsonObject.getString("body")
                } catch (e: JSONException) {
                    false
                }
            }
        }).check(matches(isDisplayed()))

    }

    //FIXME аналогично, если вдруг матчер не прошел, то выбрасывается непонятное исключение  PerformException: Error performing 'load adapter data' on view
    fun longClickRow(titleString: String, bodyString: String) {
        onData(object : BoundedMatcher<Any?, JSONObject>(JSONObject::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("Matching to JSONObject")
            }

            override fun matchesSafely(jsonObject: JSONObject): Boolean {
                return try {
                    titleString == jsonObject.getString("title")
                    bodyString == jsonObject.getString("body")
                } catch (e: JSONException) {
                    false
                }
            }
        }).perform(longClick())

    }


    //FIXME Метод не работает как надо  PerformException: Error performing 'load adapter data' on view
    fun checkRowNotExist(titleString: String, bodyString: String) {
        onData(object : BoundedMatcher<Any?, JSONObject>(JSONObject::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("Matching to JSONObject")
            }

            override fun matchesSafely(jsonObject: JSONObject): Boolean {
                return try {
                    titleString == jsonObject.getString("title")
                    bodyString == jsonObject.getString("body")
                } catch (e: JSONException) {
                    false
                }
            }
        }).check(doesNotExist())

    }
}