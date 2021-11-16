package com.catnip.rockpaperscissorchapter6and7.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.catnip.rockpaperscissorchapter6and7.databinding.ActivityAuthBinding
import com.catnip.rockpaperscissorchapter6and7.ui.auth.login.LoginFragment
import com.catnip.rockpaperscissorchapter6and7.ui.auth.register.RegisterFragment
import com.catnip.todolistapp.utils.views.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        initViewPager()
    }

    private fun initViewPager() {
        val fragmentAdapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        fragmentAdapter.addFragment(LoginFragment(), "LOGIN")
        fragmentAdapter.addFragment(RegisterFragment(), "REGISTER")
        binding.viewPager.apply {
            adapter = fragmentAdapter
        }
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = fragmentAdapter.getPageTitle(position)

        }.attach()


    }
}