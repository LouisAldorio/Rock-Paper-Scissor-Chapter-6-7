package com.catnip.rockpaperscissorchapter6and7.ui.profile

import com.catnip.rockpaperscissorchapter6and7.data.network.datasource.auth.AuthApiDataSource
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.BaseAuthResponse
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.UserData

class ProfileRepository(private val repository : AuthApiDataSource) : ProfileContract.Repository  {

    override suspend fun getProfileData(): BaseAuthResponse<UserData, String> {
        return repository.getUserData()
    }

    override suspend fun updateProfile(
        username: String,
        email: String
    ): BaseAuthResponse<UserData, String> {
        return repository.putUserData(username, email)
    }
}