package com.catnip.rockpaperscissorchapter6and7.ui.game.gameplay

import com.catnip.rockpaperscissorchapter6and7.base.BasePresenterImpl
import com.catnip.rockpaperscissorchapter6and7.ui.game.MenuContract
import com.catnip.rockpaperscissorchapter6and7.ui.game.mode.dialog.PlayerMenusContract

class GamePlayPresenter(
    private val view: GamePlayContract.View,
    private val repository: PlayerMenusContract.Repository
) : GamePlayContract.Presenter,
    BasePresenterImpl() {

    override fun compare(enemyChoice: Int) {
        TODO("Not yet implemented")
    }


}