package com.catnip.rockpaperscissorchapter6and7.ui.splashscreen

import android.annotation.SuppressLint
import com.catnip.rockpaperscissorchapter6and7.data.local.preference.datasource.LocalDataSource
import com.catnip.rockpaperscissorchapter6and7.data.network.datasource.auth.AuthApiDataSource
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.BaseAuthResponse
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.UserData

@SuppressLint("CustomSplashScreen")
class SplashScreenRepository(
    private val apiDataSource: AuthApiDataSource,
    private val localDataSource: LocalDataSource
) : SplashScreenContract.Repository {

    override suspend fun getSyncUser(): BaseAuthResponse<UserData, String> {
        return apiDataSource.getSyncUser()
    }

    override fun deleteSession() {
        localDataSource.deleteSession()
    }

}