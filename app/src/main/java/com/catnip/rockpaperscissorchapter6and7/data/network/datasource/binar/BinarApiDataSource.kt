package com.catnip.rockpaperscissorchapter6and7.data.network.datasource.binar

import com.catnip.rockpaperscissorchapter6and7.data.network.model.request.binar.RegisterRequest
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.binar.BaseResponse
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.binar.UserData


interface BinarApiDataSource {
    suspend fun postRegisterUser(registerRequest: RegisterRequest): BaseResponse<UserData, String>
}