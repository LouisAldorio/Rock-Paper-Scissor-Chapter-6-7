package com.catnip.rockpaperscissorchapter6and7.data.local.room.datasource

import com.catnip.rockpaperscissorchapter6and7.data.local.room.dao.GameHistoryDao
import com.catnip.rockpaperscissorchapter6and7.data.model.GameHistory
import com.catnip.rockpaperscissorchapter6and7.data.model.PlayerWithGameHistory


class GameHistoryDataSourceImpl(private val dao: GameHistoryDao) : GameHistoryDataSource {
    override suspend fun getAllGameHistory(): List<GameHistory> {
        return dao.getAllGameHistory()
    }

    override suspend fun getGameHistory(playerId: Int): List<PlayerWithGameHistory> {
        return dao.getGameHistory(playerId)
    }

    override suspend fun insertGameHistory(gameHistory: GameHistory): Long {
        return dao.insertGameHistory(gameHistory)
    }

}