package com.catnip.rockpaperscissorchapter6and7.ui.game


import android.app.AlertDialog
import android.content.Intent
import com.catnip.rockpaperscissorchapter6and7.R
import com.catnip.rockpaperscissorchapter6and7.base.*
import com.catnip.rockpaperscissorchapter6and7.data.local.preference.SessionPreference
import com.catnip.rockpaperscissorchapter6and7.data.local.preference.datasource.LocalDataSource
import com.catnip.rockpaperscissorchapter6and7.data.local.preference.datasource.LocalDataSourceImpl
import com.catnip.rockpaperscissorchapter6and7.databinding.ActivityMenuBinding
import com.catnip.rockpaperscissorchapter6and7.enumeration.GameHistoryType
import com.catnip.rockpaperscissorchapter6and7.ui.game.history.GameHistoryActivity

import com.catnip.rockpaperscissorchapter6and7.ui.about.AboutActivity
import com.catnip.rockpaperscissorchapter6and7.ui.about.AboutViewModel
import com.catnip.rockpaperscissorchapter6and7.ui.auth.AuthActivity
import com.catnip.rockpaperscissorchapter6and7.ui.auth.login.LoginFragment

import com.catnip.rockpaperscissorchapter6and7.ui.game.mode.GameModeActivity
import com.catnip.rockpaperscissorchapter6and7.ui.profile.ProfileDialog
import com.catnip.rockpaperscissorchapter6and7.ui.tutorial.VideoTutorialDialog


class MenuActivity : BaseViewModelActivity<ActivityMenuBinding>(
    ActivityMenuBinding::inflate
), MenuContract.View {

    private lateinit var viewModel: MenuViewModel

    override fun initView() {
        supportActionBar?.hide()
        setClickListeners()
        getViewBinding().tvLogout.text = getString(R.string.text_log_out, "nama")
    }

    override fun initViewModel() {
        viewModel =
            GenericViewModelFactory(
                MenuViewModel(
                    MenuRepository(
                        LocalDataSourceImpl(
                            SessionPreference(this)
                        )
                    )
                )
            ).create(
                MenuViewModel::class.java
            )
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
            ProfileDialog().show(supportFragmentManager, "Profile")
        }

        getViewBinding().cvLogout.setOnClickListener {
            AlertDialog.Builder(this)
                .apply {
                    setTitle("Do you want to Logout?")
                    setPositiveButton("Yes") { dialog, id ->
                        logOut()
                    }
                    setNegativeButton("No") { dialog, id ->
                        dialog.cancel()
                    }
                }.create().show()
        }
    }

    private fun logOut() {
        viewModel.deleteSession()
        navigateToLogin()
    }

    private fun navigateToLogin() {
        val intent = Intent(this, AuthActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}