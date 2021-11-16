package com.catnip.rockpaperscissorchapter6and7.data.local.preference.datasource

import com.catnip.rockpaperscissorchapter6and7.data.local.preference.UserPreference

class PreferenceDataSourceImpl(private val userPreference: UserPreference) : PreferenceDataSource {
    override fun getAuthToken(): String? {
        return userPreference.authToken
    }
}