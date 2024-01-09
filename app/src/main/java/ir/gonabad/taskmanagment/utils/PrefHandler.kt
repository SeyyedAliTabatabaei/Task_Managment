package ir.gonabad.taskmanagment.utils

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class PrefHandler @Inject constructor(val application: Application) {
    private lateinit var sharedPref: SharedPreferences
    private val PREF_NAME = "step_counter_pref"
    val KEY_TOKEN = "token"
    val KEY_TYPE = "type"
    val KEY_USERNAME = "username"
    val KEY_USER_INFO = "user_info"

    init {
        sharedPref = application.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun setPreference(key: String?, value: Any?) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        when (value) {
            is Int -> editor.putInt(
                key,
                (value as Int?)!!
            )
            is String -> editor.putString(
                key,
                value as String?
            )
            is Boolean -> editor.putBoolean(
                key,
                (value as Boolean?)!!
            )
            is Long -> editor.putLong(
                key,
                (value as Long?)!!
            )
            is Set<*> -> editor.putStringSet(
                key,
                value as Set<String?>?
            )
        }
        editor.apply()
    }

    fun getInt(key: String?, defaultValue: Int): Int {
        return sharedPref.getInt(key, defaultValue)
    }

    fun getString(key: String?, defaultValue: String?): String? {
        return sharedPref.getString(key, defaultValue)
    }

    fun getBoolean(key: String?, defaultValue: Boolean): Boolean {
        return sharedPref.getBoolean(key, defaultValue)
    }

    fun getLong(key: String?, defaultValue: Long): Long {
        return sharedPref.getLong(key, defaultValue)
    }

    fun getStringSet(
        key: String?,
        defaultValue: Set<String?>?
    ): Set<String?>? {
        return sharedPref.getStringSet(key, defaultValue)
    }

    fun clearTag(keyName: String?) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.remove(keyName)
        editor.apply()
    }

    fun clear(): Boolean {
        return sharedPref.edit().clear().commit()
    }

    fun contain(key: String?): Boolean {
        return sharedPref.contains(key)
    }

    fun RemovingSinglePreference(key: String?) {
        sharedPref.edit().remove(key).apply()
    }
}