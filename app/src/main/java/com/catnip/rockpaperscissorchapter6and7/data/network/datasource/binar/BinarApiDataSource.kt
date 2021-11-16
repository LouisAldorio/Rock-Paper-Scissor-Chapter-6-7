package com.catnip.rockpaperscissorchapter6and7.data.network.datasource.binar

import com.catnip.rockpaperscissorchapter6and7.data.network.model.request.binar.RegisterRequest
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.binar.BaseResponse
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.binar.RegisterData
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.BaseAuthResponse
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.UserData


interface BinarApiDataSource {
    suspend fun postRegisterUser(registerRequest: RegisterRequest): BaseResponse<RegisterData, String>
    suspend fun getSyncUser(): BaseAuthResponse<UserData, String>
}