package com.catnip.rockpaperscissorchapter6and7.ui.auth.register

import com.catnip.rockpaperscissorchapter6and7.data.local.room.datasource.PlayersDataSource
import com.catnip.rockpaperscissorchapter6and7.data.model.Player
import com.catnip.rockpaperscissorchapter6and7.data.network.datasource.auth.AuthApiDataSource
import com.catnip.rockpaperscissorchapter6and7.data.network.model.request.binar.RegisterRequest
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.BaseResponse
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.RegisterData

class RegisterRepository(
    private val authApiDataSource: AuthApiDataSource,
    private val playersDataSource: PlayersDataSource
) : RegisterContract.Repository {
    override suspend fun postRegisterUser(registerRequest: RegisterRequest): BaseResponse<RegisterData, String> {
        return authApiDataSource.postRegisterUser(registerRequest)
    }

    override suspend fun getAllPlayers(): List<Player> {
        return playersDataSource.getAllPlayers()
    }

    override suspend fun insertPlayer(player: Player): Long {
        return playersDataSource.insertPlayer(player)
    }
}