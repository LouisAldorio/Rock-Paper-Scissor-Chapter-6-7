package com.catnip.rockpaperscissorchapter6and7.ui.game.mode.dialog

import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.catnip.rockpaperscissorchapter6and7.R
import com.catnip.rockpaperscissorchapter6and7.base.BaseDialogFragment
import com.catnip.rockpaperscissorchapter6and7.base.BaseViewModelDialogFragment
import com.catnip.rockpaperscissorchapter6and7.base.GenericViewModelFactory
import com.catnip.rockpaperscissorchapter6and7.base.model.Resource
import com.catnip.rockpaperscissorchapter6and7.data.local.preference.UserPreference
import com.catnip.rockpaperscissorchapter6and7.data.local.room.PlayersDatabase
import com.catnip.rockpaperscissorchapter6and7.data.local.room.datasource.PlayersDataSourceImpl
import com.catnip.rockpaperscissorchapter6and7.data.model.Player
import com.catnip.rockpaperscissorchapter6and7.databinding.FragmentPlayerMenusBinding
import com.catnip.rockpaperscissorchapter6and7.enumeration.GameType
import com.catnip.rockpaperscissorchapter6and7.ui.game.gameplay.GamePlayActivity
import com.catnip.rockpaperscissorchapter6and7.utils.MapEx

class PlayerMenusDialogFragment :
    BaseViewModelDialogFragment<FragmentPlayerMenusBinding>(
        FragmentPlayerMenusBinding::inflate
    ), PlayerMenusContract.View {

    private var players: MutableMap<Long, String> = mutableMapOf()
    private var playerNames: MutableList<String> = mutableListOf()
    private lateinit var viewModel: PlayerMenusViewModel

    override fun onResume() {
        super.onResume()
        getData()
    }

    override fun getData() {
        viewModel.getAllPlayers()
    }

    override fun setMenusData(data: List<Player>) {
        players = mutableMapOf()
        playerNames = mutableListOf()
        data.forEach {
            it.id?.let { id -> players.put(id, it.name) }
            if (UserPreference(requireContext()).player != it) {
                playerNames.add(it.name)
            }
        }

        if (players.isNotEmpty()) {
            if (players.size > 4)
                (getViewBinding().menu.editText as? AutoCompleteTextView)?.dropDownHeight = 400

            (getViewBinding().menu.editText as? AutoCompleteTextView)?.setAdapter(
                ArrayAdapter(
                    requireContext(),
                    R.layout.list_item,
                    playerNames
                )
            )

        }
    }

    override fun setClickListeners() {
        getViewBinding().btnGetStarted.setOnClickListener {
            val playerName = getViewBinding().menu.editText?.text.toString()
            if (playerName.isNotBlank() && !playerNames.contains(playerName)) {
                viewModel.insertPlayer(Player(null, playerName))
                dialog?.dismiss()
                navigateToGamePlayPVP(Player(null, playerName))
            } else if (playerName.isNotBlank() && playerNames.contains(playerName)) {
                MapEx.getKey(players, playerName)?.let { id ->
                    navigateToGamePlayPVP(
                        Player(
                            id,
                            playerName
                        )
                    )
                }
            }
        }
    }

    private fun navigateToGamePlayPVP(player: Player) {
        GamePlayActivity.startActivity(
            requireContext(),
            GameType.PLAYER_TO_PLAYER,
            player
        )
        dismiss()
    }

    override fun initView() {
        setClickListeners()
    }

    override fun initViewModel() {
        context?.let {
            val dataSource = PlayersDataSourceImpl(PlayersDatabase.getInstance(it).playersDao())
            val repository = PlayerMenusRepository(dataSource)
            viewModel = GenericViewModelFactory(PlayerMenusViewModel(repository))
                .create(PlayerMenusViewModel::class.java)
        }
        viewModel.getPlayerResponse.observe(viewLifecycleOwner) { response ->
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
        getData()
    }
}