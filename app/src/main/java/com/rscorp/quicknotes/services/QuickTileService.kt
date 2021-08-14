package com.rscorp.quicknotes.services

import android.app.Dialog
import android.os.Build
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.rscorp.quicknotes.R
import com.rscorp.quicknotes.db.CurrentNotesDao
import com.rscorp.quicknotes.db.models.CurrentNoteData
import com.rscorp.quicknotes.util.DateUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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


    override fun onClick() {
        val tile = qsTile
        tile.state = Tile.STATE_ACTIVE
        tile.updateTile()
        val alert = Dialog(this, R.style.AlertDialogCustom)
        alert.setTitle("Quick title")
        alert.setContentView(R.layout.custom_dialog)
        val tIL = alert.findViewById<EditText>(R.id.edit_todo)
        val btn_save = alert.findViewById<TextView>(R.id.btn_save)
        btn_save.setOnClickListener {
            GlobalScope.launch {
                currentNotesDao.saveNotes(
                    CurrentNoteData(
                        id = 0,
                        title = tIL.text.toString(),
                        description = "",
                        currentTimeInMilli = DateUtil.getTodayDateMidNight(),
                        date = DateUtil.getDesiredDateFormat(DateUtil.getTodayDateMidNight()),
                        time = "",
                        tag = "",
                        color = -1
                    )
                )
                alert.dismiss()
                tile.state = Tile.STATE_INACTIVE
                tile.updateTile()
            }
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
