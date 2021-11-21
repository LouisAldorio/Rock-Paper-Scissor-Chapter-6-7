package com.catnip.rockpaperscissorchapter6and7.ui.game.history

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.*
import androidx.appcompat.widget.ListPopupWindow
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.catnip.rockpaperscissorchapter6and7.R
import com.catnip.rockpaperscissorchapter6and7.base.BaseViewModelActivity
import com.catnip.rockpaperscissorchapter6and7.base.GenericViewModelFactory
import com.catnip.rockpaperscissorchapter6and7.base.model.Resource
import com.catnip.rockpaperscissorchapter6and7.data.local.preference.SessionPreference
import com.catnip.rockpaperscissorchapter6and7.data.local.preference.UserPreference
import com.catnip.rockpaperscissorchapter6and7.data.local.preference.datasource.LocalDataSourceImpl
import com.catnip.rockpaperscissorchapter6and7.data.local.room.PlayersDatabase
import com.catnip.rockpaperscissorchapter6and7.data.local.room.datasource.GameHistoryDataSourceImpl
import com.catnip.rockpaperscissorchapter6and7.data.local.room.datasource.PlayersDataSourceImpl
import com.catnip.rockpaperscissorchapter6and7.data.model.GameHistoryWithPlayer
import com.catnip.rockpaperscissorchapter6and7.data.network.datasource.auth.AuthApiDataSourceImpl
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.GameHistoryData
import com.catnip.rockpaperscissorchapter6and7.data.network.services.AuthApiService
import com.catnip.rockpaperscissorchapter6and7.databinding.ActivityGameHistoryBinding
import com.catnip.rockpaperscissorchapter6and7.enumeration.GameHistoryType
import com.catnip.rockpaperscissorchapter6and7.enumeration.GameResult
import com.catnip.rockpaperscissorchapter6and7.ui.game.history.adapter.GameHistoryAdapter
import com.catnip.rockpaperscissorchapter6and7.ui.game.history.adapter.RemoteGameHistoryAdapter
import com.catnip.rockpaperscissorchapter6and7.utils.GameUtil

class GameHistoryActivity :
    BaseViewModelActivity<ActivityGameHistoryBinding>(ActivityGameHistoryBinding::inflate),
    GameHistoryContract.View {

    private lateinit var gameHistoryType: GameHistoryType
    private lateinit var adapter: GameHistoryAdapter
    private lateinit var remoteAdapter: RemoteGameHistoryAdapter
    private var gameResultFilter: String? = null
    private var gameResultSortIsAscending: Boolean = true

    private lateinit var listPopupWindowButton: Button
    private lateinit var listPopupWindow: ListPopupWindow
    private lateinit var listPopupWindowItem: List<String>

    private lateinit var viewModel: GameHistoryViewModel

    companion object {
        private const val EXTRAS_GAME_TYPE = "EXTRAS_GAME_TYPE"

        @JvmStatic
        fun startActivity(context: Context?, gameHistoryType: GameHistoryType) {
            val intent = Intent(context, GameHistoryActivity::class.java)
            intent.putExtra(EXTRAS_GAME_TYPE, gameHistoryType)
            context?.startActivity(intent)
        }
    }

    private fun getIntentData() {
        intent.extras?.getSerializable(EXTRAS_GAME_TYPE)?.let {
            gameHistoryType = (it as GameHistoryType)
        }
    }

    private fun setListPopupWindow() {
        listPopupWindowButton = getViewBinding().menuButton1
        listPopupWindow = ListPopupWindow(this, null, R.attr.listPopupWindowStyle)

        // Set button as the list popup's anchor
        listPopupWindow.anchorView = listPopupWindowButton

        listPopupWindowItem = listOf(GameResult.WIN.stringValue, "LOSE", "DRAW", "ALL")
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
        getIntentData()
        initViewModel()
        initList()

        setListPopupWindow()
        setClickListeners()
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    override fun getData() {
        when (gameHistoryType) {
            GameHistoryType.LOCAL_HISTORY -> {
                viewModel.getGameHistoriesByPlayerId(UserPreference(this).player?.id)
            }
            GameHistoryType.REMOTE_HISTORY -> {
                viewModel.getRemoteGameHistory()
            }
        }
    }



    override fun setGameHistoryData(data: List<GameHistoryWithPlayer>) {
        adapter.setItems(data)
    }

    override fun setRemoteGameHistory(data: List<GameHistoryData>) {
        remoteAdapter.setItems(data)
    }

    override fun showContent(isContentVisible: Boolean) {
        getViewBinding().rvHistory.isVisible = isContentVisible
    }

    override fun showLoading(isLoading: Boolean) {
        getViewBinding().pbLoading.isVisible = isLoading
    }

    override fun showError(isErrorEnabled: Boolean, msg: String?) {
        getViewBinding().llNoData.isVisible = isErrorEnabled
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

        when (gameHistoryType) {
            GameHistoryType.LOCAL_HISTORY -> {
                getViewBinding().menuButton2.setOnClickListener {
                    if (gameResultSortIsAscending) getViewBinding().menuButton2.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.ic_baseline_arrow_downward_24,
                        0
                    )
                    else getViewBinding().menuButton2.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.ic_baseline_arrow_upward_24,
                        0
                    )
                    gameResultSortIsAscending = !gameResultSortIsAscending
                    getData()
                }
            }
            GameHistoryType.REMOTE_HISTORY -> {
                getViewBinding().menuButton1.visibility = View.GONE
                getViewBinding().menuButton2.visibility = View.GONE
            }
        }

    }

    override fun initList() {
        when (gameHistoryType) {
            GameHistoryType.LOCAL_HISTORY -> {
                adapter = GameHistoryAdapter()
                getViewBinding().rvHistory.apply {
                    layoutManager = LinearLayoutManager(this@GameHistoryActivity)
                    adapter = this@GameHistoryActivity.adapter
                }
            }
            GameHistoryType.REMOTE_HISTORY -> {
                remoteAdapter = RemoteGameHistoryAdapter()
                getViewBinding().rvHistory.apply {
                    layoutManager = LinearLayoutManager(this@GameHistoryActivity)
                    adapter = this@GameHistoryActivity.remoteAdapter
                }
            }
        }
    }

    override fun observeViewModel() {
        when (gameHistoryType) {
            GameHistoryType.LOCAL_HISTORY -> {
                viewModel.getLocalGameHistoryLiveData().observe(this, { response ->
                    when (response) {
                        is Resource.Loading -> {
                            showLoading(true)
                            showError(false, null)
                            showContent(false)
                        }
                        is Resource.Success -> {
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
                })
            }
            GameHistoryType.REMOTE_HISTORY -> {
                viewModel.getRemoteGameHistoryLiveData().observe(this, { response ->
                    when (response) {
                        is Resource.Loading -> {
                            showLoading(true)
                            showError(false, null)
                            showContent(false)
                        }
                        is Resource.Success -> {
                            showLoading(false)
                            if (response.data!!.data.isEmpty()) {
                                showError(true, "Error")
                                showContent(false)
                            } else {
                                showContent(response.data.isSuccess)
                                showError(false, null)
                                setRemoteGameHistory(response.data.data)
                            }
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

    }

    override fun initViewModel() {
        val dataSource =
            GameHistoryDataSourceImpl(PlayersDatabase.getInstance(this).gameHistoryDao())
        val playerDataSource = PlayersDataSourceImpl(PlayersDatabase.getInstance(this).playersDao())
        val remoteDataSource = AuthApiDataSourceImpl(
            AuthApiService.invoke(
                LocalDataSourceImpl(
                    SessionPreference(this),
                    UserPreference(this)
                )
            )
        )
        val repository = GameHistoryRepository(dataSource, playerDataSource, remoteDataSource)
        viewModel = GenericViewModelFactory(GameHistoryViewModel(repository)).create(
            GameHistoryViewModel::class.java
        )
        observeViewModel()
    }

}