package com.catnip.rockpaperscissorchapter6and7.ui.splashscreen

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import com.catnip.rockpaperscissorchapter6and7.R
import com.catnip.rockpaperscissorchapter6and7.ui.auth.AuthActivity
import com.catnip.rockpaperscissorchapter6and7.ui.game.MenuActivity
import com.catnip.rockpaperscissorchapter6and7.ui.intro.IntroActivity
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : Activity() {
    private var timer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        setSplash()
    }


    private fun setSplash() {
        timer = object : CountDownTimer(3000, 1000) {
            override fun onTick(p0: Long) {}

            override fun onFinish() {
                val intent = Intent(this@SplashScreenActivity, AuthActivity::class.java)
                startActivity(intent)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                finish()
            }
        }
        timer?.start()
    }
}