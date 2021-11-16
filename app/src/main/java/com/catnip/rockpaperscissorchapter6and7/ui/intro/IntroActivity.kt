package com.catnip.rockpaperscissorchapter6and7.ui.intro

import android.os.Bundle
import com.catnip.rockpaperscissorchapter6and7.ui.intro.fragment.IntroFragment
import com.catnip.rockpaperscissorchapter6and7.enumeration.IntroType
import com.github.appintro.*

class IntroActivity : AppIntro2() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTransformer(AppIntroPageTransformerType.SlideOver)
        isSkipButtonEnabled = false
        isButtonsEnabled = false

        isIndicatorEnabled = false
        supportActionBar?.hide()
        for (i in 0..2) {
            addSlide(IntroFragment.newInstance(IntroType.values()[i]))
        }
    }

    fun nextSlide() {
        goToNextSlide(false)
    }
}