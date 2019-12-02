package com.moonpi.swiftnotes.pages

import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.assertion.ViewAssertions.doesNotExist
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.widget.TextView
import com.moonpi.swiftnotes.R
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.*
import org.hamcrest.Matchers

class EditNoteScreen : BaseScreen() {
    private val noteTitleMatcher = allOf(withId(R.id.titleEdit), isDisplayed())
    private val noteBodyMatcher = allOf(withId(R.id.bodyEdit), isDisplayed())
    private val colourButtonMatcher = withId(R.id.action_note_colour)
    private val saveChangesMessageMatcher = allOf(withClassName(endsWith("TextView")), isDisplayed(), withResourceName("message"),
            anyOf(withText(containsString("changes")), withText(containsString("Save"))))
    private val saveChangesYesMatcher = allOf(withClassName(endsWith("Button")), withText("YES"))
    private val saveChangesNoMatcher = allOf(withClassName(endsWith("Button")), withText("NO"))
    private val noteFontSizeButtonMatcher = allOf(withId(R.id.title), withText("Note font size"))
    private val hideNoteBodyButtonMatcher = allOf(withId(R.id.title), withText("Hide note body in list"))


    fun checkNoteFontSizeButtonDisplayed() {
        onView(noteFontSizeButtonMatcher).check(matches(isDisplayed()))
    }

    fun checkhideNoteBodyButtonDisplayed() {
        onView(hideNoteBodyButtonMatcher).check(matches(isDisplayed()))
    }

    fun checkNoteTitleHasText(expectedText: String) {
        onView(noteTitleMatcher).check(ViewAssertions.matches(withText(expectedText)))
    }

    fun checkNoteTitleHasHint(hintText: String) {
        onView(noteTitleMatcher).check(ViewAssertions.matches(withHint(hintText)))
    }

    fun checkNoteBodyHasText(expectedText: String) {
        onView(noteBodyMatcher).check(ViewAssertions.matches(withText(expectedText)))
    }

    fun checkNoteBodyHasHint(hintText: String) {
        onView(noteBodyMatcher).check(ViewAssertions.matches(withHint(hintText)))
    }

    fun setNoteTitleText(text: String) {
        onView(noteTitleMatcher).perform(click()).perform(clearText()).perform(typeText(text))
    }

    fun setNoteBodyText(text: String) {
        onView(noteBodyMatcher).perform(click()).perform(clearText()).perform(typeText(text))
    }

    fun clickColourButton() {
        onView(colourButtonMatcher).perform(click())
    }

    fun checkSaveChangesMessage(expectedText: String) {
        onView(saveChangesMessageMatcher).check(matches(withText(expectedText)))
    }

    fun checkYesButtonOnSaveAlertDisplayed() {
        checkIsViewDisplayed(saveChangesYesMatcher)
    }

    fun checkNoButtonOnSaveAlertDisplayed() {
        checkIsViewDisplayed(saveChangesNoMatcher)
    }

    fun clickNoButtonOnSaveAlert(){
        onView(saveChangesNoMatcher).perform(click())
    }

    fun clickYesButtonOnSaveAlert(){
        onView(saveChangesYesMatcher).perform(click())
    }

}