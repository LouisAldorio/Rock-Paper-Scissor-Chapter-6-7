package com.catnip.rockpaperscissorchapter6and7.ui.game.history

import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.MenuRes
import androidx.appcompat.widget.ListPopupWindow
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager
import com.catnip.rockpaperscissorchapter6and7.R
import com.catnip.rockpaperscissorchapter6and7.base.BaseActivity
import com.catnip.rockpaperscissorchapter6and7.base.model.Resource
import com.catnip.rockpaperscissorchapter6and7.data.local.room.PlayersDatabase
import com.catnip.rockpaperscissorchapter6and7.data.local.room.datasource.GameHistoryDataSourceImpl
import com.catnip.rockpaperscissorchapter6and7.data.model.GameHistory
import com.catnip.rockpaperscissorchapter6and7.data.model.PlayerWithGameHistory
import com.catnip.rockpaperscissorchapter6and7.databinding.ActivityGameHistoryBinding
import com.catnip.rockpaperscissorchapter6and7.ui.game.history.adapter.GameHistoryAdapter
import com.catnip.rockpaperscissorchapter6and7.utils.GameUtil

class GameHistoryActivity : BaseActivity<ActivityGameHistoryBinding, GameHistoryContract.Presenter>(
    ActivityGameHistoryBinding::inflate
), GameHistoryContract.View {

    private lateinit var adapter: GameHistoryAdapter
    private var gameResult: String? = null

    private lateinit var listPopupWindowButton: Button
    private lateinit var listPopupWindow: ListPopupWindow
    private lateinit var listPopupWindowItem: List<String>

    fun setListPopupWindow() {
        listPopupWindowButton = getViewBinding().menuButton1
        listPopupWindow = ListPopupWindow(this, null, R.attr.listPopupWindowStyle)

        // Set button as the list popup's anchor
        listPopupWindow.anchorView = listPopupWindowButton

        listPopupWindowItem = listOf("WIN", "LOSE", "DRAW")
        listPopupWindow.setAdapter(ArrayAdapter(this, R.layout.list_popup_window_item, listPopupWindowItem))
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
        val repository = GameHistoryRepository(dataSource)
        setPresenter(GameHistoryPresenter(this@GameHistoryActivity, repository))
    }

    override fun getData() {
        getPresenter().getGameHistory(1)
    }

    override fun setGameHistoryData(data: List<PlayerWithGameHistory>) {
        adapter.setItems(data)
    }

    override fun showError(isErrorEnabled: Boolean, msg: String?) {
        getViewBinding().llNoData.visibility =
            if (isErrorEnabled) View.VISIBLE else View.GONE
    }

    override fun showContent(isContentVisible: Boolean) {
        getViewBinding().rvHistory.visibility = if (isContentVisible) View.VISIBLE else View.GONE
    }
    override fun onDataCallback(response: Resource<List<PlayerWithGameHistory>>) {
        when (response) {
            is Resource.Loading -> {
                showLoading(true)
                showError(false, null)
                showContent(false)
            }
            is Resource.Success -> {
                showLoading(false)
                response.data?.let {
                    showError(false, null)
                    showContent(true)
                    when {
                        gameResult.isNullOrBlank() -> setGameHistoryData(it)
                        gameResult == "DRAW" -> {
                            val draw = mutableListOf<PlayerWithGameHistory>()
                            it.forEach {
                                if (GameUtil.getGameResult(
                                        it.gameHistory.player1Hero,
                                        it.gameHistory.player2Hero
                                    ) == com.catnip.rockpaperscissorchapter6and7.enumeration.GameResult.DRAW
                                ) {
                                    draw.add(it)
                                }
                            }
                            setGameHistoryData(draw)
                            if (draw.isEmpty()) {
                                showError(true, "Error")
                                showContent(false)
                            }
                        }
                        gameResult == "LOSE" -> {
                            val lose = mutableListOf<PlayerWithGameHistory>()
                            it.forEach {
                                if (GameUtil.getGameResult(
                                        it.gameHistory.player1Hero,
                                        it.gameHistory.player2Hero
                                    ) == com.catnip.rockpaperscissorchapter6and7.enumeration.GameResult.LOSE
                                ) {
                                    lose.add(it)
                                }
                            }
                            setGameHistoryData(lose)
                            if (lose.isEmpty()) {
                                showError(true, "Error")
                                showContent(false)
                            }
                        }
                        gameResult == "WIN" -> {
                            val win = mutableListOf<PlayerWithGameHistory>()
                            it.forEach {
                                if (GameUtil.getGameResult(
                                        it.gameHistory.player1Hero,
                                        it.gameHistory.player2Hero
                                    ) == com.catnip.rockpaperscissorchapter6and7.enumeration.GameResult.WIN
                                ) {
                                    win.add(it)
                                }
                            }
                            setGameHistoryData(win)
                            if (win.isEmpty()) {
                                showError(true, "Error")
                                showContent(false)
                            }
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
            gameResult = listPopupWindowItem[position]
            getData()

            // Dismiss popup.
            listPopupWindow.dismiss()
        }

        // Show list popup window on button click.
        listPopupWindowButton.setOnClickListener { v: View? -> listPopupWindow.show() }
    }

    override fun initList() {
        adapter = GameHistoryAdapter()
        getViewBinding().rvHistory.apply {
            layoutManager = LinearLayoutManager(this@GameHistoryActivity)
            adapter = this@GameHistoryActivity.adapter
        }
    }

}