package com.hieupnd.memorynotes.framework

import android.content.Context
import com.hieupnd.core.data.Note
import com.hieupnd.core.repository.NoteDataSource
import com.hieupnd.memorynotes.framework.db.DatabaseService
import com.hieupnd.memorynotes.framework.db.NoteEntity

class RoomNoteDataSource(context: Context) : NoteDataSource {
    private val noteDao = DatabaseService.getInstance(context).noteDao()

    override suspend fun add(note: Note) = noteDao.addNoteEntity(noteEntity = NoteEntity.fromNote(note))

    override suspend fun get(id: Long): Note = noteDao.getNoteEntity(id).toNote()

    override suspend fun getAll(): List<Note> = noteDao.getAllNoteEntity().map { it.toNote() }

    override suspend fun remove(note: Note) = noteDao.deleteNoteEntity(noteEntity = NoteEntity.fromNote(note))
}