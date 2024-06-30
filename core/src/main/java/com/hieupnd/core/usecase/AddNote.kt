package com.hieupnd.core.usecase

import com.hieupnd.core.data.Note
import com.hieupnd.core.repository.NoteRepository

class AddNote(private val repository: NoteRepository) {
    suspend operator fun invoke(note: Note) = repository.addNote(note)
}