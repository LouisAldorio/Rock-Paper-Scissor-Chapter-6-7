package com.catnip.rockpaperscissorchapter6and7.ui.auth.register

import com.catnip.rockpaperscissorchapter6and7.data.network.datasource.auth.AuthApiDataSource
import com.catnip.rockpaperscissorchapter6and7.data.network.model.request.binar.RegisterRequest
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.BaseResponse
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.RegisterData

class RegisterRepository(private val authApiDataSource: AuthApiDataSource): RegisterContract.Repository {
    override suspend fun postRegisterUser(registerRequest: RegisterRequest): BaseResponse<RegisterData, String> {
        return authApiDataSource.postRegisterUser(registerRequest)
    }
}