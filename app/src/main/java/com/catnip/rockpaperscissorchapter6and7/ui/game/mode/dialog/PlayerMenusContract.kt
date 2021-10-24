package com.catnip.rockpaperscissorchapter6and7.ui.game.mode.dialog

import com.catnip.rockpaperscissorchapter6and7.base.BaseContract
import com.catnip.rockpaperscissorchapter6and7.base.model.Resource
import com.catnip.rockpaperscissorchapter6and7.data.model.Player

interface PlayerMenusContract {
    interface View: BaseContract.BaseView {
        fun getData()
        fun setMenusData(data: List<Player>)
        fun onDataCallback(response: Resource<List<Player>>)
        fun setClickListeners()
    }

    interface Presenter: BaseContract.BasePresenter {
        fun getAllPlayers()
        fun insertPlayer(player: Player)
    }

    interface Repository {
        suspend fun getAllPlayers(): List<Player>
        suspend fun insertPlayer(player: Player): Long
    }
}