package com.catnip.rockpaperscissorchapter6and7.ui.splashscreen

import android.annotation.SuppressLint
import com.catnip.rockpaperscissorchapter6and7.data.network.datasource.auth.AuthApiDataSource
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.BaseAuthResponse
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.UserData

@SuppressLint("CustomSplashScreen")
class SplashScreenRepository(private val dataSource: AuthApiDataSource): SplashScreenContract.Repository {

    override suspend fun getSyncUser(): BaseAuthResponse<UserData, String> {
        return dataSource.getSyncUser()
    }
}