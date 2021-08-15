package com.rscorp.quicknotes.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rscorp.quicknotes.db.models.CurrentNoteData
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrentNotesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveNotes(data : CurrentNoteData) : Long

    @Query("SELECT * FROM current_notes_table")
    fun getNotes() : LiveData<List<CurrentNoteData>>

    @Query("SELECT * FROM current_notes_table WHERE title LIKE :query OR tag LIKE :query OR date LIKE :query")
    fun searchNotes(query : String) : Flow<List<CurrentNoteData>>
}