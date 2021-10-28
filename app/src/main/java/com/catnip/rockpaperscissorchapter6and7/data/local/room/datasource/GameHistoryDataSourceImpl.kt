package com.catnip.rockpaperscissorchapter6and7.data.local.room.datasource

import com.catnip.rockpaperscissorchapter6and7.data.local.room.dao.GameHistoryDao
import com.catnip.rockpaperscissorchapter6and7.data.model.GameHistory
import com.catnip.rockpaperscissorchapter6and7.data.model.GameHistoryWithPlayer


class GameHistoryDataSourceImpl(private val dao: GameHistoryDao) : GameHistoryDataSource {
    override suspend fun getGameHistoryByPlayerId(playerId: Int): List<GameHistoryWithPlayer> {
        return dao.getGameHistoryByPlayerId(playerId)
    }

    override suspend fun insertGameHistory(gameHistory: GameHistory): Long {
        return dao.insertGameHistory(gameHistory)
    }

}