package com.catnip.rockpaperscissorchapter6and7.ui.game.gameplay

import com.catnip.rockpaperscissorchapter6and7.base.BaseContract
import com.catnip.rockpaperscissorchapter6and7.base.model.Resource

interface GamePlayContract {

    interface View: BaseContract.BaseView {
        fun getIntentData()
        fun resetState()
    }

    interface Presenter: BaseContract.BasePresenter {
        fun compare(enemyChoice : Int)
    }

    interface Repository {

    }
}