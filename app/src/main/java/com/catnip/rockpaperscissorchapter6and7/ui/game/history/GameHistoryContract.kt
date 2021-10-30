package com.catnip.rockpaperscissorchapter6and7.ui.game.history

import com.catnip.rockpaperscissorchapter6and7.base.BaseContract
import com.catnip.rockpaperscissorchapter6and7.base.model.Resource
import com.catnip.rockpaperscissorchapter6and7.data.model.GameHistory
import com.catnip.rockpaperscissorchapter6and7.data.model.GameHistoryWithPlayer
import com.catnip.rockpaperscissorchapter6and7.data.model.Player

interface GameHistoryContract {
    interface View: BaseContract.BaseView {
        fun getData()
        fun setGameHistoryData(data: List<GameHistoryWithPlayer>)
        fun onDataCallback(response: Resource<List<GameHistoryWithPlayer>>)
        fun setClickListeners()
        fun initList()


    }

    interface Presenter: BaseContract.BasePresenter {
        fun getGameHistoryByPlayerId(playerId: Long?)
        fun getGameHistoriesByPlayerId(playerId: Long?)
    }

    interface Repository {
        suspend fun getGameHistoryByPlayerId(playerId: Long?): List<GameHistoryWithPlayer>
        suspend fun getGameHistoriesByPlayerId(playerId: Long?): List<GameHistory>
        suspend fun getPlayerByWhereInIDs(playerIds: List<Long>): List<Player>
    }
}