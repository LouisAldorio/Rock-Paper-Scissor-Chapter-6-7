package com.catnip.rockpaperscissorchapter6and7.ui.game.gameplay

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catnip.rockpaperscissorchapter6and7.base.model.Resource
import com.catnip.rockpaperscissorchapter6and7.data.model.GameHistory
import com.catnip.rockpaperscissorchapter6and7.data.network.model.request.binar.GameHistoryRequest
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.BaseAuthResponse
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.GameHistoryData
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class GamePlayViewModel(private val repository: GamePlayRepository) : ViewModel(),
    GamePlayContract.ViewModel {

    private val insertGameHistoryLiveData =
        MutableLiveData<Resource<Long>>()
    override fun insertGameHistoryLiveData(): LiveData<Resource<Long>> = insertGameHistoryLiveData

    private val postGameHistoryResponseLiveData =
        MutableLiveData<Resource<BaseAuthResponse<GameHistoryData, String>>>()

    override fun postRemoteGameHistoryLiveData(): LiveData<Resource<BaseAuthResponse<GameHistoryData, String>>> = postGameHistoryResponseLiveData

    override fun insertGameHistory(gameHistory: GameHistory) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.insertGameHistory(gameHistory)
                viewModelScope.launch(Dispatchers.Main) {
                    insertGameHistoryLiveData.value = Resource.Success(response)
                }
            } catch (cause: Throwable) {
                when(cause) {
                    is HttpException -> {
                        cause.response()?.errorBody()?.source()?.let {
                            val error = Gson().fromJson(it.readString(Charsets.UTF_8), BaseAuthResponse::class.java)
                            viewModelScope.launch(Dispatchers.Main) {
                                insertGameHistoryLiveData.value = Resource.Error(error.errorMsg.toString(), null)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun postRemoteGameHistory(gameHistoryRequest: GameHistoryRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.postHistoryBattle(gameHistoryRequest)
                viewModelScope.launch(Dispatchers.Main) {
                    postGameHistoryResponseLiveData.value = Resource.Success(response)
                }
            } catch (cause: Throwable) {
                when(cause) {
                    is HttpException -> {
                        cause.response()?.errorBody()?.source()?.let {
                            val error = Gson().fromJson(it.readString(Charsets.UTF_8), BaseAuthResponse::class.java)
                            viewModelScope.launch(Dispatchers.Main) {
                                postGameHistoryResponseLiveData.value = Resource.Error(error.errorMsg.toString(), null)
                            }
                        }
                    }
                }
            }
        }
    }
}