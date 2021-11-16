package com.catnip.rockpaperscissorchapter6and7.ui.auth.login

import com.catnip.rockpaperscissorchapter6and7.base.BaseViewModelContract
import com.catnip.rockpaperscissorchapter6and7.data.network.model.request.auth.AuthRequest
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.BaseAuthResponse
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.UserData


interface LoginContract {
    interface View: BaseViewModelContract.BaseView {
        fun observeViewModel()
        fun navigateToMenu()
        fun navigateToRegister()
        fun setOnClick()
        fun saveSessionLogin(data: UserData)

    }

    interface ViewModel {
        fun loginUser(loginRequest: AuthRequest)
    }

    interface Repository {
        suspend fun postLoginUser(loginRequest: AuthRequest): BaseAuthResponse<UserData, String>
    }
}