package com.rscorp.quicknotes.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.rscorp.quicknotes.util.NotificationHelper

class AlertReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        val notificationHelper = NotificationHelper(p0)
        val channel = notificationHelper.channelNotification
        notificationHelper.manager?.notify(1,channel.build())
    }
}