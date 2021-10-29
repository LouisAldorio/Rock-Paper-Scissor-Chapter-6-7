package com.catnip.rockpaperscissorchapter6and7.ui.game.history

import android.util.Log
import com.catnip.rockpaperscissorchapter6and7.base.BasePresenterImpl
import com.catnip.rockpaperscissorchapter6and7.base.model.Resource
import com.catnip.rockpaperscissorchapter6and7.data.model.GameHistoryWithPlayer
import com.catnip.rockpaperscissorchapter6and7.data.model.Player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class GameHistoryPresenter(
    private val view: GameHistoryContract.View,
    private val repository: GameHistoryContract.Repository
) : GameHistoryContract.Presenter,
    BasePresenterImpl() {

    override fun getGameHistoryByPlayerId(playerId: Long?) {
        view.onDataCallback(Resource.Loading())
        scope.launch {
            try {
                val gameHistory = repository.getGameHistoryByPlayerId(playerId)
                scope.launch(Dispatchers.Main) {
//                    view.onDataCallback(Resource.Success(gameHistory))
                }
            } catch (e: Exception) {
                scope.launch(Dispatchers.Main) {
                    view.onDataCallback(Resource.Error(e.message.orEmpty()))
                }
            }
        }
    }

    override fun getGameHistoriesByPlayerId(playerId: Long?) {
        view.onDataCallback(Resource.Loading())
        scope.launch {
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