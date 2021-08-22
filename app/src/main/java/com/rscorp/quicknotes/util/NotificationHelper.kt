package com.rscorp.quicknotes.util


import androidx.core.app.NotificationCompat
import android.app.NotificationManager
import android.app.NotificationChannel
import android.os.Build
import android.annotation.TargetApi
import android.content.Context
import android.content.ContextWrapper
import com.rscorp.quicknotes.R


class NotificationHelper(base: Context?) : ContextWrapper(base) {
    private var mManager: NotificationManager? = null
    @TargetApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        val channel =
            NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH)
        manager!!.createNotificationChannel(channel)
    }

    val manager: NotificationManager?
        get() {
            if (mManager == null) {
                mManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            }
            return mManager
        }
    val channelNotification: NotificationCompat.Builder
        get() = NotificationCompat.Builder(applicationContext, channelID)
            .setContentTitle("Alarm!")
            .setContentText("Your AlarmManager is working.")
            .setSmallIcon(R.drawable.ic_idea_bulb)

    fun getNotificationBuilder(message: String) = NotificationCompat.Builder(applicationContext, channelID)
        .setContentTitle("Quick Notes")
        .setContentText(message)
        .setSmallIcon(R.drawable.ic_idea_bulb)

    companion object {
        const val channelID = "channelID"
        const val channelName = "Channel Name"
    }

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel()
        }
    }
}