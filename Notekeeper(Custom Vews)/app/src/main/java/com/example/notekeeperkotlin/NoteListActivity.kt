package com.example.notekeeperkotlin

import android.content.Intent
import android.media.MediaCodec
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_note_list.*
import kotlinx.android.synthetic.main.content_note_list.*

class NoteListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)
        setSupportActionBar(findViewById(R.id.toolbar))

        fab.setOnClickListener { view ->
            val activityIntent = Intent(this, NoteActivity::class.java)
            startActivity(activityIntent)
        }

        listNotes.layoutManager = LinearLayoutManager(this)

        val adapter = NoteRecyclerAdapter(this, DataManager.notes)

        listNotes.adapter = adapter


    }

    override fun onResume() {
        super.onResume()
       listNotes.adapter?.notifyDataSetChanged()
    }



}