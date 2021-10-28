package com.catnip.rockpaperscissorchapter6and7.ui.game.history

import com.catnip.rockpaperscissorchapter6and7.base.BasePresenterImpl
import com.catnip.rockpaperscissorchapter6and7.base.model.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class GameHistoryPresenter(
    private val view: GameHistoryContract.View,
    private val repository: GameHistoryContract.Repository
) : GameHistoryContract.Presenter,
    BasePresenterImpl() {
    override fun getGameHistoryByPlayerId(playerId: Int) {
        view.onDataCallback(Resource.Loading())
        scope.launch {
            try {
                val gameHistory = repository.getGameHistoryByPlayerId(playerId)
                scope.launch(Dispatchers.Main) {
                    view.onDataCallback(Resource.Success(gameHistory))
                }
            } catch (e: Exception) {
                scope.launch(Dispatchers.Main) {
                    view.onDataCallback(Resource.Error(e.message.orEmpty()))
                }
            }
        }
    }

}