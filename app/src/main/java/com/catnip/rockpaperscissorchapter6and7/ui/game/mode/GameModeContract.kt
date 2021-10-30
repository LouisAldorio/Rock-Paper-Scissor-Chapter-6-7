package com.catnip.rockpaperscissorchapter6and7.ui.game.mode

import com.catnip.rockpaperscissorchapter6and7.base.BaseContract

interface GameModeContract {
    interface View : BaseContract.BaseView {
        fun setClickListeners()
        fun showDialogChoosePlayer()
    }

    interface Presenter : BaseContract.BasePresenter {

    }
}