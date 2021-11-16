package com.catnip.rockpaperscissorchapter6and7.data.network.datasource.binar

import com.catnip.rockpaperscissorchapter6and7.data.network.model.request.binar.RegisterRequest
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.binar.BaseResponse
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.binar.RegisterData
import com.catnip.rockpaperscissorchapter6and7.data.network.services.BinarApiService
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.BaseAuthResponse
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.UserData
import com.catnip.rockpaperscissorchapter6and7.data.network.model.services.BinarApiService

class BinarApiDataSourceImpl(private val binarApiService: BinarApiService) : BinarApiDataSource {
    override suspend fun postRegisterUser(registerRequest: RegisterRequest): BaseResponse<RegisterData, String> {
        return binarApiService.postRegisterUser(registerRequest)
    }
   
   override suspend fun getSyncUser(): BaseAuthResponse<UserData, String> {
        return binarApiService.getSyncUser()
    }
}