package com.catnip.rockpaperscissorchapter6and7.data.network.datasource.auth

import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.BaseAuthResponse
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.UserData

interface AuthApiDataSource {
    suspend fun getSyncUser(): BaseAuthResponse<UserData, String>
}