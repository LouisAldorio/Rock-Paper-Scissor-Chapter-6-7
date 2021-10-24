package com.catnip.rockpaperscissorchapter6and7.ui.game.mode.dialog

import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.catnip.rockpaperscissorchapter6and7.R
import com.catnip.rockpaperscissorchapter6and7.base.BaseDialogFragment
import com.catnip.rockpaperscissorchapter6and7.base.model.Resource
import com.catnip.rockpaperscissorchapter6and7.data.local.room.PlayersDatabase
import com.catnip.rockpaperscissorchapter6and7.data.local.room.datasource.PlayersDataSourceImpl
import com.catnip.rockpaperscissorchapter6and7.data.model.Player
import com.catnip.rockpaperscissorchapter6and7.databinding.FragmentPlayerMenusBinding

class PlayerMenusDialogFragment :
    BaseDialogFragment<FragmentPlayerMenusBinding, PlayerMenusContract.Presenter>(
        FragmentPlayerMenusBinding::inflate
    ), PlayerMenusContract.View {

    private var players: MutableList<String> = mutableListOf()

    override fun onResume() {
        super.onResume()
        getData()
    }

    override fun getData() {
        getPresenter().getAllPlayers()
    }

    override fun setMenusData(data: List<Player>) {
        players = mutableListOf()
        data.forEach { players.add(it.name) }
        if (players.isNotEmpty()) {
            if (players.size > 4)
                (getViewBinding().menu.editText as? AutoCompleteTextView)?.dropDownHeight = 800

            (getViewBinding().menu.editText as? AutoCompleteTextView)?.setAdapter(
                ArrayAdapter(
                    requireContext(),
                    R.layout.list_item,
                    players
                )
            )

        }
    }

    override fun onDataCallback(response: Resource<List<Player>>) {
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
                        setMenusData(it)
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
        getViewBinding().btnGetStarted.setOnClickListener {
            val playerName = getViewBinding().menu.editText?.text.toString()
            if (playerName.isNotBlank() && !players.contains(playerName)) {
                getPresenter().insertPlayer(Player(null, playerName))
                dialog?.dismiss()
            }
        }
    }

    override fun showContent(isContentVisible: Boolean) {}
    override fun showLoading(isLoading: Boolean) {}
    override fun showError(isErrorEnabled: Boolean, msg: String?) {}
    override fun initView() {
        setClickListeners()
        dialog?.setCancelable(false)
    }

    override fun initPresenter() {
        context?.let {
            val dataSource = PlayersDataSourceImpl(PlayersDatabase.getInstance(it).playersDao())
            val repository = PlayerMenusRepository(dataSource)
            setPresenter(PlayerMenusPresenter(this@PlayerMenusDialogFragment, repository))
        }
    }
}