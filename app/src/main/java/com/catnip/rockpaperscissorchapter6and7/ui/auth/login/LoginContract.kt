package com.catnip.rockpaperscissorchapter6and7.ui.auth.login

import android.app.Dialog
import com.catnip.rockpaperscissorchapter6and7.base.BaseViewModelContract
import com.catnip.rockpaperscissorchapter6and7.data.network.model.request.auth.AuthRequest
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.BaseAuthResponse
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.UserData


interface LoginContract {
    interface View: BaseViewModelContract.BaseView {
        fun observeViewModel()
        fun navigateToMenu()
        fun setOnClick()
        fun saveSessionLogin(data: UserData)
        fun showToast(isSuccess: Boolean, msg: String)
        fun showLoading(dialog: Dialog, isLoading: Boolean)
        fun checkFormValidation(): Boolean
    }

    interface ViewModel {
        fun saveSession(authToken: String)
        fun loginUser(loginRequest: AuthRequest)
    }

    interface Repository {
        fun saveSession(authToken: String)
        suspend fun postLoginUser(loginRequest: AuthRequest): BaseAuthResponse<UserData, String>

    }
}