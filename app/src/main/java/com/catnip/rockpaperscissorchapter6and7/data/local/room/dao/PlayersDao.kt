package com.catnip.rockpaperscissorchapter6and7.data.local.room.dao

import androidx.room.*
import com.catnip.rockpaperscissorchapter6and7.data.model.Player

@Dao
interface PlayersDao {
    @Query("SELECT * FROM players")
    suspend fun getAllPlayers(): List<Player>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayer(player: Player): Long

    @Query("SELECT * FROM Players WHERE id IN (:ids)")
    suspend fun getPlayerByIDs(ids : List<Long>) : List<Player>

    @Query("SELECT * FROM Players WHERE name == :username")
    suspend fun getPlayerByUsername(username : String): Player
}