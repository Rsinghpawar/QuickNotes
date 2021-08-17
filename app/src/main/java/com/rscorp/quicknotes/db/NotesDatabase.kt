package com.rscorp.quicknotes.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rscorp.quicknotes.db.models.CurrentNoteData
import com.rscorp.quicknotes.db.models.DatesNotesTable


@Database(entities = [DatesNotesTable::class , CurrentNoteData::class] , version = 2)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun getNotesDao() : NotesDao
    abstract fun getCurrentNotesDao() : CurrentNotesDao
}