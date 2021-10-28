package com.catnip.rockpaperscissorchapter6and7.ui.game.gameplay

import com.catnip.rockpaperscissorchapter6and7.data.local.room.datasource.GameHistoryDataSource

class GamePlayRepository(
    private val dataSource: GameHistoryDataSource
) : GamePlayContract.Repository {

}