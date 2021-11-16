package com.catnip.rockpaperscissorchapter6and7.data.network.datasource.auth

import com.catnip.rockpaperscissorchapter6and7.data.network.model.request.binar.RegisterRequest
import com.catnip.rockpaperscissorchapter6and7.data.network.model.request.auth.AuthRequest
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.BaseAuthResponse
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.UserData
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.BaseResponse
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.RegisterData

interface AuthApiDataSource {

    suspend fun postLoginUser(loginRequest: AuthRequest): BaseAuthResponse<UserData, String>


    suspend fun getUserData() : BaseAuthResponse<UserData, String>
    suspend fun postRegisterUser(registerRequest: RegisterRequest): BaseResponse<RegisterData, String>
    suspend fun getSyncUser(): BaseAuthResponse<UserData, String>
    suspend fun putUserData(username : String, email : String) : BaseAuthResponse<UserData, String>
}