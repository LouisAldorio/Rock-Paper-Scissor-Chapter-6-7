package com.catnip.rockpaperscissorchapter6and7.ui.game.gameplay

import com.catnip.rockpaperscissorchapter6and7.data.local.room.datasource.GameHistoryDataSource
import com.catnip.rockpaperscissorchapter6and7.data.model.GameHistory

class GamePlayRepository(
    private val dataSource: GameHistoryDataSource
) : GamePlayContract.Repository {
    override suspend fun insertGameHistory(gameHistory: GameHistory): Long {
        return dataSource.insertGameHistory(gameHistory)
    }

}