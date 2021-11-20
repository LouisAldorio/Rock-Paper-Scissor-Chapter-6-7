package com.catnip.rockpaperscissorchapter6and7.ui.game

import com.catnip.rockpaperscissorchapter6and7.base.BaseViewModelContract
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.BaseAuthResponse
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.UserData

interface MenuContract {

    interface View : BaseViewModelContract.BaseView {
        fun setClickListeners()
    }

    interface ViewModel {
        fun deleteSession()
    }

    interface Repository{
        fun deleteSession()
    }
}