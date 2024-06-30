package com.hieupnd.memorynotes.framework.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.hieupnd.core.data.Note
import com.hieupnd.core.repository.NoteDataSource
import com.hieupnd.core.repository.NoteRepository
import com.hieupnd.core.usecase.AddNote
import com.hieupnd.core.usecase.GetAllNote
import com.hieupnd.core.usecase.GetNote
import com.hieupnd.core.usecase.RemoveNote
import com.hieupnd.memorynotes.framework.RoomNoteDataSource
import com.hieupnd.memorynotes.framework.UserCases
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application): AndroidViewModel(application) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val reponsitory = NoteRepository(RoomNoteDataSource(application))

    private val userCases = UserCases(
        AddNote(reponsitory),
        GetAllNote(reponsitory),
        GetNote(reponsitory),
        RemoveNote(reponsitory)
    )

    val saved = MutableLiveData<Boolean>()

    fun saveNote(note: Note) {
        coroutineScope.launch {
            userCases.addNote(note)
            saved.postValue(true)
        }
    }
}