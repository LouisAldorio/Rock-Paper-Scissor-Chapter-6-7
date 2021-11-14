package com.catnip.rockpaperscissorchapter6and7.data.network.datasource.binar

import com.catnip.rockpaperscissorchapter6and7.data.network.model.request.binar.RegisterRequest
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.binar.BaseResponse
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.binar.RegisterData
import com.catnip.rockpaperscissorchapter6and7.data.network.services.BinarApiService

class BinarApiDataSourceImpl(private val binarApiService: BinarApiService) : BinarApiDataSource {
    override suspend fun postRegisterUser(registerRequest: RegisterRequest): BaseResponse<RegisterData, String> {
        return binarApiService.postRegisterUser(registerRequest)
    }
}