package com.catnip.rockpaperscissorchapter6and7.ui.splashscreen

import androidx.lifecycle.LiveData
import com.catnip.rockpaperscissorchapter6and7.base.model.Resource
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.BaseAuthResponse
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.UserData

interface SplashScreenContract {
    interface View {
        fun observeViewModel()
    }
    interface ViewModel {
        fun getSyncUser()
        fun getAuthSyncLiveData(): LiveData<Resource<BaseAuthResponse<UserData, String>>>
    }
    interface Repository {
        suspend fun getSyncUser(): BaseAuthResponse<UserData, String>

    }
}