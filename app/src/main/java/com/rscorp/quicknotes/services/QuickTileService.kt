package com.rscorp.quicknotes.services

import android.app.AlarmManager
import android.app.Dialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Build
import android.service.quicksettings.TileService
import android.util.Log
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatImageView
import com.google.android.material.chip.Chip
import com.rscorp.quicknotes.R
import com.rscorp.quicknotes.db.CurrentNotesDao
import com.rscorp.quicknotes.db.models.CurrentNoteData
import com.rscorp.quicknotes.ui.MainNotesActivity
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
        super.onStartListening()
    }


    @DelicateCoroutinesApi
    override fun onClick() {
//        val tile = qsTile
//        tile.state = Tile.STATE_ACTIVE
//        tile.updateTile()
        var isSelected = false
        val hash = PrefHelper.getIconHashMap(this)
        val alert = Dialog(this, R.style.AlertDialogCustom).apply {
            setTitle("Quick title")
            setCancelable(false)
            setContentView(R.layout.custom_dialog)
        }
        val tIL = alert.findViewById<EditText>(R.id.edit_todo)
        val btnSave = alert.findViewById<TextView>(R.id.btn_save)
        val btnCancel = alert.findViewById<TextView>(R.id.btn_cancel)
        val spinner = alert.findViewById<Spinner>(R.id.spinner)
        val imgLaunch = alert.findViewById<AppCompatImageView>(R.id.imgLaunch)
        val chip1Min = alert.findViewById<Chip>(R.id.chipReminder1)
        val chip2Min = alert.findViewById<Chip>(R.id.chipReminder2)
        spinner.adapter = MySpinnerAdapter(this , PrefHelper.getIconsArray(this))
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

            }
            if (isSelected && tIL.text.toString().isNotEmpty()){
                val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val intent = Intent(this , AlertReceiver::class.java).apply {
                        putExtra("message" , tIL.text.toString() )
                }
                val pendingIntent = PendingIntent.getBroadcast(this , 1 , intent , 0)
                alarmManager.setExact(AlarmManager.RTC_WAKEUP , 300000 , pendingIntent)
            }

        }
        btnCancel.setOnClickListener {
            alert.dismiss()
        }
        imgLaunch.setOnClickListener {
            Intent(this, MainNotesActivity::class.java).apply {
                addFlags(FLAG_ACTIVITY_NEW_TASK)
            }.also {
                alert.dismiss()
                startActivity(it)
            }
        }
        showDialog(alert)

        chip1Min.setOnClickListener {
            isSelected = !isSelected
            Toast.makeText(this, "$isSelected", Toast.LENGTH_SHORT).show()
        }
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

