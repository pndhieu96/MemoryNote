package com.hieupnd.memorynotes.framework.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hieupnd.core.data.Note
import com.hieupnd.core.repository.NoteRepository
import com.hieupnd.core.usecase.AddNote
import com.hieupnd.core.usecase.GetAllNote
import com.hieupnd.core.usecase.GetNote
import com.hieupnd.core.usecase.RemoveNote
import com.hieupnd.core.usecase.UserCases
import com.hieupnd.memorynotes.framework.DataSource.RoomNoteDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesListViewModel(application: Application): AndroidViewModel(application) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val repository = NoteRepository(RoomNoteDataSource(application))

    private val userCases = UserCases(
        AddNote(repository),
        GetAllNote(repository),
        GetNote(repository),
        RemoveNote(repository)
    )

    val noteListLiveData = MutableLiveData<List<Note>>()

    fun getAllNote() {
        coroutineScope.launch {
            val noteList = userCases.getAllNote.invoke()
            noteListLiveData.postValue(noteList)
        }
    }
}