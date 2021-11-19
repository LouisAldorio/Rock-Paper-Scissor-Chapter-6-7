package com.catnip.rockpaperscissorchapter6and7.ui.game


import android.content.Intent
import com.catnip.rockpaperscissorchapter6and7.base.*
import com.catnip.rockpaperscissorchapter6and7.databinding.ActivityMenuBinding
import com.catnip.rockpaperscissorchapter6and7.enumeration.GameHistoryType
import com.catnip.rockpaperscissorchapter6and7.ui.game.history.GameHistoryActivity

import com.catnip.rockpaperscissorchapter6and7.ui.about.AboutActivity
import com.catnip.rockpaperscissorchapter6and7.ui.about.AboutViewModel

import com.catnip.rockpaperscissorchapter6and7.ui.game.mode.GameModeActivity
import com.catnip.rockpaperscissorchapter6and7.ui.profile.ProfileDialog
import com.catnip.rockpaperscissorchapter6and7.ui.tutorial.VideoTutorialDialog


class MenuActivity : BaseViewModelActivity<ActivityMenuBinding> (
    ActivityMenuBinding::inflate
), MenuContract.View {

    private lateinit var viewModel: MenuViewModel

    override fun initView() {
        supportActionBar?.hide()
        setClickListeners()
    }

    override fun initViewModel() {
        viewModel = GenericViewModelFactory(MenuViewModel()).create(MenuViewModel::class.java)
    }

    override fun setClickListeners() {
        getViewBinding().cvGameMode.setOnClickListener {
            val intent = Intent(this@MenuActivity, GameModeActivity::class.java)
            startActivity(intent)
        }

        getViewBinding().cvGameHistory.setOnClickListener {
            GameHistoryActivity.startActivity(this, GameHistoryType.REMOTE_HISTORY)
        }

        getViewBinding().cvGameTutorial.setOnClickListener {
            VideoTutorialDialog().show(supportFragmentManager, "Video Tutorial")
        }

        getViewBinding().cvGameAbout.setOnClickListener {
            val intent = Intent(this@MenuActivity, AboutActivity::class.java)
            startActivity(intent)
        }

        getViewBinding().cvProfile.setOnClickListener {
            ProfileDialog().show(supportFragmentManager,"Profile")
        }
    }
}