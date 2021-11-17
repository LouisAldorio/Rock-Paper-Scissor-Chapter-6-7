package com.catnip.rockpaperscissorchapter6and7.ui.game.history

import com.catnip.rockpaperscissorchapter6and7.data.local.room.datasource.GameHistoryDataSource
import com.catnip.rockpaperscissorchapter6and7.data.local.room.datasource.PlayersDataSource
import com.catnip.rockpaperscissorchapter6and7.data.model.GameHistory
import com.catnip.rockpaperscissorchapter6and7.data.model.GameHistoryWithPlayer
import com.catnip.rockpaperscissorchapter6and7.data.model.Player
import com.catnip.rockpaperscissorchapter6and7.data.network.datasource.auth.AuthApiDataSource
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.BaseAuthResponse
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.GameHistoryData


class GameHistoryRepository(
    private val dataSource: GameHistoryDataSource,
    private val playerDataSource : PlayersDataSource,
    private val remoteDataSource: AuthApiDataSource
) : GameHistoryContract.Repository {

    override suspend fun getGameHistoryByPlayerId(playerId: Long?): List<GameHistoryWithPlayer> {
        return dataSource.getGameHistoryByPlayerId(playerId)
    }

    override suspend fun getGameHistoriesByPlayerId(playerId: Long?): List<GameHistory> {
        return dataSource.getGameHistoriesByPlayerId(playerId)
    }

    override suspend fun getPlayerByWhereInIDs(playerIds: List<Long>): List<Player> {
        return playerDataSource.getPlayerByIDs(playerIds)
    }

    override suspend fun getRemoteGameHistory(): BaseAuthResponse<List<GameHistoryData>, String> {
        return remoteDataSource.getHistoryBattle()
    }
}