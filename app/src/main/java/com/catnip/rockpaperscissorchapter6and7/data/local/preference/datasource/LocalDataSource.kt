package com.catnip.rockpaperscissorchapter6and7.data.local.preference.datasource

interface LocalDataSource {
    fun getAuthToken(): String?
    fun setAuthToken(authToken: String?)
    fun deleteSession()
}