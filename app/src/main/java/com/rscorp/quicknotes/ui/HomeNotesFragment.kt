package com.rscorp.quicknotes.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.rscorp.quicknotes.R
import com.rscorp.quicknotes.databinding.FragmentHomeNotesBinding
import com.rscorp.quicknotes.databinding.ItemRvBinding
import com.rscorp.quicknotes.databinding.RvInnerNotesItemBinding
import com.rscorp.quicknotes.db.models.CurrentNoteData
import com.rscorp.quicknotes.util.DateUtil
import com.rscorp.quicknotes.util.DateUtil.CUSTOM_DATE_REMINDER_YYYY
import com.rscorp.quicknotes.util.PrefHelper
import com.rscorp.quicknotes.util.genericAdapter.GenericAdapter
import com.rscorp.quicknotes.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class HomeNotesFragment : Fragment() {

    private var dateHeadingAdapter: GenericAdapter<DateOutNotesList, ItemRvBinding>? = null
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var binding: FragmentHomeNotesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeNotesBinding.inflate(inflater, container, false)
        observeData()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setIconsHash()
        setUpOuterAdapter()
        setSearchListener()
    }

    private fun setIconsHash() {
        PrefHelper.saveIconHasMap(
            hashMapOf(
                R.drawable.ic_url to "urls links",
                R.drawable.ic_music to "musics songs",
                R.drawable.ic_note_3 to "notes todo"
            ),
            requireContext()
        )
    }

    private fun setSearchListener() {
        binding.etSearch.addTextChangedListener {
            it?.let {
                searchDatabase(it.toString())
            }
        }
    }

    private fun getInnerAdapter() =
        object : GenericAdapter<CurrentNoteData, RvInnerNotesItemBinding>(requireContext()) {
            override fun getLayoutResId(viewType: Int?): Int = R.layout.rv_inner_notes_item

            override fun onBindData(
                model: CurrentNoteData,
                position: Int,
                dataBinding: RvInnerNotesItemBinding
            ) {
                dataBinding.tvNote.text = model.title
                dataBinding.imgNoteIcon.setImageResource(model.icon)
            }

            override fun onItemClick(view: View, model: CurrentNoteData, position: Int) {

            }

            override fun onInnerItemClick(view: View, model: CurrentNoteData, position: Int) {

            }

            override fun getInnerClickableItem(dataBinding: RvInnerNotesItemBinding): ArrayList<View>? =
                null

        }


    private fun setUpOuterAdapter() {
        dateHeadingAdapter =
            object : GenericAdapter<DateOutNotesList, ItemRvBinding>(requireContext()) {
                override fun getLayoutResId(viewType: Int?): Int = R.layout.item_rv

                override fun onBindData(
                    model: DateOutNotesList,
                    position: Int,
                    dataBinding: ItemRvBinding
                ) {
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

                override fun getInnerClickableItem(dataBinding: ItemRvBinding): ArrayList<View>? =
                    null

            }

        binding.rvMain.adapter = dateHeadingAdapter
    }

    private fun observeData() {
        val dateOutNotesList = mutableListOf<DateOutNotesList>()
        val dateHash = hashSetOf<String>()

        viewModel.searchNotes.observe(viewLifecycleOwner, {
            Log.d("TAG", "observeData: $it")

        })


        viewModel.quickNotes.observe(viewLifecycleOwner, {
            Log.d("TAG", "observeData: $it")
            it?.let { listCnd ->
                for (it in listCnd.asReversed()) {
                    val date = getDesiredDateFormat(it.currentTimeInMilli)
                    if (dateHash.contains(date)) {
                        val cnd = dateOutNotesList[dateHash.size - 1].notesList
                        cnd.add(it)
                    } else {
                        val innerCNDList = ArrayList<CurrentNoteData>()
                        innerCNDList.add(it)
                        dateOutNotesList.add(DateOutNotesList(date, innerCNDList))
                        dateHash.add(date)
                    }
                }
                dateHeadingAdapter?.updateListWithoutNotify(dateOutNotesList)
            }
        })

    }


    data class DateOutNotesList(
        val date: String,
        val notesList: ArrayList<CurrentNoteData>
    )

    private fun getDesiredDateFormat(millis: Long): String {
        return DateUtil.deserializeDateFromMilliSecond(millis, CUSTOM_DATE_REMINDER_YYYY)
    }

    override fun onDestroy() {
        Log.d("TAG", "onDestroy: ")
        super.onDestroy()

    }

    private fun searchDatabase(query: String) {
        val dateOutNotesList = mutableListOf<DateOutNotesList>()
        val dateHash = hashSetOf<String>()
        val searchQuery = "%$query%"
        viewModel.searchDatabase(searchQuery).observe(viewLifecycleOwner, {

            it?.let { listCnd ->
                for (it in listCnd.asReversed()) {
                    val date = getDesiredDateFormat(it.currentTimeInMilli)
                    if (dateHash.contains(date)) {
                        val cnd = dateOutNotesList[dateHash.size - 1].notesList
                        cnd.add(it)
                    } else {
                        val innerCNDList = ArrayList<CurrentNoteData>()
                        innerCNDList.add(it)
                        dateOutNotesList.add(DateOutNotesList(date, innerCNDList))
                        dateHash.add(date)
                    }
                }
                dateHeadingAdapter?.updateListWithoutNotify(dateOutNotesList)
            }
        })
    }
}