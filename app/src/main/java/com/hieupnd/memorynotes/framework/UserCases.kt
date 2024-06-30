package com.hieupnd.memorynotes.framework

import com.hieupnd.core.usecase.AddNote
import com.hieupnd.core.usecase.GetAllNote
import com.hieupnd.core.usecase.GetNote
import com.hieupnd.core.usecase.RemoveNote

data class UserCases(
    val addNote: AddNote,
    val getAddNote: GetAllNote,
    val getNote: GetNote,
    val removeNote: RemoveNote
)