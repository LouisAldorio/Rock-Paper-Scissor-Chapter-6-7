package com.catnip.rockpaperscissorchapter6and7.ui.game.gameplay

import com.catnip.rockpaperscissorchapter6and7.data.local.room.datasource.GameHistoryDataSource
import com.catnip.rockpaperscissorchapter6and7.data.model.GameHistory
import com.catnip.rockpaperscissorchapter6and7.data.network.datasource.auth.AuthApiDataSource
import com.catnip.rockpaperscissorchapter6and7.data.network.model.request.binar.GameHistoryRequest
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.BaseAuthResponse
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.GameHistoryData

class GamePlayRepository(
    private val dataSource: GameHistoryDataSource,
    private val remoteDataSource: AuthApiDataSource
) : GamePlayContract.Repository {

    override suspend fun insertGameHistory(gameHistory: GameHistory): Long {
        return dataSource.insertGameHistory(gameHistory)
    }

    override suspend fun postHistoryBattle(gameHistoryRequest: GameHistoryRequest): BaseAuthResponse<GameHistoryData, String> {
        return remoteDataSource.postHistoryBattle(gameHistoryRequest)
    }

}