package com.catnip.rockpaperscissorchapter6and7

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.catnip.rockpaperscissorchapter6and7.ui.game.mode.GameModeActivity
import com.catnip.rockpaperscissorchapter6and7.ui.intro.IntroActivity
import com.catnip.rockpaperscissorchapter6and7.ui.splashscreen.SplashScreenActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}