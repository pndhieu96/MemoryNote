package com.hieupnd.core.usecase

import com.hieupnd.core.usecase.AddNote
import com.hieupnd.core.usecase.GetAllNote
import com.hieupnd.core.usecase.GetNote
import com.hieupnd.core.usecase.RemoveNote

data class UserCases(
    val addNote: AddNote,
    val getAllNote: GetAllNote,
    val getNote: GetNote,
    val removeNote: RemoveNote
)