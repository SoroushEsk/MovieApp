package com.example.myapplication.utils

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

//region Clear and Remove
fun SharedPreferences.clearPreferences(){
    this.edit().clear().apply()
}
fun SharedPreferences.remove(key : String){
    this.edit().remove(key).apply()
}
fun SharedPreferences.containsKey(key : String) : Boolean{
    return this.contains(key)
}
//endregion
//region String
fun SharedPreferences.putString(key : String, value : String){
    this.edit().putString(key, value).apply()
}
fun SharedPreferences.putStringList(key: String, items : ArrayList<String>){
    val gson = Gson()
    this.edit(){
        val json: String = gson.toJson(items)
        putString(key, json)
        apply()
    }
}
fun SharedPreferences.getStringList(key : String): ArrayList<String>{
    val gson = Gson()
    val json = this.getString(key, null)
    val type = object : TypeToken<ArrayList<String>>() {}.type
    return gson.fromJson(json, type)
}
//endregion
//region Integer
fun SharedPreferences.putInteger(key : String, value : Int){
    this.edit().putInt(key, value).apply()
}

//endregion
//region Boolean
fun SharedPreferences.putBoolean(key : String, value : Boolean){
    this.edit().putBoolean(key, value).apply()
}
//endregion
