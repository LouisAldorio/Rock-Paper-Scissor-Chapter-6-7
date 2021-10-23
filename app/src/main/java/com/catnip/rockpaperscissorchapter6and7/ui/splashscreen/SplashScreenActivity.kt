package com.catnip.rockpaperscissorchapter6and7.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.catnip.rockpaperscissorchapter6and7.R
import com.catnip.rockpaperscissorchapter6and7.ui.game.mode.GameModeActivity
import com.catnip.rockpaperscissorchapter6and7.ui.intro.IntroActivity

class SplashScreenActivity : AppCompatActivity() {
    private var timer : CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()
        setSplash()
    }

    private fun setSplash() {
        timer = object : CountDownTimer(3000,1000){
            override fun onTick(p0: Long) {            }

            override fun onFinish() {
                val intent = Intent(this@SplashScreenActivity, GameModeActivity::class.java)
                startActivity(intent)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                finish()
            }
        }
        timer?.start()
    }
}