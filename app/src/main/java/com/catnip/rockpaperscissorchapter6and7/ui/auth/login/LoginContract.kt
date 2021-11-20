package com.catnip.rockpaperscissorchapter6and7.ui.auth.login

import android.app.Dialog
import com.catnip.rockpaperscissorchapter6and7.base.BaseViewModelContract
import com.catnip.rockpaperscissorchapter6and7.data.local.room.PlayersDatabase
import com.catnip.rockpaperscissorchapter6and7.data.model.Player
import com.catnip.rockpaperscissorchapter6and7.data.network.model.request.auth.AuthRequest
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.BaseAuthResponse
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.UserData


interface LoginContract {
    interface View : BaseViewModelContract.BaseView {
        fun observeViewModel()
        fun navigateToMenu()
        fun setOnClick()
        fun saveSessionLogin(data: UserData)
        fun showLoading(dialog: Dialog, isLoading: Boolean)
        fun showToast(isSuccess: Boolean, msg: String)
        fun checkFormValidation(): Boolean
    }

    interface ViewModel {
        fun saveToDao(userName: String, isAdd: Boolean, db: PlayersDatabase)
        fun saveSession(authToken: String)
        fun saveUserPreference(userName: String)
        fun loginUser(loginRequest: AuthRequest)
    }

    interface Repository {
        fun saveSession(authToken: String)
        fun saveUserPreference(player : Player)
        suspend fun postLoginUser(loginRequest: AuthRequest): BaseAuthResponse<UserData, String>
        suspend fun getPlayerByUsername(username : String): Player
    }
}