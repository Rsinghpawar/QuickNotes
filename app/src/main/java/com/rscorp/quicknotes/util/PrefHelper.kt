package com.rscorp.quicknotes.util

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import com.google.gson.Gson
import android.app.usage.UsageStats

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


object PrefHelper {
    fun saveIconHasMap(hash : HashMap<Int , String> , context: Context){
        val sharedPref: SharedPreferences = getSharedPref(context)
        val editor = sharedPref.edit()
        editor.putString("MyHashMap", Gson().toJson(hash))
        editor.apply()
    }

    fun getIconHashMap(context: Context) : HashMap<Int , String>{
        val gson = Gson()
        val json: String? = getSharedPref(context).getString("MyHashMap", "")
        val typeMyType: Type = object : TypeToken<HashMap<Int,String>>() {}.type
        return gson.fromJson(json, typeMyType)
    }

    fun getSharedPref(context: Context) = context.getSharedPreferences(
        "MyPreference", Context.MODE_PRIVATE
    )
}