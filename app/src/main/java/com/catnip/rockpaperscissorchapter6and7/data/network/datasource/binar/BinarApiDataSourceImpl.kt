package com.catnip.rockpaperscissorchapter6and7.data.network.datasource.binar

import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.BaseAuthResponse
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.UserData
import com.catnip.rockpaperscissorchapter6and7.data.network.model.services.BinarApiService

class BinarApiDataSourceImpl(private val binarApiService: BinarApiService) : BinarApiDataSource {
    override suspend fun getSyncUser(): BaseAuthResponse<UserData, String> {
        return binarApiService.getSyncUser()
    }
}