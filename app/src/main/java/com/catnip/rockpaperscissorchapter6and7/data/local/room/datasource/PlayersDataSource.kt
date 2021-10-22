package com.catnip.rockpaperscissorchapter6and7.data.local.room.datasource

import com.catnip.rockpaperscissorchapter6and7.data.model.Player

interface PlayersDataSource {
    suspend fun getAllPlayers(): List<Player>
    suspend fun insertPlayer(player: Player): Long
}