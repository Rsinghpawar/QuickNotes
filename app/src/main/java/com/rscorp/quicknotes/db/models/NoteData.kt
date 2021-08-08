package com.rscorp.quicknotes.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
data class NoteData(
    @PrimaryKey(autoGenerate = true)
    var id : Int ,
    var title : String ,
    var description : String ,
    var date : String
)