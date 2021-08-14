package com.rscorp.quicknotes.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rscorp.quicknotes.db.models.CurrentNoteData

@Dao
interface CurrentNotesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveNotes(data : CurrentNoteData) : Long

    @Query("SELECT * FROM current_notes_table")
    fun getNotes() : LiveData<List<CurrentNoteData>>
}