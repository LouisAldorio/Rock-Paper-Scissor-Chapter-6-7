package com.catnip.rockpaperscissorchapter6and7.ui.game.mode

import com.catnip.rockpaperscissorchapter6and7.base.BaseViewModelContract

interface GameModeContract {
    interface View : BaseViewModelContract.BaseView {
        fun setClickListeners()
        fun showDialogChoosePlayer()
    }

    interface ViewModel {

    }
}