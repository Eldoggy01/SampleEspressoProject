package com.moonpi.swiftnotes.test

import android.support.test.espresso.Espresso.pressBack
import android.support.test.runner.AndroidJUnit4
import com.moonpi.swiftnotes.MainActivity
import com.moonpi.swiftnotes.pages.EditNoteScreen
import com.moonpi.swiftnotes.pages.MainScreen
import com.moonpi.swiftnotes.rule.SwiftnotesRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.tinkoff.allure.annotations.DisplayName

@RunWith(AndroidJUnit4::class)
@DisplayName("Создание заметки")
class SimpleTests : AbstractSwiftnotesTest() {

    @get:Rule
    val rule = SwiftnotesRule(MainActivity::class.java, false)

    @Test
    @DisplayName("Проверка экрана создания заметки")
    fun newNoteScreenTest() {
        rule.launchActivity()
        MainScreen().clickNewNote()
        with(EditNoteScreen()) {
            checkNoteTitleHasHint("Title")
            checkNoteBodyHasHint("Note")
            clickBack()
            checkSaveChangesMessage("Save changes?")
            checkYesButtonOnSaveAlertDisplayed()
            checkNoButtonOnSaveAlertDisplayed()
            clickNoButtonOnSaveAlert()
        }

        MainScreen().checkTitleDisplayed()
    }

    @Test
    @DisplayName("Проверка сохранения заметки")
    fun saveNoteTest() {
        val noteTitleText = "MyTitle1"
        val noteBodyText = "MyNoteBody"
        rule.launchActivity()
        MainScreen().clickNewNote()
        with(EditNoteScreen()) {
            setNoteTitleText(noteTitleText)
            setNoteBodyText(noteBodyText)
            checkNoteTitleHasText(noteTitleText)
            checkNoteBodyHasText(noteBodyText)
            clickBack()
            clickYesButtonOnSaveAlert()
        }

        with(MainScreen()) {
            checkTitleDisplayed()
            checkRowExist(noteTitleText, noteBodyText)
        }
    }

    @Test
    @DisplayName("Проверка пунктов меню в тулбаре")
    fun toolbarMenuTest() {
        rule.launchActivity()
        with(MainScreen()) {
            clickMoreOptionsButton()
            checkBackupNotesButtonDisplayed()
            checkRestoreNotesButtonDisplayed()
            checkRateAppButtonDisplayed()
            pressBack()
            clickNewNote()
        }

        with(EditNoteScreen()) {
            clickMoreOptionsButton()
            checkNoteFontSizeButtonDisplayed()
            checkhideNoteBodyButtonDisplayed()
        }
    }
}
