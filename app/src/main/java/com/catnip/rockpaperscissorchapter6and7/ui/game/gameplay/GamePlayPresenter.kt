package com.catnip.rockpaperscissorchapter6and7.ui.game.gameplay

import android.util.Log
import com.catnip.rockpaperscissorchapter6and7.base.BasePresenterImpl
import com.catnip.rockpaperscissorchapter6and7.data.model.Player
import com.catnip.rockpaperscissorchapter6and7.ui.game.MenuContract
import com.catnip.rockpaperscissorchapter6and7.ui.game.mode.dialog.PlayerMenusContract

class GamePlayPresenter(
    private val view: GamePlayContract.View,
    private val repository: PlayerMenusContract.Repository
) : GamePlayContract.Presenter,
    BasePresenterImpl() {

    private val tag = GamePlayPresenter::class.java.simpleName


    override fun compare(enemyChoice: Int, player : Player) {
        if ((player.choice + 1) % 3 == enemyChoice) {

            // enemy win


        } else if (player.choice == enemyChoice) {

            //draw


        } else {

            //player win

        }

        view.disableClickAfterComparison()
    }

}