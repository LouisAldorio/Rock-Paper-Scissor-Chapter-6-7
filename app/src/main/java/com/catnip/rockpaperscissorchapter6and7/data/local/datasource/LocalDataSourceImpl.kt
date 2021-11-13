package com.catnip.rockpaperscissorchapter6and7.data.local.datasource

import com.catnip.rockpaperscissorchapter6and7.data.local.preference.SessionPreference
import javax.inject.Inject

class LocalDataSourceImpl(private val sessionPreference: SessionPreference) : LocalDataSource {
    override fun getAuthToken(): String? {
        return sessionPreference.authToken
    }
}