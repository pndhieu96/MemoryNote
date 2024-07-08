package com.hieupnd.memorynotes.framework.recycleView.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hieupnd.core.data.Note
import com.hieupnd.memorynotes.databinding.ItemNoteBinding
import com.hieupnd.memorynotes.framework.recycleView.callback.ListAction
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NotesListAdapter(var list: MutableList<Note>, val listAction: ListAction) : RecyclerView.Adapter<NotesListAdapter.NoteListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemNoteBinding.inflate(layoutInflater, parent, false)
        return NoteListViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: NoteListViewHolder, position: Int) {
        val note = list[position]
        holder.bind(note)
    }

    fun updateNotes(updateList: List<Note>) {
        list.clear()
        list = updateList.toMutableList()
        notifyDataSetChanged()
    }

    inner class NoteListViewHolder(val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            val dateFormat = SimpleDateFormat("MMM dd, HH:mm:ss", Locale.getDefault())
            val updateDate = Date(note.updateTime)

            binding.tvTitle.text = note.title
            binding.tvContent.text = note.content
            binding.tvDate.text = dateFormat.format(updateDate)

            binding.root.setOnClickListener {
                listAction.onRCItemClick(note.id)
            }
        }
    }
}