package com.catnip.rockpaperscissorchapter6and7.ui.game.history

import com.catnip.rockpaperscissorchapter6and7.data.local.room.datasource.GameHistoryDataSource
import com.catnip.rockpaperscissorchapter6and7.data.local.room.datasource.PlayersDataSource
import com.catnip.rockpaperscissorchapter6and7.data.model.GameHistory
import com.catnip.rockpaperscissorchapter6and7.data.model.GameHistoryWithPlayer
import com.catnip.rockpaperscissorchapter6and7.data.model.Player


class GameHistoryRepository(
    private val dataSource: GameHistoryDataSource,
    private val playerDataSource : PlayersDataSource
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
}