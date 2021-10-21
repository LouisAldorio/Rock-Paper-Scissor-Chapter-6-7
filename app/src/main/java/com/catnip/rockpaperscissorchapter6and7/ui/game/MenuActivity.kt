package com.catnip.rockpaperscissorchapter6and7.ui.game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.catnip.rockpaperscissorchapter6and7.databinding.ActivityMenuBinding
import com.catnip.rockpaperscissorchapter6and7.ui.dialog.VideoTutorialDialog

class MenuActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cvGameTutorial.setOnClickListener {
            VideoTutorialDialog().show(supportFragmentManager, "Video Tutorial")
        }
    }
}