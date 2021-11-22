package com.catnip.rockpaperscissorchapter6and7.data.local.preference.datasource

import com.catnip.rockpaperscissorchapter6and7.data.model.Player

interface LocalDataSource {
    fun getAuthToken(): String?
    fun setAuthToken(authToken: String?)
    fun saveUser(player : Player)
    fun deleteSession()
}