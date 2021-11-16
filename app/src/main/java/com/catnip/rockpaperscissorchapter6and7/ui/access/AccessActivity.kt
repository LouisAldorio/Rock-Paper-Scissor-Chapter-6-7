package com.catnip.rockpaperscissorchapter6and7.ui.access

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.catnip.rockpaperscissorchapter6and7.R
import com.catnip.rockpaperscissorchapter6and7.ui.access.register.RegisterFragment

class AccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_access)
//        supportFragmentManager.beginTransaction().replace(R.id.fooFragment, RegisterFragment()).commitAllowingStateLoss()
    }
}