package com.rscorp.quicknotes.viewmodels

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rscorp.quicknotes.db.CurrentNotesDao
import com.rscorp.quicknotes.db.NotesDao
import com.rscorp.quicknotes.db.models.CurrentNoteData
import com.rscorp.quicknotes.db.models.DatesNotesTable
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(private val notesDao: NotesDao, private val currentNotesDao : CurrentNotesDao) : ViewModel() {


    var quickNotes : LiveData<List<CurrentNoteData>> = MutableLiveData()


    private fun getAllNotes() = viewModelScope.launch {
        quickNotes = currentNotesDao.getNotes()
    }

    init {
        getAllNotes()
    }

}