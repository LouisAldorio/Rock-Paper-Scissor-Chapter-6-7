package com.catnip.rockpaperscissorchapter6and7.ui.auth.login

import com.catnip.rockpaperscissorchapter6and7.data.local.preference.datasource.LocalDataSource
import com.catnip.rockpaperscissorchapter6and7.data.local.room.PlayersDatabase
import com.catnip.rockpaperscissorchapter6and7.data.local.room.datasource.GameHistoryDataSource
import com.catnip.rockpaperscissorchapter6and7.data.local.room.datasource.PlayersDataSource
import com.catnip.rockpaperscissorchapter6and7.data.model.Player
import com.catnip.rockpaperscissorchapter6and7.data.network.datasource.auth.AuthApiDataSource
import com.catnip.rockpaperscissorchapter6and7.data.network.model.request.auth.AuthRequest
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.BaseAuthResponse
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.UserData


class LoginRepository(
    private val dataSource: AuthApiDataSource,
    private val localDataSource: LocalDataSource,
    private val playerDataSource: PlayersDataSource
) : LoginContract.Repository {

    override fun saveSession(authToken: String) {
        localDataSource.setAuthToken(authToken)
    }

    override fun saveUserPreference(player : Player) {
        localDataSource.saveUser(player)
    }

    override suspend fun postLoginUser(loginRequest: AuthRequest): BaseAuthResponse<UserData, String> {
        return dataSource.postLoginUser(loginRequest)
    }

    override suspend fun getPlayerByUsername(username: String): Player {
        return playerDataSource.getPlayerByUsername(username)
    }

    override suspend fun getAllPlayers(): List<Player> {
        return playerDataSource.getAllPlayers()
    }

    override suspend fun insertPlayer(player: Player): Long {
        return playerDataSource.insertPlayer(player)
    }
}