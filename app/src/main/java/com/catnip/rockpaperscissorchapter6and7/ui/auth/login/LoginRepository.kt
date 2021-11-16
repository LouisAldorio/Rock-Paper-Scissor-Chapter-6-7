package com.catnip.rockpaperscissorchapter6and7.ui.auth.login

import com.catnip.rockpaperscissorchapter6and7.data.network.datasource.auth.AuthApiDataSource
import com.catnip.rockpaperscissorchapter6and7.data.network.model.request.auth.AuthRequest
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.BaseAuthResponse
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.UserData


class LoginRepository(private val dataSource: AuthApiDataSource) : LoginContract.Repository {
    override suspend fun postLoginUser(loginRequest: AuthRequest): BaseAuthResponse<UserData, String> {
        return dataSource.postLoginUser(loginRequest)
    }


}