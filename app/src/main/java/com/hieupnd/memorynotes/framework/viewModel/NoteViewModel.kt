package com.hieupnd.memorynotes.framework.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.hieupnd.memorynotes.framework.DataSource.RoomNoteDataSource
import com.hieupnd.core.data.Note
import com.hieupnd.core.repository.NoteRepository
import com.hieupnd.core.usecase.AddNote
import com.hieupnd.core.usecase.GetAllNote
import com.hieupnd.core.usecase.GetNote
import com.hieupnd.core.usecase.RemoveNote
import com.hieupnd.core.usecase.UserCases

class NoteViewModel(application: Application): AndroidViewModel(application) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val repository = NoteRepository(RoomNoteDataSource(application))

    private val userCases = UserCases(
        AddNote(repository),
        GetAllNote(repository),
        GetNote(repository),
        RemoveNote(repository)
    )

    val saved = MutableLiveData<Boolean>()
    val noteLiveData = MutableLiveData<Note>()

    fun isUpdatedNote(currentNote: Note, newTitle: String, newContent: String) =
        currentNote.id != 0L && (newContent != currentNote.content || newTitle != currentNote.title)

    fun saveNote(note: Note) {
        coroutineScope.launch {
            userCases.addNote(note)
            saved.postValue(true)
        }
    }

    fun getNote(id: Long) {
        coroutineScope.launch {
            val note = userCases.getNote(id) ?: Note()
            noteLiveData.postValue(note)
        }
    }

    fun deleteNote(note: Note) {
        coroutineScope.launch {
            userCases.removeNote(note)
            saved.postValue(true)
        }
    }
}