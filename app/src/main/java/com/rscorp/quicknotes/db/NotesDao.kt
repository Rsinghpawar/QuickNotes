package com.rscorp.quicknotes.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rscorp.quicknotes.db.models.DatesNotesTable

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveNotes(data : DatesNotesTable) : Long

    @Query("SELECT * FROM current_notes_table")
    fun getNotes() : LiveData<List<DatesNotesTable>>
}