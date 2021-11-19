package com.catnip.rockpaperscissorchapter6and7.data.local.preference.datasource

import com.catnip.rockpaperscissorchapter6and7.data.local.preference.SessionPreference

class LocalDataSourceImpl(private val sessionPreference: SessionPreference) : LocalDataSource {
    override fun getAuthToken(): String? {
        return sessionPreference.authToken
    }

    override fun setAuthToken(authToken: String?) {
        sessionPreference.authToken = authToken
    }

    override fun deleteSession() {
        sessionPreference.deleteSession()
    }
}