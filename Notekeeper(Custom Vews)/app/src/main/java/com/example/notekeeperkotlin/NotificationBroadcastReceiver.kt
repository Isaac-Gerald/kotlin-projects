package com.example.notekeeperkotlin

import android.app.RemoteInput
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.notekeeperkotlin.constants.NOTE_POSITION
import com.example.notekeeperkotlin.constants.POSITION_NOT_SET
import com.example.notekeeperkotlin.notification.ReminderNotification

class NotificationBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val bundle = RemoteInput.getResultsFromIntent(intent)
        if (bundle != null) {
            val notePosition = intent.getIntExtra(NOTE_POSITION, POSITION_NOT_SET)
            val text = bundle
                .getCharSequence(ReminderNotification.KEY_TEXT_REPLY)?.toString() ?: ""
            DataManager.notes[notePosition].comments?.add(
                0,
                NoteComment("Gerald", text, System.currentTimeMillis())
            )
            ReminderNotification.notify(
                context,
                DataManager.notes[notePosition],
                notePosition
            )
            bundle.clear()
        }
    }
}