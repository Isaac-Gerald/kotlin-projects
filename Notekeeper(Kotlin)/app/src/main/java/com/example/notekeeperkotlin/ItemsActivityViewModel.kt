package com.example.notekeeperkotlin

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel

class ItemsActivityViewModel: ViewModel() {
    private val tag = this::class.simpleName
    private val maxRecentlyViewedNotes = 5

    val recentlyViewedNotes = ArrayList<NoteInfo>(maxRecentlyViewedNotes)
    var isNewlyCreated = true

    val recentlyViewedNoteIdsName =
        "com.example.notekeeperkotlin.ItemsActivityViewModel.recentlyViewedNoteIdsName"

    private val navDrawerSelectionDisplayName =
        "com.example.notekeeperkotlin.ItemsActivityViewModel.navDrawerDisplaySelection"
    var navDrawerDisplaySelection = R.id.action_notes


     fun addToRecentlyViewedNotes(note: NoteInfo) {
        // Check if selection is already in the list
        val existingIndex = recentlyViewedNotes.indexOf(note)
        Log.d(tag, "addToRecentlyViewedNotes: called")
        if (existingIndex == -1) {
            // it isn't in the list...
            // Add new one to beginning of list and remove any beyond max we want to keep
            recentlyViewedNotes.add(0, note)
            for (index in recentlyViewedNotes.lastIndex downTo maxRecentlyViewedNotes)
                recentlyViewedNotes.removeAt(index)
            Log.d(tag, "addToRecentlyViewedNotes: ${recentlyViewedNotes[0]}")
        } else {
            // it is in the list...
            // Shift the ones above down the list and make it first member of the list
            for (index in (existingIndex - 1) downTo 0)
                recentlyViewedNotes[index + 1] = recentlyViewedNotes[index]
            recentlyViewedNotes[0] = note
            Log.d(tag, "addToRecentlyViewedNotes: ${recentlyViewedNotes[0]}")
        }
    }

    fun saveState(outState: Bundle) {
        outState.putInt(navDrawerSelectionDisplayName, navDrawerDisplaySelection)
        val noteIds = DataManager.noteIdsAsIntArray(recentlyViewedNotes)
        outState.putIntArray(recentlyViewedNoteIdsName, noteIds)
    }

    fun restoreState(savedInstanceState: Bundle) {
        navDrawerDisplaySelection = savedInstanceState.getInt(navDrawerSelectionDisplayName)
        val notedIds = savedInstanceState.getIntArray(recentlyViewedNoteIdsName)
        val noteList = DataManager.loadNotes(*notedIds!!)
        recentlyViewedNotes.addAll(noteList)
    }
}