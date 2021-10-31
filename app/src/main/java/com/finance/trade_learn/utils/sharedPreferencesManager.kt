package com.finance.trade_learn.utils

import android.content.Context
import android.content.SharedPreferences

class sharedPreferencesManager(val context: Context) {
    private var packName = "com.finance.trade_learn"
    private val sharedPreferencesManager =
        context.getSharedPreferences(packName, Context.MODE_PRIVATE)

    fun addSharedPreferencesString(keyName: String, value: String) {
        sharedPreferencesManager.edit().putString(keyName, value).apply()
    }

    fun getSharedPreferencesString(keyName: String) =
        sharedPreferencesManager.getString(keyName, "BTC").toString()

    fun addSharedPreferencesInt(keyName: String, value: Int) {
        sharedPreferencesManager.edit().putInt(keyName, value).apply()
    }

    fun getSharedPreferencesInt(keyName: String, i: Int) =
        sharedPreferencesManager.getInt(keyName, 0)

    fun addSharedPreferencesBoolen(keyName: String, value: Boolean) {
        sharedPreferencesManager.edit().putBoolean(keyName, value).apply()
    }

    fun getSharedPreferencesBoolen(keyName: String) =
        sharedPreferencesManager.getBoolean(keyName, true)


}