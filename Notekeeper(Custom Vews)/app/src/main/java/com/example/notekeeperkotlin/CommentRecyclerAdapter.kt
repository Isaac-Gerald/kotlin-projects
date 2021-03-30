package com.jwhh.notekeeper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notekeeperkotlin.NoteInfo
import com.example.notekeeperkotlin.R

class CommentRecyclerAdapter(val context: Context, val note: NoteInfo) : RecyclerView.Adapter<CommentRecyclerAdapter.ViewHolder>() {
  private val layoutInflater = LayoutInflater.from(context)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val itemView = layoutInflater.inflate(R.layout.item_comment, parent, false)
    return ViewHolder(itemView)
  }

  override fun getItemCount(): Int {
    return note.comments?.size?: 0
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.name.text = note.comments?.get(position)?.name ?:"You"
    holder.comment.text = note.comments?.get(position)?.comment ?: null
  }

  inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val name: TextView = itemView.findViewById(R.id.name)
    val comment: TextView = itemView.findViewById(R.id.comment)
  }
}