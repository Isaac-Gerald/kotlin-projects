package com.example.notekeeperkotlin

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.jwhh.notekeeper.CourseRecyclerAdapter
import org.hamcrest.CoreMatchers.containsString
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationTest {
    @Rule
    @JvmField
    val itemsActivity = ActivityTestRule(ItemsActivity::class.java)


    @Test
    fun selectNoteAfterNavigationDrawerChanged(){

      onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())

        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.action_courses))

        val coursePosition = 0

        onView(withId(R.id.listNotes))
            .perform(RecyclerViewActions.actionOnItemAtPosition<CourseRecyclerAdapter.ViewHolder>(coursePosition, click()))

        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())

        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.action_notes))

        val notesPosition = 0

        onView(withId(R.id.listNotes)).perform(RecyclerViewActions.actionOnItemAtPosition<NoteRecyclerAdapter.ViewHolder>(notesPosition, click()))

        val notes = DataManager.notes[notesPosition]

        onView(withId(R.id.spinnerCourses)).check(matches(withSpinnerText(containsString(notes.course?.title))))
        onView(withId(R.id.textNoteTitle)).check(matches(withText(containsString(notes.title))))
        onView(withId(R.id.textNoteText)).check(matches(withText(containsString(notes.text))))
    }

}