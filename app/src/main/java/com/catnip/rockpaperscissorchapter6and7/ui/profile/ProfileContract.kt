package com.catnip.rockpaperscissorchapter6and7.ui.profile

import com.catnip.rockpaperscissorchapter6and7.base.BaseContract
import com.catnip.rockpaperscissorchapter6and7.data.model.Player
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.BaseAuthResponse
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.UserData

interface ProfileContract {

    interface View: BaseContract.BaseView {
        fun setProfileData(email: String, username: String)
    }

    interface ViewModel {
        fun getProfileData()
        fun updateProfile(username: String , email : String)
        fun updateUsername(newUsername: String, oldUsername: String)
    }

    interface Repository {
        suspend fun getProfileData() : BaseAuthResponse<UserData, String>
        suspend fun updateProfile(
            username: String,
            email: String
        ): BaseAuthResponse<UserData, String>

        suspend fun getPlayerByUsername(username : String): Player
        suspend fun insertPlayer(player: Player): Long
        fun saveUser(player : Player)
    }
}