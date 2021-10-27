package com.catnip.rockpaperscissorchapter6and7.ui.game.mode

import androidx.core.content.ContentProviderCompat.requireContext
import com.catnip.rockpaperscissorchapter6and7.R
import com.catnip.rockpaperscissorchapter6and7.base.BaseActivity
import com.catnip.rockpaperscissorchapter6and7.data.local.preference.UserPreference
import com.catnip.rockpaperscissorchapter6and7.data.model.Player
import com.catnip.rockpaperscissorchapter6and7.databinding.ActivityGameModeBinding
import com.catnip.rockpaperscissorchapter6and7.enumeration.GameType
import com.catnip.rockpaperscissorchapter6and7.ui.game.gameplay.GamePlayActivity
import com.catnip.rockpaperscissorchapter6and7.ui.game.mode.dialog.PlayerMenusDialogFragment

class GameModeActivity : BaseActivity<ActivityGameModeBinding, GameModeContract.Presenter>(
    ActivityGameModeBinding::inflate
), GameModeContract.View {

    private var allPlayersDialogFragment = PlayerMenusDialogFragment()

    override fun initView() {
        supportActionBar?.hide()
        setPlayerName()
        setClickListeners()
    }

    private fun setPlayerName() {
        getViewBinding().tvPlayerNamePvp.text =
            getString(R.string.text_format_player_name, UserPreference(this).username)
        getViewBinding().tvPlayerNamePvr.text =
            getString(R.string.text_format_player_name, UserPreference(this).username)
    }

    override fun initPresenter() {
        val presenter = GameModePresenter(this)
        setPresenter(presenter)
    }

    override fun setClickListeners() {
        getViewBinding().cvModePvp.setOnClickListener {
            showDialogChoosePlayer()
        }
        getViewBinding().cvModePvr.setOnClickListener {
            GamePlayActivity.startActivity(this,
                GameType.PLAYER_TO_COM,
                Player(null, "")
            )
        }
    }

    override fun showDialogChoosePlayer() {
        allPlayersDialogFragment.show(supportFragmentManager, "Dialog Choose Player 2")
    }
}