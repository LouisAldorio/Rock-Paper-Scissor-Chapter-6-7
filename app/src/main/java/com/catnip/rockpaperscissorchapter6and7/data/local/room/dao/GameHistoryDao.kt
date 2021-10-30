package com.catnip.rockpaperscissorchapter6and7.data.local.room.dao

import androidx.room.*
import com.catnip.rockpaperscissorchapter6and7.data.model.GameHistory
import com.catnip.rockpaperscissorchapter6and7.data.model.GameHistoryWithPlayer

@Dao
interface GameHistoryDao {
    @Transaction
    @Query("SELECT * FROM GameHistory WHERE player1_id == :playerId OR player2_id == :playerId")
    suspend fun getGameHistoryByPlayerId(playerId: Long?): List<GameHistoryWithPlayer>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGameHistory(gameHistory: GameHistory): Long

    @Transaction
    @Query("SELECT * FROM GameHistory WHERE player1_id == :playerId OR player2_id == :playerId")
    suspend fun getGameHistoriesByPlayerId(playerId: Long?): List<GameHistory>
}