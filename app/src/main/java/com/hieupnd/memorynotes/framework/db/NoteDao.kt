package com.hieupnd.memorynotes.framework.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NoteDao {

    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun getNoteEntity(id: Long) : NoteEntity

    @Query("SELECT * FROM note")
    suspend fun getAllNoteEntity() : List<NoteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNoteEntity(noteEntity: NoteEntity)

    @Delete
    suspend fun deleteNoteEntity(noteEntity: NoteEntity)
}