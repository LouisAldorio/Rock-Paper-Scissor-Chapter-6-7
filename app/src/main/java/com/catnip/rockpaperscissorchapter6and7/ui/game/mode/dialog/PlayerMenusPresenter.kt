package com.catnip.rockpaperscissorchapter6and7.ui.game.mode.dialog

import android.util.Log
import com.catnip.rockpaperscissorchapter6and7.base.BasePresenterImpl
import com.catnip.rockpaperscissorchapter6and7.base.model.Resource
import com.catnip.rockpaperscissorchapter6and7.data.model.Player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class PlayerMenusPresenter(
    private val view: PlayerMenusContract.View,
    private val repository: PlayerMenusContract.Repository
) : PlayerMenusContract.Presenter, BasePresenterImpl() {

    override fun getAllPlayers() {
        view.onDataCallback(Resource.Loading())
        scope.launch {
            try {
                val players = repository.getAllPlayers()
                scope.launch(Dispatchers.Main) {
                    view.onDataCallback(Resource.Success(players))
                }
            } catch (e: Exception) {
                scope.launch(Dispatchers.Main) {
                    view.onDataCallback(Resource.Error(e.message.orEmpty()))
                }
            }
        }
    }

    override fun insertPlayer(player: Player) {
        scope.launch {
            try {
                val playerId = repository.insertPlayer(player)
                scope.launch(Dispatchers.Main) {
                    Log.d("insertPlayer", "insertPlayer: $playerId")
                    view.onPlayerIDCallback(
                        Player(
                        id = playerId.toInt(),
                        name = player.name
                    ))
                }
            } catch (e: Exception) {
                scope.launch(Dispatchers.Main) {
                    view.onDataCallback(Resource.Error(e.message.orEmpty()))
                }
            }
        }
    }
}