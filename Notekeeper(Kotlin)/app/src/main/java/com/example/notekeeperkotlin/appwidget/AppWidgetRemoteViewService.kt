package com.example.notekeeperkotlin.appwidget

import android.content.Intent
import android.widget.RemoteViewsService

class AppWidgetRemoteViewService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return AppWidgetRemoteViewsFactory(applicationContext)
    }
}