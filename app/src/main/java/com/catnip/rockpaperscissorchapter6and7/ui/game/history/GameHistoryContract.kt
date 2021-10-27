package com.catnip.rockpaperscissorchapter6and7.ui.game.history

import com.catnip.rockpaperscissorchapter6and7.base.BaseContract
import com.catnip.rockpaperscissorchapter6and7.base.model.Resource
import com.catnip.rockpaperscissorchapter6and7.data.model.GameHistory
import com.catnip.rockpaperscissorchapter6and7.data.model.PlayerWithGameHistory

interface GameHistoryContract {
    interface View: BaseContract.BaseView {
        fun getData()
        fun setGameHistoryData(data: List<PlayerWithGameHistory>)
        fun onDataCallback(response: Resource<List<PlayerWithGameHistory>>)
        fun setClickListeners()
        fun initList()
    }

    interface Presenter: BaseContract.BasePresenter {
        fun getAllGameHistory()
        fun getGameHistory(playerId: Int)
    }

    interface Repository {
        suspend fun getAllGameHistory(): List<GameHistory>
        suspend fun getGameHistory(playerId: Int): List<PlayerWithGameHistory>
    }
}