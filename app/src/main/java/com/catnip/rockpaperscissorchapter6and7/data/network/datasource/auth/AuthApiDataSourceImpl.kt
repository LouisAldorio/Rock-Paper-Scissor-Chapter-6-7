package com.catnip.rockpaperscissorchapter6and7.data.network.datasource.auth

import com.catnip.rockpaperscissorchapter6and7.data.network.model.request.auth.AuthRequest
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.BaseAuthResponse
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.UserData
import com.catnip.rockpaperscissorchapter6and7.data.network.services.AuthApiService
import okhttp3.MultipartBody

class AuthApiDataSourceImpl(private val authApiService: AuthApiService) : AuthApiDataSource {

    override suspend fun postLoginUser(loginRequest: AuthRequest): BaseAuthResponse<UserData, String> {
        return authApiService.postLoginUser(loginRequest)
    }

    override suspend fun postRegisterUser(registerRequest: AuthRequest): BaseAuthResponse<UserData, String> {
        return authApiService.postRegisterUser(registerRequest)
    }

    override suspend fun getUserData(): BaseAuthResponse<UserData, String> {
        return authApiService.getUserData()
    }

    override suspend fun putUserData(
        username: String,
        email: String
    ): BaseAuthResponse<UserData, String> {

        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("email", email)
            .addFormDataPart("username", username)
            .build()
        return authApiService.putUserData(requestBody)
    }
}