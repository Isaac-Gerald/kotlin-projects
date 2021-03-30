package com.example.notekeeperkotlin.appwidget

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.example.notekeeperkotlin.DataManager
import com.example.notekeeperkotlin.NOTE_POSITION
import com.example.notekeeperkotlin.R

class AppWidgetRemoteViewsFactory(val context: Context): RemoteViewsService.RemoteViewsFactory {
    override fun onCreate() {

    }

    override fun onDataSetChanged() {

    }

    override fun onDestroy() {

    }

    override fun getCount(): Int {
        return DataManager.notes.size
    }

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(context.packageName, R.layout.item_note_widget)
        rv.setTextViewText(R.id.note_title, DataManager.notes[position].text)

        val extras = Bundle()
        extras.putInt(NOTE_POSITION, position)
        val fillIntent = Intent()
        fillIntent.putExtras(extras)
        rv.setOnClickFillInIntent(R.id.widget_item, fillIntent)

        return rv
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }
}