package com.catnip.rockpaperscissorchapter6and7.ui.game

import com.catnip.rockpaperscissorchapter6and7.data.local.preference.UserPreference
import com.catnip.rockpaperscissorchapter6and7.data.local.preference.datasource.LocalDataSource

class MenuRepository(private val localDataSource: LocalDataSource) : MenuContract.Repository {

    override fun deleteSession() {
        localDataSource.deleteSession()
    }
}