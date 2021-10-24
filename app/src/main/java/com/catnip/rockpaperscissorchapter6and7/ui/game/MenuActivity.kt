package com.catnip.rockpaperscissorchapter6and7.ui.game


import android.content.Intent
import com.catnip.rockpaperscissorchapter6and7.base.BaseActivity
import com.catnip.rockpaperscissorchapter6and7.databinding.ActivityMenuBinding
import com.catnip.rockpaperscissorchapter6and7.ui.game.mode.GameModeActivity
import com.catnip.rockpaperscissorchapter6and7.ui.tutorial.VideoTutorialDialog


class MenuActivity : BaseActivity<ActivityMenuBinding, MenuContract.Presenter>(
    ActivityMenuBinding::inflate
), MenuContract.View {

    override fun initView() {
        supportActionBar?.hide()
        setClickListeners()
    }

    override fun initPresenter() {
        val presenter = MenuPresenter(this)
        setPresenter(presenter)
    }

    override fun setClickListeners() {
        getViewBinding().cvGameTutorial.setOnClickListener {
            VideoTutorialDialog().show(supportFragmentManager, "Video Tutorial")
        }

        getViewBinding().cvGameMode.setOnClickListener {
            val intent = Intent(this@MenuActivity, GameModeActivity::class.java)
            startActivity(intent)
        }
    }
}