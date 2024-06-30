package com.hieupnd.core.usecase

import com.hieupnd.core.repository.NoteRepository

class GetAllNote (private val repository: NoteRepository) {
    suspend operator fun invoke() = repository.getAllNote()
}