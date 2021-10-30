package com.catnip.rockpaperscissorchapter6and7.ui.game.history

import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.widget.ListPopupWindow
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager
import com.catnip.rockpaperscissorchapter6and7.R
import com.catnip.rockpaperscissorchapter6and7.base.BaseActivity
import com.catnip.rockpaperscissorchapter6and7.base.model.Resource
import com.catnip.rockpaperscissorchapter6and7.data.local.preference.UserPreference
import com.catnip.rockpaperscissorchapter6and7.data.local.room.PlayersDatabase
import com.catnip.rockpaperscissorchapter6and7.data.local.room.datasource.GameHistoryDataSourceImpl
import com.catnip.rockpaperscissorchapter6and7.data.local.room.datasource.PlayersDataSourceImpl
import com.catnip.rockpaperscissorchapter6and7.data.model.GameHistory
import com.catnip.rockpaperscissorchapter6and7.data.model.GameHistoryWithPlayer
import com.catnip.rockpaperscissorchapter6and7.databinding.ActivityGameHistoryBinding
import com.catnip.rockpaperscissorchapter6and7.enumeration.GameResult
import com.catnip.rockpaperscissorchapter6and7.ui.game.history.adapter.GameHistoryAdapter
import com.catnip.rockpaperscissorchapter6and7.utils.GameUtil

class GameHistoryActivity : BaseActivity<ActivityGameHistoryBinding, GameHistoryContract.Presenter>(
    ActivityGameHistoryBinding::inflate
), GameHistoryContract.View {

    private lateinit var adapter: GameHistoryAdapter
    private var gameResultFilter: String? = null
    private var gameResultSortIsAscending: Boolean = true

    private lateinit var listPopupWindowButton: Button
    private lateinit var listPopupWindow: ListPopupWindow
    private lateinit var listPopupWindowItem: List<String>

    private fun setListPopupWindow() {
        listPopupWindowButton = getViewBinding().menuButton1
        listPopupWindow = ListPopupWindow(this, null, R.attr.listPopupWindowStyle)

        // Set button as the list popup's anchor
        listPopupWindow.anchorView = listPopupWindowButton

        listPopupWindowItem = listOf(GameResult.WIN.stringValue, "LOSE", "DRAW")
        listPopupWindow.setAdapter(
            ArrayAdapter(
                this,
                R.layout.list_popup_window_item,
                listPopupWindowItem
            )
        )
    }

    override fun initView() {
        supportActionBar?.hide()

        initList()
        setListPopupWindow()
        setClickListeners()

    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    override fun initPresenter() {
        val dataSource =
            GameHistoryDataSourceImpl(PlayersDatabase.getInstance(this).gameHistoryDao())

        val playerDataSource = PlayersDataSourceImpl(PlayersDatabase.getInstance(this).playersDao())
        val repository = GameHistoryRepository(dataSource, playerDataSource)
        setPresenter(GameHistoryPresenter(this@GameHistoryActivity, repository))
    }

    override fun getData() {
        getPresenter().getGameHistoriesByPlayerId(UserPreference(this).player?.id)
    }

    override fun setGameHistoryData(data: List<GameHistoryWithPlayer>) {
        adapter.setItems(data)
    }

    override fun showError(isErrorEnabled: Boolean, msg: String?) {
        getViewBinding().llNoData.visibility =
            if (isErrorEnabled) View.VISIBLE else View.GONE
    }

    override fun showContent(isContentVisible: Boolean) {
        getViewBinding().rvHistory.visibility = if (isContentVisible) View.VISIBLE else View.GONE
    }

    fun sortGameHistory(
        gameHistoryWithPlayer: List<GameHistoryWithPlayer>,
        isAscending: Boolean
    ): List<GameHistoryWithPlayer> {
        return if (isAscending) {
            gameHistoryWithPlayer.sortedBy { it.gameHistory.id }
        } else {
            gameHistoryWithPlayer.sortedByDescending { it.gameHistory.id }
        }
    }

    private fun filterGameHistory(gameHistoryWithPlayer: List<GameHistoryWithPlayer>): List<GameHistoryWithPlayer> {
        val gameHistoryWithPlayerFiltered = mutableListOf<GameHistoryWithPlayer>()

        when (gameResultFilter) {
            GameResult.DRAW.stringValue -> {
                gameHistoryWithPlayer.forEach {
                    if (it.player1.name == UserPreference(this).player?.name) {
                        if (GameUtil.getGameResult(
                                it.gameHistory.player1Hero,
                                it.gameHistory.player2Hero
                            ) == GameResult.DRAW
                        ) gameHistoryWithPlayerFiltered.add(it)
                    } else {
                        if (GameUtil.getGameResult(
                                it.gameHistory.player2Hero,
                                it.gameHistory.player1Hero
                            ) == GameResult.DRAW
                        ) gameHistoryWithPlayerFiltered.add(it)
                    }
                }
            }
            GameResult.LOSE.stringValue -> {
                gameHistoryWithPlayer.forEach {
                    if (it.player1.name == UserPreference(this).player?.name) {
                        if (GameUtil.getGameResult(
                                it.gameHistory.player1Hero,
                                it.gameHistory.player2Hero
                            ) == GameResult.LOSE
                        ) gameHistoryWithPlayerFiltered.add(it)
                    } else {
                        if (GameUtil.getGameResult(
                                it.gameHistory.player2Hero,
                                it.gameHistory.player1Hero
                            ) == GameResult.LOSE
                        ) gameHistoryWithPlayerFiltered.add(it)
                    }
                }
            }
            GameResult.WIN.stringValue -> {
                gameHistoryWithPlayer.forEach {
                    if (it.player1.name == UserPreference(this).player?.name) {
                        if (GameUtil.getGameResult(
                                it.gameHistory.player1Hero,
                                it.gameHistory.player2Hero
                            ) == GameResult.WIN
                        ) gameHistoryWithPlayerFiltered.add(it)
                    } else {
                        if (GameUtil.getGameResult(
                                it.gameHistory.player2Hero,
                                it.gameHistory.player1Hero
                            ) == GameResult.WIN
                        ) gameHistoryWithPlayerFiltered.add(it)
                    }
                }
            }
            else -> gameHistoryWithPlayerFiltered.addAll(gameHistoryWithPlayer)
        }

        return gameHistoryWithPlayerFiltered
    }

    override fun onDataCallback(response: Resource<List<GameHistoryWithPlayer>>) {

        when (response) {
            is Resource.Loading -> {
                showLoading(true)
                showError(false, null)
                showContent(false)
            }
            is Resource.Success -> {
                showLoading(false)
                response.data?.let {
                    if (it.isEmpty()) {
                        showError(true, "Error")
                        showContent(false)
                    } else {
                        showError(false, null)
                        showContent(true)

                        val filteredGameHistory = filterGameHistory(it)
                        if (filteredGameHistory.isEmpty()) {
                            showError(true, "Error")
                            showContent(false)
                        } else {
                            setGameHistoryData(
                                sortGameHistory(
                                    filteredGameHistory,
                                    gameResultSortIsAscending
                                )
                            )
                        }
                    }
                }
            }
            is Resource.Error -> {
                showLoading(false)
                showError(true, response.message)
                showContent(false)
            }
        }
    }

    override fun setClickListeners() {
        // Set list popup's item click listener
        listPopupWindow.setOnItemClickListener { parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
            // Respond to list popup window item click.
            gameResultFilter = listPopupWindowItem[position]
            getViewBinding().menuButton1.text = listPopupWindowItem[position]
            getData()

            // Dismiss popup.
            listPopupWindow.dismiss()
        }

        // Show list popup window on button click.
        listPopupWindowButton.setOnClickListener { v: View? -> listPopupWindow.show() }

        getViewBinding().btnSortAsc.setOnClickListener {
            gameResultSortIsAscending = true
            getData()
        }

        getViewBinding().btnSortDesc.setOnClickListener {
            gameResultSortIsAscending = false
            getData()
        }
    }

    override fun initList() {
        adapter = GameHistoryAdapter()
        getViewBinding().rvHistory.apply {
            layoutManager = LinearLayoutManager(this@GameHistoryActivity)
            adapter = this@GameHistoryActivity.adapter
        }
    }

}