package com.catnip.rockpaperscissorchapter6and7.ui.game.gameplay

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.catnip.rockpaperscissorchapter6and7.R
import com.catnip.rockpaperscissorchapter6and7.base.BaseActivity
import com.catnip.rockpaperscissorchapter6and7.data.local.preference.UserPreference
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

    private var gameType: GameType? = null
    private var player: Player? = null

    private lateinit var playerElements: Array<ImageView>
    private lateinit var enemyElements: Array<ImageView>

    private var enemy : Player? = null

    companion object {
        @JvmStatic
        fun startActivity(context: Context, gameType: GameType, player: Player) {
            val intent = Intent(context, GamePlayActivity::class.java)
            intent.putExtra(Constant.GAME_TYPE_KEY, gameType)
            intent.putExtra(Constant.PLAYER, player)
            context.startActivity(intent)
        }
    }

    override fun initView() {
        supportActionBar?.hide()
        getIntentData()
        initListeners()
    }

    override fun initPresenter() {
        // sesuaikan , untuk saat ini pakai player dlo, ntar pake history punya
        val dataSource = PlayersDataSourceImpl(PlayersDatabase.getInstance(this).playersDao())
        val repository = PlayerMenusRepository(dataSource)
        setPresenter(GamePlayPresenter(this, repository))
    }

    override fun getIntentData() {
        gameType = intent.extras?.getSerializable(Constant.GAME_TYPE_KEY) as GameType
        if(gameType == GameType.PLAYER_TO_PLAYER) {
            enemy = intent.extras?.get(Constant.PLAYER) as Player
        }


        Log.i(GamePlayActivity::class.java.simpleName, gameType.toString())
        Log.i(GamePlayActivity::class.java.simpleName, enemy.toString())
    }

    override fun resetState() {
        TODO("Not yet implemented")
    }

    override fun initListeners() {

        getViewBinding().tvPlayer.text = UserPreference(this).username

        playerElements = arrayOf(
            getViewBinding().ivPlayerRock,
            getViewBinding().ivPlayerPaper,
            getViewBinding().ivPlayerScissor
        )
        enemyElements = arrayOf(
            getViewBinding().ivComRock,
            getViewBinding().ivComPaper,
            getViewBinding().ivComScissor
        )

        playerElements.forEachIndexed { index, frameLayout ->
            frameLayout.setOnClickListener { elem ->
                setFocus(elem, index, playerElements, false)
            }
        }

        if (gameType == GameType.PLAYER_TO_PLAYER) {
            getViewBinding().tvCom.text = enemy?.name ?: ""
            enemyElements.forEachIndexed { index, frameLayout ->
                frameLayout.setOnClickListener { elem ->
                    setFocus(elem, index, enemyElements, true)
                }
            }
        }
    }

    override fun disableClickAfterComparison() {
        playerElements.forEachIndexed { index, iv ->
            iv.isClickable = false
        }

        if(gameType == GameType.PLAYER_TO_PLAYER){
            enemyElements.forEachIndexed { index, iv ->
                iv.isClickable = false
            }
        }

        getViewBinding().llLeft.visibility = View.VISIBLE
        getViewBinding().llRight.visibility = View.VISIBLE
    }

    override fun showResultDialog() {

    }

    private fun setFocus(elem : View, index : Int, candidates : Array<ImageView>, isFromEnemy : Boolean) {

        elem.setBackgroundColor(ContextCompat.getColor(this, R.color.ThirdColor))
        candidates.forEach {
            if (elem != it) {
                it.setBackgroundColor(ContextCompat.getColor(this, R.color.primaryColor))
            }
        }

        if(gameType == GameType.PLAYER_TO_PLAYER) {

            if(isFromEnemy) {
                enemy?.choice = index
                getViewBinding().llRight.visibility = View.INVISIBLE
            }else {

                //replace id with the id got from preference later
                player = UserPreference(this).username?.let {
                    Player(1, it)
                }
                player?.choice = index
                getViewBinding().llLeft.visibility = View.INVISIBLE
            }

            if(player?.choice != null && (enemy?.choice != null && enemy?.choice != -1)) {

                enemy?.choice?.let {
                    //do compare
                    getPresenter().compare(it, player!!)
                }
            }

        }else if(gameType == GameType.PLAYER_TO_COM) {

            //change id to the id from preference later
            player = UserPreference(this).username?.let {
                Player(1, it)
            }

            player?.choice = index

            //do compare
            val computeChoice = (0..2).random()
            enemyElements[computeChoice].setBackgroundColor(ContextCompat.getColor(this, R.color.ThirdColor))
            getPresenter().compare(computeChoice, player!!)
        }
    }
}