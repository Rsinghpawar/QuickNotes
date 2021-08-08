package com.rscorp.quicknotes.util

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object DateUtil {
    const val REGISTRATION_DATE_FORMAT = "dd-MM-yyyy"
    const val REGISTRATION_DATE_API_FORMAT = "yyyy-MM-dd"
    const val BOOKING_DATE_TIME_API_FORMAT = "yyyy-MM-dd HH:mm"
    const val BOOKING_TIME = "HH:mm"
    const val RAW_TIME = "HH:mm:ss"
    const val RAW_DATE_TIME = "yyyy-MM-dd HH:mm"
    const val PRETTY_TIME = "hh:mm aa"
    const val PRETTY_DATE = "dd MMM"
    const val PRETTY_DATE_TIME = "dd MMM, hh:mm aa"
    const val BOOKING_DATE = "yyyy-MM-dd"
    const val HOUR = "HH"
    const val DAY_MONTH_SPACE = "    EEE, dd MMM    "
    const val DAY_MONTH = "EEE, dd MMM"
    const val CUSTOM_DATE_REMINDER = "dd MMM yy"
    const val CUSTOM_DATE_REMINDER_YYYY = "dd MMM yyyy"
    const val VEHICLE_SERVICE_REGISTRATION_DATE_FORMAT = "dd-MMM-yyyy"
    const val CHALLAN_RAW_DATE_FORMAT = "yyyy-MM-dd"
    const val CHALLAN_RAW_TIME_FORMAT = "HH:mm:ss"
    const val CHALLAN_RAW_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"
    const val CHALLAN_DATE_FORMAT = "EEEE, dd-MMM-yyyy"
    const val CHALLAN_DATE_TIME_FORMAT = "EEEE, dd-MMM-yyyy | hh:mm aa"
    const val FASTAG_TIME_FORMAT = "dd-MMM-yyyy | hh:mm aa"
    const val NOTIFICATION_DATE_FROMAT = "MMM dd"
    const val TAG_INFRA_UPDATED_TIME_FORMAT = "yyyy-MM-dd HH:mm"
    const val EVENT_BOOKING_DATE = "yyyy-MM-dd HH:mm:ss"
    const val RECENT_RECHARGE_DATE_FORMAT = "dd-MMM | hh:mm aa"
    const val RECENT_RECHARGE_DATE_FORMAT_NEW = "dd-MMM"
    const val RECENT_RECHARGE_DATE_API_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"
    const val DIGILOCKER_API_FORMAT = "ddMMyyyy"
    const val DIGILOCKER_DOB_FORMAT = "dd-MM-yyyy"
    const val VALID_UPTO_FORMAT="dd/MM/yyyy"
    const val CARD_EXPIRY="MM/yyyy"
    const val EXTEND_DATE_TIME_FORMAT = "dd-MMM | hh:mm aa"


//    2021-05-27 19:44

    fun deserializeDateFromMilliSecond(milliSecond: Long? = 0, format: String): String {
        if (milliSecond != null) {
            try {
                return SimpleDateFormat(format).format(Date(milliSecond))
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
        return ""
    }

    fun serializeDateFromString(dateString: String? = null, format: String): Long {
        try {
            return SimpleDateFormat(format).parse(dateString).time
        } catch (ex: Exception) {
            ex.printStackTrace()
            return 0L
        }
    }

    fun getMillisecondsByHours(hours: Long): Long {
        return TimeUnit.HOURS.toMillis(hours)
    }

    fun getSecondsByMilliseconds(milliSecond: Long): Long {
        return TimeUnit.MILLISECONDS.toSeconds(milliSecond)
    }

    fun getMinutesByMilliseconds(milliSecond: Long): Long {
        return TimeUnit.MILLISECONDS.toMinutes(milliSecond)
    }

    fun getHoursByMilliseconds(milliSecond: Long): Long {
        return TimeUnit.MILLISECONDS.toHours(milliSecond)
    }

    fun getDaysByMilliseconds(milliSecond: Long): Long {
        return TimeUnit.MILLISECONDS.toDays(milliSecond)
    }

    fun getMillisecondsByDay(days: Long): Long {
        return TimeUnit.DAYS.toMillis(days)
    }

    fun getHoursByMintues(minutes: Long): Long {
        return TimeUnit.MINUTES.toHours(minutes)
    }

    fun getTodayDateMidNight(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }

    fun getFutureDateByDays(days: Int): Long {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, days)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }

    fun getMillisecondsOf18Year(): Long {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.YEAR, -18)
        return calendar.timeInMillis
    }

    fun getFutureDateByDaysAndMillis(days: Int, milliSecond: Long): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSecond
        calendar.add(Calendar.DAY_OF_YEAR, days)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }

    fun getPastMonthDate(month : Int): String {
//        2020-06-12T19:37:31
//        yyyy-MM-dd'T'hh:mm:ss.SSS'Z'
        val format = SimpleDateFormat("yyyy-MM-dd'T'hh:mm")
        val cal = Calendar.getInstance()
        cal.add(Calendar.MONTH, -month)
        return format.format(cal.time)
    }

    fun fromMinutesToHHmm(minutes: Long): String {
        val hours = TimeUnit.MINUTES.toHours(minutes.toLong())
        val remainMinutes = minutes - TimeUnit.HOURS.toMinutes(hours)
        return "$hours hr $remainMinutes min"
    }
}