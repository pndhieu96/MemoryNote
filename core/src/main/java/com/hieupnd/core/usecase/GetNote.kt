package com.hieupnd.core.usecase

import com.hieupnd.core.repository.NoteRepository

class GetNote(private val repository: NoteRepository) {
    suspend operator fun invoke(id: Long) = repository.getNote(id)
}