package com.example.notekeeperkotlin.notification

import android.annotation.TargetApi
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import androidx.core.content.ContextCompat
import com.example.notekeeperkotlin.*
import com.example.notekeeperkotlin.constants.NOTE_POSITION

/**
 * Helper class for showing and canceling reminder
 * notifications.
 *
 *
 * This class makes heavy use of the [NotificationCompat.Builder] helper
 * class to create notifications in a backward-compatible way.
 */
object ReminderNotification {
    /**
     * The unique identifier for this type of notification.
     */
    private const val NOTIFICATION_TAG = "Reminder"

    const val REMINDER_CHANNEL = "reminders"

    const val KEY_TEXT_REPLY = "keyTextReply"

    /**
     * Shows the notification, or updates a previously shown notification of
     * this type, with the given parameters.
     *
     * @see .cancel
     */
    fun notify(context: Context, note: NoteInfo, notePosition: Int) {

        val noteQuickIntent = NoteQuickViewActivity.getIntent(context, notePosition)
        noteQuickIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val noteQuickPendingIntent = PendingIntent.getActivity(
            context,
            0,
            noteQuickIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val remoteInput = RemoteInput.Builder(KEY_TEXT_REPLY)
            .setLabel("Add note")
            .build()

        val replyIntent = Intent(context, NotificationBroadcastReceiver::class.java)
        replyIntent.putExtra(NOTE_POSITION, notePosition)

        val replyPendingIntent = PendingIntent.getBroadcast(
            context,
            100,
            replyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val replyAction = NotificationCompat
            .Action
            .Builder(R.drawable.ic_reply_black_24, "Add Note", replyPendingIntent)
            .addRemoteInput(remoteInput)
            .build()

        val intent = Intent(context, NoteActivity::class.java)
        intent.putExtra(NOTE_POSITION, notePosition)
        //add parent activities to backStack using pending intent
        val pendingIntent = TaskStackBuilder.create(context)
            .addNextIntentWithParentStack(intent)
            .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)


        val shareIntent = PendingIntent.getActivity(
            context, 0,
            Intent.createChooser(
                Intent(Intent.ACTION_SEND)
                    .setType("text/plain")
                    .putExtra(Intent.EXTRA_TEXT, note.text),
                "Share note Reminder"
            ),
            PendingIntent.FLAG_UPDATE_CURRENT
        )


        val builder = NotificationCompat.Builder(context, REMINDER_CHANNEL)

            // Set appropriate defaults for the notification light, sound,
            // and vibration.
            .setDefaults(Notification.DEFAULT_ALL)

            // Set required fields, including the small icon, the
            // notification title, and text.
            .setSmallIcon(R.drawable.ic_assignment_notification_orange)
            .setContentTitle("Comment about " + note.title)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.logo))
            .addAction(replyAction)

            // All fields below this line are optional.

            // Use a default priority (recognized on devices running Android
            // 4.1 or later)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            .setColor(ContextCompat.getColor(context, R.color.pluralsight_orange))
            .setColorized(true)
            .setOnlyAlertOnce(true)

            // Set ticker text (preview) information for this notification.
            .setTicker("Comment about " + note.title)

            // Set the pending intent to be initiated when the user touches
            // the notification.
            .setContentIntent(
                pendingIntent
            )




//        .setStyle(NotificationCompat.BigTextStyle()
//            .bigText("Leverage agile frameWorks to provide a robust synopsis XXXXX XXXXXXX XXXX XXXXXXXX")
//            .setBigContentTitle("Big content title")
//            .setSummaryText("Summary text"))
            // Automatically dismiss the notification when it is touched.
            .setAutoCancel(true)

            // Add a share action
//            .addAction(
//                R.drawable.ic_share_black_24,
//                "Share", shareIntent
//            )

        notify(context, builder.build())
    }

    @TargetApi(Build.VERSION_CODES.ECLAIR)
    private fun notify(context: Context, notification: Notification) {
        val nm = context
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            nm.notify(NOTIFICATION_TAG, 0, notification)
        } else {
            nm.notify(NOTIFICATION_TAG.hashCode(), notification)
        }
    }

    /**
     * Cancels any notifications of this type previously shown using
     * [.notify].
     */
    @TargetApi(Build.VERSION_CODES.ECLAIR)
    fun cancel(context: Context) {
        val nm = context
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            nm.cancel(NOTIFICATION_TAG, 0)
        } else {
            nm.cancel(NOTIFICATION_TAG.hashCode())
        }
    }
}
