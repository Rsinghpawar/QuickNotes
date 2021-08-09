package com.rscorp.quicknotes.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.rscorp.quicknotes.R
import com.rscorp.quicknotes.adapters.MainAdapter
import com.rscorp.quicknotes.databinding.ActivityMainBinding
import com.rscorp.quicknotes.databinding.ItemRvBinding
import com.rscorp.quicknotes.databinding.RvInnerNotesItemBinding
import com.rscorp.quicknotes.db.models.CurrentNoteData
import com.rscorp.quicknotes.util.genericAdapter.GenericAdapter
import com.rscorp.quicknotes.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var dateHeadingAdapter: GenericAdapter<MainActivity.NotesDateData, ItemRvBinding>?=null
    private var innerNotesAdapter: GenericAdapter<CurrentNoteData, RvInnerNotesItemBinding>?=null
    private val viewModel by viewModels<MainViewModel>()
    lateinit var adapter: MainAdapter
    private var notesDateDataList : ArrayList<NotesDateData> = arrayListOf()
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  DataBindingUtil.setContentView(this, R.layout.activity_main)
        setUpInnerAdapter()
        setUpOuterAdapter()
        observeData()
    }

    private fun setUpInnerAdapter(){

        innerNotesAdapter  = object : GenericAdapter< CurrentNoteData , RvInnerNotesItemBinding>(this){
            override fun getLayoutResId(viewType: Int?): Int  = R.layout.rv_inner_notes_item

            override fun onBindData(model: CurrentNoteData, position: Int, dataBinding: RvInnerNotesItemBinding) {
                dataBinding.tvNote.text = model.title
            }

            override fun onItemClick(view: View, model: CurrentNoteData, position: Int) {

            }

            override fun onInnerItemClick(view: View, model: CurrentNoteData, position: Int) {

            }

            override fun getInnerClickableItem(dataBinding: RvInnerNotesItemBinding): ArrayList<View>? =null

        }

        binding.rvMain.adapter = innerNotesAdapter
    }

    private fun setUpOuterAdapter(){
        dateHeadingAdapter  = object : GenericAdapter<NotesDateData , ItemRvBinding>(this){
            override fun getLayoutResId(viewType: Int?): Int  = R.layout.item_rv

            override fun onBindData(model: NotesDateData, position: Int, dataBinding: ItemRvBinding) {
                dataBinding.apply {
                    tvDate.text = model.date
                    rvMain.adapter = innerNotesAdapter
                    innerNotesAdapter?.updateList(model.notesList)
                }
            }

            override fun onItemClick(view: View, model: NotesDateData, position: Int) {
                
            }

            override fun onInnerItemClick(view: View, model: NotesDateData, position: Int) {
                
            }

            override fun getInnerClickableItem(dataBinding: ItemRvBinding): ArrayList<View>? =null

        }

        binding.rvMain.adapter = dateHeadingAdapter
    }





    private fun setUpAdapter(list: List<CurrentNoteData>) {
        adapter = MainAdapter()
        val view = findViewById<RecyclerView>(R.id.rv_main)
        view.adapter = adapter
        adapter.setListData(list)
    }

    private fun observeData() {
        var localDate = ""
        viewModel.quickNotes.observe(this, {
            it?.let { 
                for (i in it){
                   if (i.currentTimeInMilli.toString() != localDate){
                       notesDateDataList.add(
                           NotesDateData(date = i.currentTimeInMilli.toString(), it)
                       )
                       localDate = i.date
                   }
                }
                Log.d("TAG", "observeData: list $it")
                Log.d("TAG", "observeData: newList $notesDateDataList")
                dateHeadingAdapter?.updateList(notesDateDataList)
            }
        })
    }
    
    
    data class NotesDateData(
        val date : String,
        val notesList : List<CurrentNoteData>
    )
}