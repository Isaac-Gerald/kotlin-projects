package com.example.notekeeperkotlin

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notekeeperkotlin.constants.NOTE_POSITION

class NoteRecyclerAdapter(private val context: Context, private val notes: List<NoteInfo>)
    : RecyclerView.Adapter<NoteRecyclerAdapter.ViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)
    private var onNoteSelectedListener : OnNoteSelectedListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = layoutInflater.inflate(R.layout.item_note_list, parent, false)

             return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notes[position]
        holder.textCourse.text = note.course?.title
        holder.textNote.text = note.title
        holder.notePosition = position
        holder.color.setBackgroundColor(note.color)
    }

    fun setNoteSelectedListener(listener: OnNoteSelectedListener){
        onNoteSelectedListener = listener
    }

    override fun getItemCount() = notes.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textCourse = itemView.findViewById<TextView>(R.id.textCourse)
        val textNote = itemView.findViewById<TextView>(R.id.textTitle)
        val color = itemView.findViewById<View>(R.id.noteColor)
        var notePosition = 0

        init {
            itemView.setOnClickListener{view ->
                onNoteSelectedListener?.onNoteSelected(notes[notePosition])
                val intent = Intent(context, NoteActivity::class.java)
                intent.putExtra(NOTE_POSITION, notePosition)
                context.startActivity(intent)
            }
        }

    }

    interface  OnNoteSelectedListener{
        fun onNoteSelected(note : NoteInfo)
    }
}