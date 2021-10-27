package com.catnip.rockpaperscissorchapter6and7.ui.game.gameplay

import com.catnip.rockpaperscissorchapter6and7.base.BaseContract
import com.catnip.rockpaperscissorchapter6and7.base.model.Resource
import com.catnip.rockpaperscissorchapter6and7.data.model.Player

interface GamePlayContract {

    interface View: BaseContract.BaseView {
        fun getIntentData()
        fun resetState()
        fun initListeners()
        fun disableClickAfterComparison()
        fun showResultDialog()
    }

    interface Presenter: BaseContract.BasePresenter {
        fun compare(enemyChoice : Int, player : Player)
    }

    interface Repository {

    }
}