package com.catnip.rockpaperscissorchapter6and7.ui.game.gameplay

import android.content.Context
import android.content.Intent
import android.util.Log
import com.catnip.rockpaperscissorchapter6and7.base.BaseActivity
import com.catnip.rockpaperscissorchapter6and7.data.local.room.PlayersDatabase
import com.catnip.rockpaperscissorchapter6and7.data.local.room.datasource.PlayersDataSourceImpl
import com.catnip.rockpaperscissorchapter6and7.data.model.Player
import com.catnip.rockpaperscissorchapter6and7.databinding.ActivityGamePlayBinding
import com.catnip.rockpaperscissorchapter6and7.enumeration.GameType
import com.catnip.rockpaperscissorchapter6and7.ui.game.mode.dialog.PlayerMenusRepository
import com.catnip.rockpaperscissorchapter6and7.utils.Constant

class GamePlayActivity : BaseActivity<ActivityGamePlayBinding, GamePlayContract.Presenter>(
    ActivityGamePlayBinding::inflate
), GamePlayContract.View {

    private var gameType : GameType? = null
    private lateinit var player : Player

    companion object {
        @JvmStatic
        fun startActivity(context: Context, gameType : GameType, player: Player) {
            val intent = Intent(context, GamePlayActivity::class.java)
            intent.putExtra(Constant.GAME_TYPE_KEY, gameType)
            intent.putExtra(Constant.PLAYER, player)
            context.startActivity(intent)
        }
    }

    override fun initView() {
        supportActionBar?.hide()
        getIntentData()
    }

    override fun initPresenter() {
        // sesuaikan , untuk saat ini pakai player dlo, ntar pake history punya
        val dataSource = PlayersDataSourceImpl(PlayersDatabase.getInstance(this).playersDao())
        val repository = PlayerMenusRepository(dataSource)
        setPresenter(GamePlayPresenter(this, repository))
    }

    override fun getIntentData() {
        gameType = intent.extras?.getSerializable(Constant.GAME_TYPE_KEY) as GameType
        player = intent.extras?.get(Constant.PLAYER) as Player

        Log.i(GamePlayActivity::class.java.simpleName, gameType.toString())
        Log.i(GamePlayActivity::class.java.simpleName, player.toString())
    }

    override fun resetState() {
        TODO("Not yet implemented")
    }

}