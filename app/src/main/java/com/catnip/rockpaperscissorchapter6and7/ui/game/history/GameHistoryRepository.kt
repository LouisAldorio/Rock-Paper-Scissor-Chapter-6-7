package com.catnip.rockpaperscissorchapter6and7.ui.game.history

import com.catnip.rockpaperscissorchapter6and7.data.local.room.datasource.GameHistoryDataSource
import com.catnip.rockpaperscissorchapter6and7.data.model.GameHistory
import com.catnip.rockpaperscissorchapter6and7.data.model.GameHistoryWithPlayer


class GameHistoryRepository(
    private val dataSource: GameHistoryDataSource
) : GameHistoryContract.Repository {
    override suspend fun getGameHistoryByPlayerId(playerId: Long?): List<GameHistoryWithPlayer> {
        return dataSource.getGameHistoryByPlayerId(playerId)
    }
}