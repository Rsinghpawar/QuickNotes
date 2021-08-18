package com.rscorp.quicknotes.viewmodels

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.rscorp.quicknotes.db.CurrentNotesDao
import com.rscorp.quicknotes.db.NotesDao
import com.rscorp.quicknotes.db.models.CurrentNoteData
import com.rscorp.quicknotes.db.models.DatesNotesTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(private val notesDao: NotesDao, private val currentNotesDao : CurrentNotesDao) : ViewModel() {


    var quickNotes : LiveData<List<CurrentNoteData>> = MutableLiveData()
    var searchNotes : LiveData<List<CurrentNoteData>> = MutableLiveData()
    var isEditing : MutableLiveData<Boolean> = MutableLiveData(false)
    var selectedNoteData : CurrentNoteData? = null

    private fun getAllNotes() = viewModelScope.launch {
        quickNotes = currentNotesDao.getNotes()
    }

//    var id : Int ,
//    var title : String ,
//    var description : String ,
//    var currentTimeInMilli : Long,
//    var date : String ,
//    var time : String ,
//    var tag : String ,
//    var color : Int,
//    var icon : Int ,
//    var iconPosition : Int

    fun updateTable(id : Int ,title : String , icon : Int , iconPosition : Int ) = viewModelScope.launch(Dispatchers.IO) {
            currentNotesDao.updateNote(id, title, icon, iconPosition)
    }

    fun searchDatabase(searchQuery: String) =  currentNotesDao.searchNotes(searchQuery).asLiveData()


    init {
        getAllNotes()
    }

}