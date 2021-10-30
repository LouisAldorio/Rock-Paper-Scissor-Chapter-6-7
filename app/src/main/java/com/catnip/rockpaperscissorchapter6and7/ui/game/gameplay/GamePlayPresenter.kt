package com.catnip.rockpaperscissorchapter6and7.ui.game.gameplay

import android.util.Log
import com.catnip.rockpaperscissorchapter6and7.base.BasePresenterImpl
import com.catnip.rockpaperscissorchapter6and7.base.model.Resource
import com.catnip.rockpaperscissorchapter6and7.data.model.GameHistory
import com.catnip.rockpaperscissorchapter6and7.data.model.Player
import com.catnip.rockpaperscissorchapter6and7.enumeration.GameResult
import com.catnip.rockpaperscissorchapter6and7.ui.game.MenuContract
import com.catnip.rockpaperscissorchapter6and7.ui.game.mode.dialog.PlayerMenusContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class GamePlayPresenter(
    private val view: GamePlayContract.View,
    private val repository: GamePlayContract.Repository
) : GamePlayContract.Presenter,
    BasePresenterImpl() {

    private val tag = GamePlayPresenter::class.java.simpleName


    override fun compare(enemyChoice: Int, player : Player) {
        if ((player.choice + 1) % 3 == enemyChoice) {

            // enemy win
            view.showResultDialog(GameResult.LOSE)

        } else if (player.choice == enemyChoice) {

            //draw
            view.showResultDialog(GameResult.DRAW)

        } else {

            //player win
            view.showResultDialog(GameResult.WIN)
        }

        view.disableClickAfterComparison()
    }

    override fun insertGameHistory(gameHistory: GameHistory) {
        scope.launch {
            try {
                val gameHistory = repository.insertGameHistory(gameHistory)
                Log.d("insertGameHistory", "insertGameHistory: Success with id = $gameHistory")
            } catch (e: Exception) {
            }
        }
    }

}