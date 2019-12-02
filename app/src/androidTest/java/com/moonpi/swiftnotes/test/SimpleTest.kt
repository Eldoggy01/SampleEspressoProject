package com.moonpi.swiftnotes.test

import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.espresso.action.ScrollToAction
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.test.uiautomator.UiCollection
import com.moonpi.swiftnotes.MainActivity
import com.moonpi.swiftnotes.R
import com.moonpi.swiftnotes.pages.EditNoteScreen
import com.moonpi.swiftnotes.pages.MainScreen
import com.moonpi.swiftnotes.rule.SwiftnotesRule
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.tinkoff.allure.android.deviceScreenshot
import ru.tinkoff.allure.annotations.DisplayName
import ru.tinkoff.allure.step

@RunWith(AndroidJUnit4::class)
@DisplayName("Создание заметки")
class SimpleTest : AbstractSwiftnotesTest() {

    @get:Rule
    val rule = SwiftnotesRule(MainActivity::class.java, false)

    @Test
    @DisplayName("Проверка экрана создания заметки")
    fun newNoteScreenTest() {
        rule.launchActivity()
        step("Проверяем отображение страницы") {
            MainScreen().clickNewNote()
            with(EditNoteScreen()) {
                checkNoteTitleHasHint("Title")
                checkNoteBodyHasHint("Note")
            }
            deviceScreenshot("page_display")
        }

        step("Проверка отображения диалога о сохранении изменений") {
            with(EditNoteScreen()) {
                clickBack()
                checkSaveChangesMessage("Save changes?")
                checkYesButtonOnSaveAlertDisplayed()
                checkNoButtonOnSaveAlertDisplayed()
                clickNoButtonOnSaveAlert()
                deviceScreenshot("page_display")
            }
        }

        step("Проверка отображения главного экрана после возвращения") {
            MainScreen().checkIsTitleDisplayed()
            deviceScreenshot("page_display")
        }
    }


    @Test
    @DisplayName("Проверка сохранения заметки")
    fun saveNoteTest() {
        val noteTitleText = "MyTitle1"
        val noteBodyText = "MyNoteBody"
        rule.launchActivity()
        step("Проверяем отображение страницы создания") {
            MainScreen().clickNewNote()
            with(EditNoteScreen()) {
                setNoteTitleText(noteTitleText)
                setNoteBodyText(noteBodyText)
                checkNoteTitleHasText(noteTitleText)
                checkNoteBodyHasText(noteBodyText)
                deviceScreenshot("page_display")
            }
        }

        step("Возвращаемся на главную страницу, сохраняя заметку.") {
            with(EditNoteScreen()) {
                clickBack()
                clickYesButtonOnSaveAlert()
                deviceScreenshot("page_display")
            }
        }

        step("Проверяем отображение сохраненной заметки на главной странице") {
            with(MainScreen()) {
                checkIsTitleDisplayed()
                checkRowExist(noteTitleText, noteBodyText)
                deviceScreenshot("page_display")
            }
        }

    }

    @Test
    @DisplayName("Проверка пунктов меню в тулбаре")
    fun toolbarMenuTest() {
        rule.launchActivity()
        step("Проверяем отображение пунктов меню: \"Backup notes\", \"Restore notes\", \"Rate app\"") {
            with(MainScreen()) {
                clickMoreOptionsButton()
                checkBackupNotesButtonDisplayed()
                checkRestoreNotesButtonDisplayed()
                checkRateAppButtonDisplayed()
                pressBack() //костыль, чтобы скрыть пункты меню.
            }
        }
        step("Открываем меню") {
            MainScreen().clickNewNote()
        }

        step("Проверяем отображение пунктов меню: \"Note font size\", \"Hide note body in list\"") {
            with(EditNoteScreen()) {
                clickMoreOptionsButton()
                checkNoteFontSizeButtonDisplayed()
                checkhideNoteBodyButtonDisplayed()
            }
        }
    }

    @Test
    @DisplayName("Проверка удаления заметки")
    fun deleteNoteTest() {
        rule.launchActivity()
        val noteTitle: String = "Title1"
        val noteBody: String = "Body1"
        step("Заполняем новую заметку") {
            MainScreen().clickNewNote()
            with(EditNoteScreen()) {
                setNoteTitleText(noteTitle)
                setNoteBodyText(noteBody)
                deviceScreenshot("page_display")
            }
        }

        step("Возвращаемся на главную страницу, сохраняя заметку.") {
            with(EditNoteScreen()) {
                clickBack()
                clickYesButtonOnSaveAlert()
                deviceScreenshot("page_display")
            }
        }

        step("Лонг тап на заметку на главном экране") {
            MainScreen().longClickRow(noteTitle, noteBody)
            deviceScreenshot("page_display")
        }

        step("В тулбаре нажать \"Удалить\"") {
            MainScreen().clickDelete()
            deviceScreenshot("page_display")
        }

        step("На алерте об удалении нажать \"ОК\"") {
            MainScreen().clickOkOnDeleteAlert()
            deviceScreenshot("page_display")
        }

        step("Проверяем отсутствие заметки на главном экране") {
            MainScreen().checkRowNotExist(noteTitle, noteBody)
        } //TODO  метод checkRowNotExist почему-то нормально не работает.
    }
}
