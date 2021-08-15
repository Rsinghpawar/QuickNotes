package com.rscorp.quicknotes.viewmodels

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.rscorp.quicknotes.db.CurrentNotesDao
import com.rscorp.quicknotes.db.NotesDao
import com.rscorp.quicknotes.db.models.CurrentNoteData
import com.rscorp.quicknotes.db.models.DatesNotesTable
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(private val notesDao: NotesDao, private val currentNotesDao : CurrentNotesDao) : ViewModel() {


    var quickNotes : LiveData<List<CurrentNoteData>> = MutableLiveData()
    var searchNotes : LiveData<List<CurrentNoteData>> = MutableLiveData()


    private fun getAllNotes() = viewModelScope.launch {
        quickNotes = currentNotesDao.getNotes()
    }

    fun searchNotes(query : String)  {
//        Log.d("TAG", "searchNotes: $query")
//        searchNotes = currentNotesDao.searchNotes(query)
//        Log.d("TAG", "searchNotes: ${currentNotesDao.searchNotes(query).value}")
    }

    fun searchDatabase(searchQuery: String) =  currentNotesDao.searchNotes(searchQuery).asLiveData()


    init {
        getAllNotes()
    }

}