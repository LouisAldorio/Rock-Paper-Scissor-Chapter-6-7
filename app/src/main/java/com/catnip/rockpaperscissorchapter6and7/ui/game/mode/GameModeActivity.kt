package com.catnip.rockpaperscissorchapter6and7.ui.game.mode

import com.catnip.rockpaperscissorchapter6and7.base.BaseActivity
import com.catnip.rockpaperscissorchapter6and7.databinding.ActivityGameModeBinding
import com.catnip.rockpaperscissorchapter6and7.ui.game.mode.dialog.PlayerMenusDialogFragment

class GameModeActivity : BaseActivity<ActivityGameModeBinding, GameModeContract.Presenter>(
    ActivityGameModeBinding::inflate
), GameModeContract.View {

    private var allPlayersDialogFragment = PlayerMenusDialogFragment()

    override fun initView() {
        supportActionBar?.hide()
        setClickListeners()
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

        }
    }

    override fun showDialogChoosePlayer() {
        allPlayersDialogFragment.show(supportFragmentManager, "Dialog Choose Player 2")
    }
}