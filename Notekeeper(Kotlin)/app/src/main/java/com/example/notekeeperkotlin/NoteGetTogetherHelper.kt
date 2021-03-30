package com.example.notekeeperkotlin

import android.content.Context
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.jwhh.notekeeper.PseudoLocationManager
import com.jwhh.notekeeper.PseudoMessagingConnection
import com.jwhh.notekeeper.PseudoMessagingManager

class NoteGetTogetherHelper(context: Context, val lifecycle: Lifecycle) : LifecycleObserver {

    init {
        lifecycle.addObserver(this)
    }

    private val tag = this::class.simpleName
    var currentLat = 0.0
    var currentLon = 0.0

    val locManger = PseudoLocationManager(context) { lat, lon ->
        currentLat = lat
        currentLon = lon

        Log.d(tag, "Location callback lat:$currentLat lon:$currentLon")

    }

    val msgManager = PseudoMessagingManager(context)
    var msgConnection: PseudoMessagingConnection? = null

    fun sendMessage(note: NoteInfo) {
        val getTogetherMessage = "$currentLat|$currentLon|${note.title}|${note.text}"
        msgConnection?.send(getTogetherMessage)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun startHandler() {
        Log.d(tag, "startHandler:")
        locManger.start()
        msgManager.connect { connection ->
            Log.d(tag, "startHandler: connection callback - Lifecycle state:${lifecycle.currentState}")
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED))
                msgConnection = connection
            else
                connection.disconnect()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stopHandler() {
        Log.d(tag, "stopHandler:")
        locManger.stop()
        msgConnection?.disconnect()
    }
}