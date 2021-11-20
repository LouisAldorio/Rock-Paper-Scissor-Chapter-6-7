package com.catnip.rockpaperscissorchapter6and7.data.local.room.datasource

import com.catnip.rockpaperscissorchapter6and7.data.local.room.dao.PlayersDao
import com.catnip.rockpaperscissorchapter6and7.data.model.Player

class PlayersDataSourceImpl(private val dao: PlayersDao) : PlayersDataSource {
    override suspend fun getAllPlayers(): List<Player> {
        return dao.getAllPlayers()
    }

    override suspend fun insertPlayer(player: Player): Long {
        return dao.insertPlayer(player)
    }

    override suspend fun getPlayerByIDs(ids: List<Long>): List<Player> {
        return dao.getPlayerByIDs(ids)
    }

    override suspend fun getPlayerByUsername(username: String): Player {
        return dao.getPlayerByUsername(username)
    }
}