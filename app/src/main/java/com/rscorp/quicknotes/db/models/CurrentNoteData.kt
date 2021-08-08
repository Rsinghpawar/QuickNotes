package com.rscorp.quicknotes.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_notes_table")
data class CurrentNoteData(
    @PrimaryKey(autoGenerate = true)
    var id : Int ,
    var title : String ,
    var description : String ,
    var currentTimeInMilli : Long,
    var date : String ,
    var time : String ,
    var tag : String ,
    var color : Int
)