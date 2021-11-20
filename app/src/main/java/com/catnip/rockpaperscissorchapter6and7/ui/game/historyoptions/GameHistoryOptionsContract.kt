package com.catnip.rockpaperscissorchapter6and7.ui.game.historyoptions

import androidx.lifecycle.LiveData
import com.catnip.rockpaperscissorchapter6and7.base.BaseContract
import com.catnip.rockpaperscissorchapter6and7.base.BaseViewModelContract
import com.catnip.rockpaperscissorchapter6and7.base.model.Resource
import com.catnip.rockpaperscissorchapter6and7.data.model.GameHistory
import com.catnip.rockpaperscissorchapter6and7.data.model.GameHistoryWithPlayer
import com.catnip.rockpaperscissorchapter6and7.data.model.Player
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.BaseAuthResponse
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.GameHistoryData

interface GameHistoryOptionsContract {
    interface View : BaseViewModelContract.BaseView {
        fun setClickListeners()
    }

    interface ViewModel {

    }
}