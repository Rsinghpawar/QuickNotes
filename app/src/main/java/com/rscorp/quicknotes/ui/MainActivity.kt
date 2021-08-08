package com.rscorp.quicknotes.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.rscorp.quicknotes.R
import com.rscorp.quicknotes.adapters.MainAdapter
import com.rscorp.quicknotes.db.NotesDao
import com.rscorp.quicknotes.db.models.CurrentNoteData
import com.rscorp.quicknotes.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()
    lateinit var adapter: MainAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val sharedPref = getSharedPreferences("Myshared" , MODE_PRIVATE)
//        val text = sharedPref.getString("string" , "NOthing added")
//        view.text = text
        observeData()
    }

    private fun setUpAdapter(list: List<CurrentNoteData>) {
        adapter = MainAdapter()
        val view = findViewById<RecyclerView>(R.id.rv_main)
        view.adapter = adapter
        adapter.setListData(list)
    }

    private fun observeData() {
        viewModel.quickNotes.observe(this, {
            setUpAdapter(it)
        })
    }
}