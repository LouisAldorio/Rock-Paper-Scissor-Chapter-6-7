package com.catnip.rockpaperscissorchapter6and7.data.local.preference

import android.content.Context
import android.content.SharedPreferences

class UserPreference(context : Context) {

    private var preference : SharedPreferences = context.getSharedPreferences(PREF_NAME, PREF_MODE)

    companion object {
        private const val PREF_NAME = "RockPaperScissor"
        private const val PREF_MODE = Context.MODE_PRIVATE
        private val PREF_IS_USER_LOGGED_IN = Pair("username", "")
    }

    var username : String?
        get() = preference.getString(PREF_IS_USER_LOGGED_IN.first, PREF_IS_USER_LOGGED_IN.second)
        set(value) {
            preference.edit {
                it.putString(PREF_IS_USER_LOGGED_IN.first, value)
            }
        }
}

private inline fun SharedPreferences.edit(operation : (SharedPreferences.Editor) -> Unit) {
    val editor = edit()
    operation(editor)
    editor.apply()
}