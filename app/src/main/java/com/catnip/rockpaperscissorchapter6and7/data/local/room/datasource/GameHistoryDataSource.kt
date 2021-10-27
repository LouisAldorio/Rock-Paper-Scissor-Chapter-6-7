package com.catnip.rockpaperscissorchapter6and7.data.local.room.datasource

import com.catnip.rockpaperscissorchapter6and7.data.model.GameHistory
import com.catnip.rockpaperscissorchapter6and7.data.model.PlayerWithGameHistory

interface GameHistoryDataSource {
    suspend fun getAllGameHistory(): List<GameHistory>
    suspend fun getGameHistory(playerId: Int): List<PlayerWithGameHistory>
    suspend fun insertGameHistory(gameHistory: GameHistory): Long
}