package com.catnip.rockpaperscissorchapter6and7.ui.profile

import com.catnip.rockpaperscissorchapter6and7.data.local.preference.datasource.LocalDataSource
import com.catnip.rockpaperscissorchapter6and7.data.local.room.datasource.PlayersDataSource
import com.catnip.rockpaperscissorchapter6and7.data.model.Player
import com.catnip.rockpaperscissorchapter6and7.data.network.datasource.auth.AuthApiDataSource
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.BaseAuthResponse
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.UserData

class ProfileRepository(
    private val repository: AuthApiDataSource,
    private val playersDataSource: PlayersDataSource,
    private val localDataSource: LocalDataSource
) : ProfileContract.Repository {

    override suspend fun getProfileData(): BaseAuthResponse<UserData, String> {
        return repository.getUserData()
    }

    override suspend fun updateProfile(
        username: String,
        email: String
    ): BaseAuthResponse<UserData, String> {
        return repository.putUserData(username, email)
    }

    override suspend fun getPlayerByUsername(username: String): Player {
        return playersDataSource.getPlayerByUsername(username)
    }

    override suspend fun insertPlayer(player: Player): Long {
        return playersDataSource.insertPlayer(player)
    }

    override fun saveUser(player: Player) {
        localDataSource.saveUser(player)
    }
}