package com.example.ioasys.service.local

import android.content.Context

class SecuritySharedPreferences(context: Context) {

    private val mSharedPreferences = context.getSharedPreferences("app", Context.MODE_PRIVATE)

    fun storeString(key: String, value: String) {
        mSharedPreferences.edit().putString(key, value).apply()
    }

    fun getString(key: String): String {
        return mSharedPreferences.getString(key, "") ?: ""
    }
}