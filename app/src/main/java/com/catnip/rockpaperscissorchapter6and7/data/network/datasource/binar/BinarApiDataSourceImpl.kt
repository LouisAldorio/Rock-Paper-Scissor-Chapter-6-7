package com.catnip.rockpaperscissorchapter6and7.data.network.datasource.binar

import com.catnip.mycoin.data.network.model.request.auth.AuthRequest
import com.catnip.mycoin.data.network.model.response.auth.BaseAuthResponse
import com.catnip.mycoin.data.network.model.response.auth.UserData
import com.catnip.rockpaperscissorchapter6and7.data.network.services.BinarApiService

class BinarApiDataSourceImpl(private val binarApiService: BinarApiService) : BinarApiDataSource {
    override suspend fun postRegisterUser(registerRequest: AuthRequest): BaseAuthResponse<UserData, String> {
        return binarApiService.postRegisterUser(registerRequest)
    }
}