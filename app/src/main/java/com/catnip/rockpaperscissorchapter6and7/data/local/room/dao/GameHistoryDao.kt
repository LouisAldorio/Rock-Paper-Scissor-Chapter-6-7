package com.catnip.rockpaperscissorchapter6and7.data.local.room.dao

import androidx.room.*
import com.catnip.rockpaperscissorchapter6and7.data.model.GameHistory
import com.catnip.rockpaperscissorchapter6and7.data.model.PlayerWithGameHistory

@Dao
interface GameHistoryDao {
    @Query("SELECT * FROM GameHistory")
    suspend fun getAllGameHistory(): List<GameHistory>

    @Transaction
    @Query("SELECT * FROM GameHistory WHERE player1_id == :playerId OR player2_id == :playerId")
    suspend fun getGameHistory(playerId: Int): List<PlayerWithGameHistory>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGameHistory(gameHistory: GameHistory): Long


}