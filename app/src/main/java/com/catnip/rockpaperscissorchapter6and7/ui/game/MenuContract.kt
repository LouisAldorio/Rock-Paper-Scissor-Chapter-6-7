package com.catnip.rockpaperscissorchapter6and7.ui.game

import com.catnip.rockpaperscissorchapter6and7.base.BaseContract

interface MenuContract {

    interface View : BaseContract.BaseView {
        fun setClickListeners()
    }

    interface Presenter : BaseContract.BasePresenter {

    }
}