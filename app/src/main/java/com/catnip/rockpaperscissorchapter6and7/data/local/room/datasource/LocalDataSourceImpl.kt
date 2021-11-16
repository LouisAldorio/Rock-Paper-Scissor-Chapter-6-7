package com.catnip.rockpaperscissorchapter6and7.data.local.room.datasource

import com.catnip.rockpaperscissorchapter6and7.data.local.preference.SessionPreference

class LocalDataSourceImpl(private val sessionPreference: SessionPreference) :
    LocalDataSource {
    override fun getAuthToken(): String? {
        return sessionPreference.authToken
    }
}