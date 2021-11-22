package com.catnip.rockpaperscissorchapter6and7.data.network.datasource.auth

import com.catnip.rockpaperscissorchapter6and7.data.network.model.request.binar.RegisterRequest
import com.catnip.rockpaperscissorchapter6and7.data.network.model.request.auth.AuthRequest
import com.catnip.rockpaperscissorchapter6and7.data.network.model.request.binar.GameHistoryRequest
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.*
import com.catnip.rockpaperscissorchapter6and7.data.network.services.AuthApiService
import okhttp3.MultipartBody

class AuthApiDataSourceImpl(private val authApiService: AuthApiService) : AuthApiDataSource {

    override suspend fun postLoginUser(loginRequest: AuthRequest): BaseAuthResponse<UserData, String> {
        return authApiService.postLoginUser(loginRequest)
    }

    override suspend fun getUserData(): BaseAuthResponse<UserData, String> {
        return authApiService.getUserData()
    }

    override suspend fun postRegisterUser(registerRequest: RegisterRequest): BaseResponse<RegisterData, String> {
        return authApiService.postRegisterUser(registerRequest)
    }

    override suspend fun getSyncUser(): BaseAuthResponse<UserData, String> {
        return authApiService.getSyncUser()
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

    override suspend fun getHistoryBattle(): BaseAuthResponse<List<GameHistoryData>, String> {
        return authApiService.getHistoryBattle()
    }

    override suspend fun postHistoryBattle(gameHistoryRequest: GameHistoryRequest): BaseAuthResponse<GameHistoryData, String> {
        return authApiService.postHistoryBattle(gameHistoryRequest)
    }
}