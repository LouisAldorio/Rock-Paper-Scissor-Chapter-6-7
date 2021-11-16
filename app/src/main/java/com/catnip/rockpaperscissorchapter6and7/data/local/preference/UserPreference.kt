package com.catnip.rockpaperscissorchapter6and7.data.local.preference

import android.content.Context
import android.content.SharedPreferences
import com.catnip.rockpaperscissorchapter6and7.data.model.Player
import com.google.gson.Gson

class UserPreference(context : Context) {

    private var preference : SharedPreferences = context.getSharedPreferences(PREF_NAME, PREF_MODE)

    companion object {
        private const val PREF_NAME = "RockPaperScissor"
        private const val PREF_MODE = Context.MODE_PRIVATE
        private val PREF_IS_USER_LOGGED_IN = Pair("player", null)
        private val PREF_AUTH_TOKEN = Pair("PREF_AUTH_TOKEN", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2MThkMTkzODZiOWFlZjAwMTc0NWEzMWQiLCJ1c2VybmFtZSI6ImxvdWlzYWxkb3JpbzEyMyIsImVtYWlsIjoibG91aXNhbGRvcmlvMTIzQGdtYWlsLmNvbSIsImlhdCI6MTYzNjk4NDUzMSwiZXhwIjoxNjM2OTkxNzMxfQ.a9rJMbNJ8Rlen1i3cUaxQwL8kV46kdxHl9RuNoVyUe0")
        private val gson = Gson()
    }

    var player: Player?
        get() = gson.fromJson(preference.getString(PREF_IS_USER_LOGGED_IN.first, PREF_IS_USER_LOGGED_IN.second), Player::class.java)
        set(player) {
            preference.edit {
                it.putString(PREF_IS_USER_LOGGED_IN.first, gson.toJson(player))
            }
        }

//    var authToken: String?
//        get() = preference.getString(PREF_AUTH_TOKEN.first, PREF_AUTH_TOKEN.second)
//        set(value) = preference.edit {
//            it.putString(PREF_AUTH_TOKEN.first, value)
//        }
}

private inline fun SharedPreferences.edit(operation : (SharedPreferences.Editor) -> Unit) {
    val editor = edit()
    operation(editor)
    editor.apply()
}