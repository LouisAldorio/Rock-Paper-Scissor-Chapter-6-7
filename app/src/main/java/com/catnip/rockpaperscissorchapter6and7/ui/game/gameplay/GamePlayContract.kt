package com.catnip.rockpaperscissorchapter6and7.ui.game.gameplay

import com.catnip.rockpaperscissorchapter6and7.base.BaseContract
import com.catnip.rockpaperscissorchapter6and7.base.model.Resource
import com.catnip.rockpaperscissorchapter6and7.data.model.GameHistory
import com.catnip.rockpaperscissorchapter6and7.data.model.Player
import com.catnip.rockpaperscissorchapter6and7.enumeration.GameResult

interface GamePlayContract {

    interface View: BaseContract.BaseView {
        fun getIntentData()
        fun resetState()
        fun initListeners()
        fun disableClickAfterComparison()
        fun showResultDialog(gameResult : GameResult)
    }

    interface Presenter: BaseContract.BasePresenter {
        fun compare(enemyChoice : Int, player : Player)
        fun insertGameHistory(gameHistory: GameHistory)
    }

    interface Repository {
        suspend fun insertGameHistory(gameHistory: GameHistory): Long
    }
}