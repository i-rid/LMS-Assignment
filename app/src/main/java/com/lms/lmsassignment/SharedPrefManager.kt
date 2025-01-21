package com.lms.lmsassignment

import android.content.Context
import android.content.SharedPreferences

class SharedPrefManager(context: Context) {

    companion object {
        private const val PREF_NAME = "BiometricAuthPrefs"
        private const val KEY_LAST_ACTIVE_TIME = "LastActiveTime"
    }
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    // Save the last active time in SharedPreferences
    fun saveLastActiveTime(currentTime: Long) {
        sharedPreferences.edit().putLong(KEY_LAST_ACTIVE_TIME, currentTime).apply()
    }

    // Retrieve the last active time from SharedPreferences
    fun getLastActiveTime(): Long {
        return sharedPreferences.getLong(KEY_LAST_ACTIVE_TIME, -1L) // Default to -1 if not set
    }

    // Clear all session data from SharedPreferences
    fun clearSessionData() {
        sharedPreferences.edit().clear().apply()
    }
}