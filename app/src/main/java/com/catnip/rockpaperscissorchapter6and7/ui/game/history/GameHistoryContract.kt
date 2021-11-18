package com.catnip.rockpaperscissorchapter6and7.ui.game.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.catnip.rockpaperscissorchapter6and7.base.BaseContract
import com.catnip.rockpaperscissorchapter6and7.base.model.Resource
import com.catnip.rockpaperscissorchapter6and7.data.model.GameHistory
import com.catnip.rockpaperscissorchapter6and7.data.model.GameHistoryWithPlayer
import com.catnip.rockpaperscissorchapter6and7.data.model.Player
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.BaseAuthResponse
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.GameHistoryData
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.UserData

interface GameHistoryContract {
    interface View: BaseContract.BaseView {
        fun getData()
        fun setGameHistoryData(data: List<GameHistoryWithPlayer>)
        fun setRemoteGameHistory(data: List<GameHistoryData>)
        fun setClickListeners()
        fun initList()
        fun observeViewModel()
    }

    interface ViewModel {
        fun getGameHistoryByPlayerId(playerId: Long?)
        fun getGameHistoriesByPlayerId(playerId: Long?)

        fun getLocalGameHistoryLiveData(): LiveData<Resource<ArrayList<GameHistoryWithPlayer>>>
        fun getLocalGameHistory(playerId: Long?)

        fun getRemoteGameHistoryLiveData(): LiveData<Resource<BaseAuthResponse<List<GameHistoryData>, String>>>
        fun getRemoteGameHistory()
    }

    interface Repository {
        suspend fun getGameHistoryByPlayerId(playerId: Long?): List<GameHistoryWithPlayer>
        suspend fun getGameHistoriesByPlayerId(playerId: Long?): List<GameHistory>
        suspend fun getPlayerByWhereInIDs(playerIds: List<Long>): List<Player>
        suspend fun getRemoteGameHistory(): BaseAuthResponse<List<GameHistoryData>, String>
    }
}