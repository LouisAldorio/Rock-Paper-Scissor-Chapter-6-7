package com.catnip.rockpaperscissorchapter6and7.ui.game.gameplay

import androidx.lifecycle.LiveData
import com.catnip.rockpaperscissorchapter6and7.base.BaseContract
import com.catnip.rockpaperscissorchapter6and7.base.model.Resource
import com.catnip.rockpaperscissorchapter6and7.data.model.GameHistory
import com.catnip.rockpaperscissorchapter6and7.data.model.GameHistoryWithPlayer
import com.catnip.rockpaperscissorchapter6and7.data.model.Player
import com.catnip.rockpaperscissorchapter6and7.data.network.model.request.binar.GameHistoryRequest
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.BaseAuthResponse
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.GameHistoryData
import com.catnip.rockpaperscissorchapter6and7.enumeration.GameResult

interface GamePlayContract {

    interface View: BaseContract.BaseView {
        fun getIntentData()
        fun resetState()
        fun initListeners()
        fun disableClickAfterComparison()
        fun showResultDialog(gameResult : GameResult)
    }

    interface ViewModel {
        fun insertGameHistoryLiveData(): LiveData<Resource<Long>>
        fun insertGameHistory(gameHistory: GameHistory)

        fun postRemoteGameHistoryLiveData(): LiveData<Resource<BaseAuthResponse<GameHistoryData, String>>>
        fun postRemoteGameHistory(gameHistoryRequest: GameHistoryRequest)
    }

    interface Repository {
        suspend fun insertGameHistory(gameHistory: GameHistory): Long
        suspend fun postHistoryBattle(gameHistoryRequest: GameHistoryRequest): BaseAuthResponse<GameHistoryData, String>
    }
}