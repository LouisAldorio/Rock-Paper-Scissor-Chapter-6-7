package com.catnip.rockpaperscissorchapter6and7.data.local.room.datasource

import com.catnip.rockpaperscissorchapter6and7.data.model.GameHistory
import com.catnip.rockpaperscissorchapter6and7.data.model.GameHistoryWithPlayer

interface GameHistoryDataSource {
    suspend fun getGameHistoryByPlayerId(playerId: Long?): List<GameHistoryWithPlayer>
    suspend fun insertGameHistory(gameHistory: GameHistory): Long
    suspend fun getGameHistoriesByPlayerId(playerId: Long?): List<GameHistory>
}