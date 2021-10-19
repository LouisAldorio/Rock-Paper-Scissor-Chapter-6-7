package com.catnip.rockpaperscissorchapter6and7.ui.intro

import android.os.Bundle
import android.content.Intent
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.catnip.rockpaperscissorchapter6and7.ui.intro.fragment.IntroFragment
import com.catnip.rockpaperscissorchapter6and7.enumeration.IntroType
import com.catnip.rockpaperscissorchapter6and7.ui.game.MenuActivity
import com.github.appintro.*

class IntroActivity : AppIntro2() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTransformer(AppIntroPageTransformerType.Depth)
        isSkipButtonEnabled = false
        isButtonsEnabled = false

        isIndicatorEnabled = false
        supportActionBar?.hide()
        for (i in 0..3) {
            addSlide(IntroFragment.newInstance(IntroType.values()[i]))
        }
    }

    fun nextSlide() {
        goToNextSlide(false)
    }
}