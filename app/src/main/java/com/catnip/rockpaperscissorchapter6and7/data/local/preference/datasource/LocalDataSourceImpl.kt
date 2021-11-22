package com.catnip.rockpaperscissorchapter6and7.data.local.preference.datasource

import com.catnip.rockpaperscissorchapter6and7.data.local.preference.SessionPreference
import com.catnip.rockpaperscissorchapter6and7.data.local.preference.UserPreference
import com.catnip.rockpaperscissorchapter6and7.data.model.Player

class LocalDataSourceImpl(
    private val sessionPreference: SessionPreference,
    private val userPreference: UserPreference
) : LocalDataSource {
    override fun getAuthToken(): String? {
        return sessionPreference.authToken
    }

    override fun setAuthToken(authToken: String?) {
        sessionPreference.authToken = authToken
    }

    override fun saveUser(player : Player) {
        userPreference.player = player
    }

    override fun deleteSession() {
        sessionPreference.deleteSession()
    }
}