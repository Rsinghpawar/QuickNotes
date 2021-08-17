package com.rscorp.quicknotes.services

import android.app.Dialog
import android.os.Build
import android.service.quicksettings.TileService
import android.util.Log
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.rscorp.quicknotes.R
import com.rscorp.quicknotes.db.CurrentNotesDao
import com.rscorp.quicknotes.db.models.CurrentNoteData
import com.rscorp.quicknotes.util.DateUtil
import com.rscorp.quicknotes.util.MySpinnerAdapter
import com.rscorp.quicknotes.util.PrefHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
@RequiresApi(Build.VERSION_CODES.N)
class QuickTileService : TileService() {

    @Inject
    lateinit var currentNotesDao: CurrentNotesDao
    val TAG = "quicktile"
    override fun onTileAdded() {
        Log.d(TAG, "onTileAdded: ")
        super.onTileAdded()
    }

    override fun onStartListening() {

    }


    @DelicateCoroutinesApi
    override fun onClick() {
        val tile = qsTile
//        tile.state = Tile.STATE_ACTIVE
//        tile.updateTile()
        val hash = PrefHelper.getIconHashMap(this)
        val alert = Dialog(this, R.style.AlertDialogCustom)
        alert.setTitle("Quick title")
        alert.setCancelable(false)
        alert.setContentView(R.layout.custom_dialog)
        val tIL = alert.findViewById<EditText>(R.id.edit_todo)
        val btnSave = alert.findViewById<TextView>(R.id.btn_save)
        val btnCancel = alert.findViewById<TextView>(R.id.btn_cancel)
        val spinner = alert.findViewById<Spinner>(R.id.spinner)
        spinner.adapter = MySpinnerAdapter(this , arrayOf(R.drawable.ic_note_3 , R.drawable.ic_music , R.drawable.ic_url))
        btnSave.setOnClickListener {
            GlobalScope.launch {
                currentNotesDao.saveNotes(
                    CurrentNoteData(
                        id = 0,
                        title = tIL.text.toString(),
                        description = "",
                        currentTimeInMilli = DateUtil.getTodayDateMidNight(),
                        date = DateUtil.getDesiredDateFormat(DateUtil.getTodayDateMidNight()),
                        time = "",
                        tag = hash.getValue(spinner.selectedItem as Int),
                        color = -1,
                        icon = spinner.selectedItem as Int,
                        iconPosition = spinner.selectedItemPosition
                    )
                )
                withContext(Dispatchers.Main){
                    Toast.makeText(this@QuickTileService, "Notes saved", Toast.LENGTH_SHORT).show()
                }
                alert.dismiss()
//                tile.state = Tile.STATE_INACTIVE
//                tile.updateTile()
            }
        }
        btnCancel.setOnClickListener {
            alert.dismiss()
        }
        showDialog(alert)

    }

}
//        val sharedPref = getSharedPreferences("Myshared", MODE_PRIVATE)

//                intent.putExtra("string" , edittext.text.toString())
//                startActivityAndCollapse(intent)
//                sharedPref.edit().putString("string" , edittext.text.toString()).apply()


//val alert = AlertDialog.Builder(this , R.style.AlertDialogCustom).apply {
//            setTitle("Add Quick note")
//            setView(R.layout.custom_dialog)
//            setPositiveButton("Save") { di, i ->
//
//                GlobalScope.launch {
//                    currentNotesDao.saveNotes(CurrentNoteData(0, editText.text.toString(), "Hello", "10 july"))
//                }
//            }
//            setNegativeButton("Cancel"){di , i ->
//                di.dismiss()
//            }
//            setCancelable(false)
//        }.create()

//val intent = Intent(this, MainActivity::class.java)
//        intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
//        startActivity(intent)
