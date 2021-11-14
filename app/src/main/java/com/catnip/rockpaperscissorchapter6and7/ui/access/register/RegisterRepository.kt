package com.catnip.rockpaperscissorchapter6and7.ui.access.register

import com.catnip.rockpaperscissorchapter6and7.data.network.datasource.binar.BinarApiDataSource
import com.catnip.rockpaperscissorchapter6and7.data.network.model.request.binar.RegisterRequest
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.binar.BaseResponse
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.binar.RegisterData

class RegisterRepository(private val binarApiDataSource: BinarApiDataSource): RegisterContract.Repository {
    override suspend fun postRegisterUser(registerRequest: RegisterRequest): BaseResponse<RegisterData, String> {
        return binarApiDataSource.postRegisterUser(registerRequest)
    }
}