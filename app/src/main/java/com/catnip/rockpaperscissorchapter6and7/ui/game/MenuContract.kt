package com.catnip.rockpaperscissorchapter6and7.ui.game

import com.catnip.rockpaperscissorchapter6and7.base.BaseViewModelContract

interface MenuContract {

    interface View : BaseViewModelContract.BaseView {
        fun setClickListeners()
    }

    interface ViewModel {

    }
}