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
import com.rscorp.quicknotes.util.DateUtil
import com.rscorp.quicknotes.util.DateUtil.CUSTOM_DATE_REMINDER_YYYY
import com.rscorp.quicknotes.util.genericAdapter.GenericAdapter
import com.rscorp.quicknotes.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var dateHeadingAdapter: GenericAdapter<MainActivity.DateOutNotesList, ItemRvBinding>?=null
    private var innerNotesAdapter: GenericAdapter<CurrentNoteData, RvInnerNotesItemBinding>?=null
    private val viewModel by viewModels<MainViewModel>()
    lateinit var adapter: MainAdapter
    private var dateOutNotesListList : ArrayList<DateOutNotesList> = arrayListOf()
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  DataBindingUtil.setContentView(this, R.layout.activity_main)
        setUpOuterAdapter()
        observeData()
    }

    private fun getInnerAdapter()  = object : GenericAdapter< CurrentNoteData , RvInnerNotesItemBinding>(this){
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


    private fun setUpOuterAdapter(){
        dateHeadingAdapter  = object : GenericAdapter<DateOutNotesList , ItemRvBinding>(this){
            override fun getLayoutResId(viewType: Int?): Int  = R.layout.item_rv

            override fun onBindData(model: DateOutNotesList, position: Int, dataBinding: ItemRvBinding) {
                dataBinding.apply {
                    if (model.date == getDesiredDateFormat(DateUtil.getTodayDateMidNight()))
                        tvDate.text = "Today"
                    else
                        tvDate.text = model.date
                    val innerAdapter = getInnerAdapter()
                    rvInner.adapter = innerAdapter
                    innerAdapter.updateList(model.notesList)
                }
            }

            override fun onItemClick(view: View, model: DateOutNotesList, position: Int) {
                
            }

            override fun onInnerItemClick(view: View, model: DateOutNotesList, position: Int) {
                
            }

            override fun getInnerClickableItem(dataBinding: ItemRvBinding): ArrayList<View>? =null

        }

        binding.rvMain.adapter = dateHeadingAdapter
    }

    private fun observeData() {
        val dateOutNotesList = mutableListOf<DateOutNotesList>()
        val dateHash = hashSetOf<String>()

        viewModel.quickNotes.observe(this, {
            it?.let { listCnd ->
               for (it in listCnd.asReversed()) {
                    val date = getDesiredDateFormat(it.currentTimeInMilli)
                    if (dateHash.contains(date)){
                        val cnd = dateOutNotesList[dateHash.size -1].notesList
                        cnd.add(it)
                    }else{
                        val innerCNDList = ArrayList<CurrentNoteData>()
                        innerCNDList.add(it)
                        dateOutNotesList.add(DateOutNotesList(date , innerCNDList))
                        dateHash.add(date)
                    }
                }
                dateHeadingAdapter?.updateListWithoutNotify(dateOutNotesList)
            }
        })
    }
    
    
    data class DateOutNotesList(
        val date : String,
        val notesList : ArrayList<CurrentNoteData>
    )

    private fun getDesiredDateFormat(millis : Long) : String {
        return DateUtil.deserializeDateFromMilliSecond(millis , CUSTOM_DATE_REMINDER_YYYY)
    }
}