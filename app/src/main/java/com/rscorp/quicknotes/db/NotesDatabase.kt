package com.rscorp.quicknotes.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rscorp.quicknotes.db.models.CurrentNoteData
import com.rscorp.quicknotes.db.models.NoteData


@Database(entities = [NoteData::class , CurrentNoteData::class] , version = 1)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun getNotesDao() : NotesDao
    abstract fun getCurrentNotesDao() : CurrentNotesDao
}