package com.catnip.rockpaperscissorchapter6and7.data.network.datasource.auth

import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.BaseAuthResponse
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.UserData
import com.catnip.rockpaperscissorchapter6and7.data.network.model.services.AuthApiService
import javax.inject.Inject

class AuthApiDataSourceImpl(private val authApiService: AuthApiService) : AuthApiDataSource {
    override suspend fun getSyncUser(): BaseAuthResponse<UserData, String> {
        return authApiService.getSyncUser()
    }
}