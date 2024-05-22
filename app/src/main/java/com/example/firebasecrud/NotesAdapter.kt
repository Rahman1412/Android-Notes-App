package com.example.firebasecrud

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(private var notes : List<Note>, context: Context):
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

        private val db : NotesDbHelper = NotesDbHelper(context)
    private var toast : Toast? = null
    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val title : TextView = itemView.findViewById(R.id.title)
        val description : TextView = itemView.findViewById(R.id.description)
        val editNote : ImageView = itemView.findViewById(R.id.editNote)
        val deleteNote :ImageView = itemView.findViewById(R.id.deleteNote)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notes_item,parent,false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.title.text = note.title;
        holder.description.text = note.description;

        holder.editNote.setOnClickListener{
            val intent = Intent(holder.itemView.context,UpdateNote::class.java).apply {
                putExtra("id",note.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteNote.setOnClickListener{
            db.deleteNote(note.id)
            refreshData(db.getAllNotes())
            toast?.cancel()
            toast = Toast.makeText(holder.itemView.context,"Note Deleted Successfully!!",Toast.LENGTH_SHORT)
            toast?.show()
        }


    }


    fun refreshData(newNotes: List<Note>){
        notes = newNotes;
        notifyDataSetChanged()
    }
}