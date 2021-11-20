package com.catnip.rockpaperscissorchapter6and7.ui.game.gameplay

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.catnip.rockpaperscissorchapter6and7.R
import com.catnip.rockpaperscissorchapter6and7.base.BaseActivity
import com.catnip.rockpaperscissorchapter6and7.base.BaseViewModelActivity
import com.catnip.rockpaperscissorchapter6and7.base.GenericViewModelFactory
import com.catnip.rockpaperscissorchapter6and7.base.model.Resource
import com.catnip.rockpaperscissorchapter6and7.data.local.preference.SessionPreference
import com.catnip.rockpaperscissorchapter6and7.data.local.preference.UserPreference
import com.catnip.rockpaperscissorchapter6and7.data.local.preference.datasource.LocalDataSourceImpl
import com.catnip.rockpaperscissorchapter6and7.data.local.room.PlayersDatabase
import com.catnip.rockpaperscissorchapter6and7.data.local.room.datasource.GameHistoryDataSourceImpl
import com.catnip.rockpaperscissorchapter6and7.data.local.room.datasource.PlayersDataSourceImpl
import com.catnip.rockpaperscissorchapter6and7.data.model.GameHistory
import com.catnip.rockpaperscissorchapter6and7.data.model.Player
import com.catnip.rockpaperscissorchapter6and7.data.network.datasource.auth.AuthApiDataSourceImpl
import com.catnip.rockpaperscissorchapter6and7.data.network.model.request.binar.GameHistoryRequest
import com.catnip.rockpaperscissorchapter6and7.data.network.services.AuthApiService
import com.catnip.rockpaperscissorchapter6and7.databinding.ActivityGamePlayBinding
import com.catnip.rockpaperscissorchapter6and7.enumeration.GameHistoryType
import com.catnip.rockpaperscissorchapter6and7.enumeration.GameResult
import com.catnip.rockpaperscissorchapter6and7.enumeration.GameType
import com.catnip.rockpaperscissorchapter6and7.ui.game.gameplay.dialog.ResultDialogFragment
import com.catnip.rockpaperscissorchapter6and7.ui.game.history.GameHistoryViewModel
import com.catnip.rockpaperscissorchapter6and7.ui.game.mode.dialog.PlayerMenusRepository
import com.catnip.rockpaperscissorchapter6and7.utils.Constant
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

class GamePlayActivity : BaseViewModelActivity<ActivityGamePlayBinding>(
    ActivityGamePlayBinding::inflate
), GamePlayContract.View {

    private var gameType: GameType? = null
    private var player: Player? = null

    private lateinit var playerElements: Array<ImageView>
    private lateinit var enemyElements: Array<ImageView>

    private var enemy: Player? = null

    private lateinit var viewModel: GamePlayViewModel

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
        initViewModel()
        getIntentData()
        initListeners()
    }

    override fun getIntentData() {
        gameType = intent.extras?.getSerializable(Constant.GAME_TYPE_KEY) as GameType
        if (gameType == GameType.PLAYER_TO_PLAYER) {
            enemy = intent.extras?.get(Constant.PLAYER) as Player
        }
    }

    override fun resetState() {

        player?.choice = -1
        enemy?.choice = -1

        playerElements.forEach {
            it.setBackgroundColor(ContextCompat.getColor(this, R.color.primaryColor))
            it.isClickable = true
        }
        enemyElements.forEach {
            it.setBackgroundColor(ContextCompat.getColor(this, R.color.primaryColor))
            if (gameType == GameType.PLAYER_TO_PLAYER) {
                it.isClickable = true
            }
        }
    }

    override fun initListeners() {

        getViewBinding().ivExit.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
            dialog.setTitle("Exit Game")
            dialog.setMessage("Do you really want to exit ?")

            dialog.setPositiveButton(android.R.string.yes) { dialog, which ->
                finishAffinity()
            }

            dialog.setNegativeButton(android.R.string.no) { dialog, which ->
                dialog.dismiss()
            }

            dialog.show()
        }

        getViewBinding().tvPlayer.text = UserPreference(this).player?.name

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
                Toast.makeText(
                    this,
                    "${UserPreference(this).player?.name} telah memilih",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        if (gameType == GameType.PLAYER_TO_PLAYER) {
            getViewBinding().tvCom.text = enemy?.name ?: ""
            enemyElements.forEachIndexed { index, frameLayout ->
                frameLayout.setOnClickListener { elem ->
                    Toast.makeText(this, "Player 2 telah memilih", Toast.LENGTH_SHORT).show()
                    setFocus(elem, index, enemyElements, true)
                }
            }
        }
    }

    override fun disableClickAfterComparison() {
        playerElements.forEachIndexed { index, iv ->
            iv.isClickable = false
        }

        if (gameType == GameType.PLAYER_TO_PLAYER) {
            enemyElements.forEachIndexed { index, iv ->
                iv.isClickable = false
            }
        }

        getViewBinding().llLeft.visibility = View.VISIBLE
        getViewBinding().llRight.visibility = View.VISIBLE
    }

    override fun showResultDialog(gameResult: GameResult) {
        player?.let {
            ResultDialogFragment(
                gameResult,
                { resetState() },
                { finishToMenu() },
                it
            ).show(supportFragmentManager, null)
        }
    }

    private fun finishToMenu() {
        finish()
    }

    private fun setFocus(
        elem: View,
        index: Int,
        candidates: Array<ImageView>,
        isFromEnemy: Boolean
    ) {

        elem.setBackgroundColor(ContextCompat.getColor(this, R.color.ThirdColor))
        candidates.forEach {
            if (elem != it) {
                it.setBackgroundColor(ContextCompat.getColor(this, R.color.primaryColor))
            }
        }

        if (gameType == GameType.PLAYER_TO_PLAYER) {

            if (isFromEnemy) {
                enemy?.choice = index
                getViewBinding().llRight.visibility = View.INVISIBLE
            } else {

                //replace id with the id got from preference later
                player = UserPreference(this).player?.let {
                    Player(it.id, it.name)
                }
                player?.choice = index
                getViewBinding().llLeft.visibility = View.INVISIBLE
            }

            if (player?.choice != -1 && (enemy?.choice != null && enemy?.choice != -1)) {

                enemy?.choice?.let {
                    //do compare

                    val winner = compare(it, player!!)
                    viewModel.insertGameHistory(
                        GameHistory(
                            null,
                            player?.id,
                            player?.choice,
                            enemy?.id,
                            enemy?.choice,
                            SimpleDateFormat("EEEE, dd MMMM yyyy").format(Date())
                        )
                    )
                    viewModel.postRemoteGameHistory(
                        GameHistoryRequest(
                            mode = GameType.PLAYER_TO_PLAYER.value,
                            result = winner
                        )
                    )
                }
            }

        } else if (gameType == GameType.PLAYER_TO_COM) {

            //change id to the id from preference later
            player = UserPreference(this).player?.let {
                Player(it.id, it.name)
            }

            player?.choice = index

            //do compare
            val computeChoice = (0..2).random()
            enemyElements[computeChoice].setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.ThirdColor
                )
            )

            val winner = compare(computeChoice, player!!)
            viewModel.insertGameHistory(
                GameHistory(
                    null,
                    player?.id,
                    player?.choice,
                    null,
                    computeChoice,
                    SimpleDateFormat("EEEE, dd MMMM yyyy").format(Date())
                )
            )
            viewModel.postRemoteGameHistory(
                GameHistoryRequest(
                    mode = GameType.PLAYER_TO_COM.value,
                    result = winner
                )
            )
        }
    }

    private fun compare(enemyChoice: Int, player: Player): String {
        if ((player.choice + 1) % 3 == enemyChoice) {

            // enemy win
            showResultDialog(GameResult.LOSE)
            return "Opponent Win"

        } else if (player.choice == enemyChoice) {

            //draw
            showResultDialog(GameResult.DRAW)
            return "Draw"
        } else {

            //player win
            showResultDialog(GameResult.WIN)
            return "Player Win"
        }

        disableClickAfterComparison()
    }

    override fun initViewModel() {
        val dataSource =
            GameHistoryDataSourceImpl(PlayersDatabase.getInstance(this).gameHistoryDao())
        val remoteDataSource = AuthApiDataSourceImpl(
            AuthApiService.invoke(
                LocalDataSourceImpl(
                    SessionPreference(this),
                    UserPreference(this)
                )
            )
        )
        val repository = GamePlayRepository(dataSource, remoteDataSource)
        viewModel = GenericViewModelFactory(GamePlayViewModel(repository)).create(
            GamePlayViewModel::class.java
        )
        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.postRemoteGameHistoryLiveData().observe(this, { response ->
            when (response) {
                is Resource.Loading -> {
                    showLoading(true)
                    showError(false, null)
                    showContent(false)
                }
                is Resource.Success -> {
                }
                is Resource.Error -> {
                    showLoading(false)
                    showError(true, response.message)
                    showContent(false)
                }
            }
        })
        viewModel.insertGameHistoryLiveData().observe(this, { response ->
            when (response) {
                is Resource.Loading -> {
                    showLoading(true)
                    showError(false, null)
                    showContent(false)
                }
                is Resource.Success -> {
                }
                is Resource.Error -> {
                    showLoading(false)
                    showError(true, response.message)
                    showContent(false)
                }
            }
        })
    }
}