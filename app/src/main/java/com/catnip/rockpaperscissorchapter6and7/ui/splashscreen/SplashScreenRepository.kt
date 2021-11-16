package com.catnip.rockpaperscissorchapter6and7.ui.splashscreen

import com.catnip.rockpaperscissorchapter6and7.data.network.datasource.binar.BinarApiDataSource
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.BaseAuthResponse
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.UserData

class SplashScreenRepository(private val dataSource: BinarApiDataSource): SplashScreenContract.Repository {

    override suspend fun getSyncUser(): BaseAuthResponse<UserData, String> {
        return dataSource.getSyncUser()
    }
}