package com.rscorp.quicknotes.util

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import com.google.gson.Gson
import android.app.usage.UsageStats

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


object PrefHelper {

    //todo create addIconInHash method
    fun saveIconHash(hash : HashMap<Int , String>, context: Context){
        val sharedPref: SharedPreferences = getSharedPref(context)
        if (sharedPref.getString("MyHashMap" , null) ==null){
            val editor = sharedPref.edit()
            editor.putString("MyHashMap", Gson().toJson(hash))
            editor.apply()
        }

    }

    fun getIconHashMap(context: Context) : HashMap<Int , String>{
        val gson = Gson()
        val json: String? = getSharedPref(context).getString("MyHashMap", "")
        val typeMyType: Type = object : TypeToken<HashMap<Int,String>>() {}.type
        return gson.fromJson(json, typeMyType)
    }

    private fun getSharedPref(context: Context) = context.getSharedPreferences(
        "MyPreference", Context.MODE_PRIVATE
    )

    fun createIconsArray(context: Context,  iconArray : Array<Int>) {
        val sharedPref = getSharedPref(context)
        if (sharedPref.getString("iconArray" , null) ==null){
            val editor = sharedPref.edit()
            editor.putString("iconArray", Gson().toJson(iconArray))
            editor.apply()
        }
    }

    fun getIconsArray(context: Context) : Array<Int>{
        val gson = Gson()
        val json: String? = getSharedPref(context).getString("iconArray", "")
        val typeMyType: Type = object : TypeToken<Array<Int>>() {}.type
        return gson.fromJson(json, typeMyType)
    }

}