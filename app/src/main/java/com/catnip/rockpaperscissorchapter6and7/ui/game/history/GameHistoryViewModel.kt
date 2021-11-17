package com.catnip.rockpaperscissorchapter6and7.ui.game.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catnip.rockpaperscissorchapter6and7.base.model.Resource
import com.catnip.rockpaperscissorchapter6and7.data.model.GameHistory
import com.catnip.rockpaperscissorchapter6and7.data.model.GameHistoryWithPlayer
import com.catnip.rockpaperscissorchapter6and7.data.model.Player
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.BaseAuthResponse
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.GameHistoryData
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class GameHistoryViewModel(private val repository: GameHistoryRepository): ViewModel(), GameHistoryContract.ViewModel {

    private val getGameHistoryLiveData =
        MutableLiveData<Resource<BaseAuthResponse<List<GameHistoryData>, String>>>()

    override fun getRemoteGameHistoryLiveData(): LiveData<Resource<BaseAuthResponse<List<GameHistoryData>, String>>> = getGameHistoryLiveData

    private val getGameLocalHistoryLiveData =
        MutableLiveData<Resource<ArrayList<GameHistoryWithPlayer>>>()

    override fun getLocalGameHistoryLiveData(): LiveData<Resource<ArrayList<GameHistoryWithPlayer>>> = getGameLocalHistoryLiveData
    override fun getLocalGameHistory(playerId: Long?) {

    }

    override fun getGameHistoryByPlayerId(playerId: Long?) {
    }

    override fun getGameHistoriesByPlayerId(playerId: Long?) {
        getGameLocalHistoryLiveData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val gameHistory = arrayListOf<GameHistoryWithPlayer>()
                val gameHistories = repository.getGameHistoriesByPlayerId(playerId)


                val playerIds = arrayListOf<Long>()
                gameHistories.forEach {
                    if(it.player1Id !in playerIds) {
                        it.player1Id?.let { it1 -> playerIds.add(it1) }
                    }

                    if(it.player2Id !in playerIds) {
                        it.player2Id?.let { it2 -> playerIds.add(it2) }
                    }
                }

                val players = repository.getPlayerByWhereInIDs(playerIds)
                val playerMappedById = mutableMapOf<Long, Player>()
                players.forEach {
                    it.id?.let { it1 -> playerMappedById.put(it1, it) }
                }

                gameHistories.forEach {

                    val player1 = playerMappedById[it.player1Id!!]?.let { it1 -> Player(playerMappedById[it.player1Id!!]?.id, it1.name) }
                    if (player1 != null) {
                        player1.choice = it.player1Hero!!
                    }

                    val player2 = Player(null, "COM")
                    player2.choice = it.player2Hero!!
                    if(it.player2Id != null) {
                        player2.id = it.player2Id
                        player2.name = playerMappedById[it.player2Id!!]?.name ?: ""
                    }

                    player1?.let { it1 -> GameHistoryWithPlayer(it, it1, player2) }?.let { it2 ->
                        gameHistory.add(
                            it2
                        )
                    }
                }
                viewModelScope.launch(Dispatchers.Main) {
                    getGameLocalHistoryLiveData.value = Resource.Success(gameHistory)
                }
            } catch (cause: Throwable) {
                when(cause) {
                    is HttpException -> {
                        cause.response()?.errorBody()?.source()?.let {
                            val error = Gson().fromJson(it.readString(Charsets.UTF_8), BaseAuthResponse::class.java)
                            viewModelScope.launch(Dispatchers.Main) {
                                getGameLocalHistoryLiveData.value = Resource.Error(error.errorMsg.toString(), null)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun getRemoteGameHistory() {
        getGameHistoryLiveData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getRemoteGameHistory()
                viewModelScope.launch(Dispatchers.Main) {
                    getGameHistoryLiveData.value = Resource.Success(response)
                }
            } catch (cause: Throwable) {
                when(cause) {
                    is HttpException -> {
                        cause.response()?.errorBody()?.source()?.let {
                            val error = Gson().fromJson(it.readString(Charsets.UTF_8), BaseAuthResponse::class.java)
                            viewModelScope.launch(Dispatchers.Main) {
                                getGameHistoryLiveData.value = Resource.Error(error.errorMsg.toString(), null)
                            }
                        }
                    }
                }
            }
        }
    }





}