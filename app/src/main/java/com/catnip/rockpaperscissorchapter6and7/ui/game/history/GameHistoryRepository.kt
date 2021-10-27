package com.catnip.rockpaperscissorchapter6and7.ui.game.history

import com.catnip.rockpaperscissorchapter6and7.data.local.room.datasource.GameHistoryDataSource
import com.catnip.rockpaperscissorchapter6and7.data.model.GameHistory
import com.catnip.rockpaperscissorchapter6and7.data.model.PlayerWithGameHistory


class GameHistoryRepository(
    private val dataSource: GameHistoryDataSource
) : GameHistoryContract.Repository {
    override suspend fun getAllGameHistory(): List<GameHistory> {
        return dataSource.getAllGameHistory()
    }

    override suspend fun getGameHistory(playerId: Int): List<PlayerWithGameHistory> {
        return dataSource.getGameHistory(playerId)
    }
}