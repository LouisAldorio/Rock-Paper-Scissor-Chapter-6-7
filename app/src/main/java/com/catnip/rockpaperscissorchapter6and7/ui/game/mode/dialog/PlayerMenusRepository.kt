package com.catnip.rockpaperscissorchapter6and7.ui.game.mode.dialog

import com.catnip.rockpaperscissorchapter6and7.data.local.room.datasource.PlayersDataSource
import com.catnip.rockpaperscissorchapter6and7.data.model.Player

class PlayerMenusRepository(
    private val dataSource: PlayersDataSource
) : PlayerMenusContract.Repository {
    override suspend fun getAllPlayers(): List<Player> {
        return dataSource.getAllPlayers()
    }

    override suspend fun insertPlayer(player: Player): Long {
        return dataSource.insertPlayer(player)
    }
}