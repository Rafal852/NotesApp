package com.example.todoapp

import android.app.Activity
import android.app.AlertDialog
import android.app.usage.UsageEvents
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.media.metrics.Event
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class NoteAdapter(
    val context: Context,
    val noteOnClickInterface: NoteClickInterface,
    val noteClickDeleteInterface: NoteClickDeleteInterface
): RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    private val allNotes = ArrayList<Note>()


    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val noteTV = itemView.findViewById<TextView>(R.id.tv_noteTitle)
        val timeTV = itemView.findViewById<TextView>(R.id.tv_timeStamp)
        val deleteTV = itemView.findViewById<ImageView>(R.id.tv_delete)
        val descriptionTV = itemView.findViewById<TextView>(R.id.tv_noteDesc)
        val cardViewTV = itemView.findViewById<CardView>(R.id.rv_cardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int    ): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.note_rv_item, parent, false)
        return ViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.noteTV.setText(allNotes.get(position).noteTitle)
        holder.descriptionTV.setText(allNotes.get(position).noteDescription)
        holder.timeTV.setText("Last Updated: " + allNotes.get(position).timeStamp)
        val colors = arrayOf(
            ContextCompat.getColor(context, R.color.RandomBlue),
            ContextCompat.getColor(context, R.color.RandomGreen),
            ContextCompat.getColor(context, R.color.RandomOrange),
            ContextCompat.getColor(context, R.color.RamdomRed),
            ContextCompat.getColor(context, R.color.RandomPink),
            ContextCompat.getColor(context, R.color.RandomPurple)
        )
        val randomColor = colors.random()
        holder.cardViewTV.setCardBackgroundColor(randomColor)

        holder.deleteTV.setOnClickListener {
            deleteEvent(position)
        }

        holder.itemView.setOnClickListener {
            noteOnClickInterface.onNoteClick(allNotes.get(position))
        }
    }
    private fun deleteEvent(position: Int) {
        val builder = AlertDialog.Builder(context, R.style.MyDialogTheme)

        builder.setMessage("Are you sure you want to delete this note?")
            .setCancelable(true)
            .setPositiveButton("Yes") { dialog, id ->
                noteClickDeleteInterface.onDeleteIconClick(allNotes.get(position))
            }
            .setNegativeButton("No") { dialog, id ->

                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()

    }


    override fun getItemCount(): Int {
        return allNotes.size
    }

    fun updateList(newList: List<Note>){
        allNotes.clear()
        allNotes.addAll(newList)
        notifyDataSetChanged()
    }
}

interface NoteClickDeleteInterface{
    fun onDeleteIconClick(note: Note)
}

interface NoteClickInterface{
    fun onNoteClick(note: Note)
}