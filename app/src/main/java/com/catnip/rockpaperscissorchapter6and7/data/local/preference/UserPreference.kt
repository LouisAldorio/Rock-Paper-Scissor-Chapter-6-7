package com.catnip.rockpaperscissorchapter6and7.data.local.preference

import android.content.Context
import android.content.SharedPreferences
import com.catnip.rockpaperscissorchapter6and7.data.model.Player
import com.google.gson.Gson

class UserPreference(context : Context) {

    private var preference : SharedPreferences = context.getSharedPreferences(PREF_NAME, PREF_MODE)

    companion object {
        private const val PREF_NAME = "userName"
        private const val PREF_MODE = Context.MODE_PRIVATE
        private val PREF_IS_USER_LOGGED_IN = Pair("player", null)
        private val gson = Gson()
    }

    var player: Player?
        get() = gson.fromJson(preference.getString(PREF_IS_USER_LOGGED_IN.first, PREF_IS_USER_LOGGED_IN.second), Player::class.java)
        set(player) {
            preference.edit {
                it.putString(PREF_IS_USER_LOGGED_IN.first, gson.toJson(player))
            }
        }
}

private inline fun SharedPreferences.edit(operation : (SharedPreferences.Editor) -> Unit) {
    val editor = edit()
    operation(editor)
    editor.apply()
}