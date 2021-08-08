package com.rscorp.quicknotes.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rscorp.quicknotes.db.models.NoteData

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveNotes(data : NoteData) : Long

    @Query("SELECT * FROM notes_table")
    fun getNotes() : LiveData<List<NoteData>>
}