package com.rscorp.quicknotes.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dates_notes_table")
data class DatesNotesTable(
    @PrimaryKey(autoGenerate = true)
    var id : Int ,
    var title : String ,
    var description : String ,
    var date : String
)