package com.catnip.rockpaperscissorchapter6and7.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.widget.Toast
import com.catnip.rockpaperscissorchapter6and7.base.BaseViewModelActivity
import com.catnip.rockpaperscissorchapter6and7.base.GenericViewModelFactory
import com.catnip.rockpaperscissorchapter6and7.base.model.Resource
import com.catnip.rockpaperscissorchapter6and7.data.local.preference.datasource.LocalDataSourceImpl
import com.catnip.rockpaperscissorchapter6and7.data.local.preference.SessionPreference
import com.catnip.rockpaperscissorchapter6and7.data.network.datasource.auth.AuthApiDataSourceImpl
import com.catnip.rockpaperscissorchapter6and7.data.network.services.AuthApiService
import com.catnip.rockpaperscissorchapter6and7.databinding.ActivitySplashScreenBinding
import com.catnip.rockpaperscissorchapter6and7.ui.access.AccessActivity
import com.catnip.rockpaperscissorchapter6and7.ui.game.MenuActivity
import com.catnip.rockpaperscissorchapter6and7.ui.intro.IntroActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : BaseViewModelActivity<ActivitySplashScreenBinding>(
    ActivitySplashScreenBinding::inflate
), SplashScreenContract.View {
    private lateinit var viewModel: SplashScreenViewModel

    override fun initView() {
        supportActionBar?.hide()
    }

    override fun initViewModel() {
        val dataSource = AuthApiDataSourceImpl(AuthApiService.invoke(LocalDataSourceImpl(SessionPreference(this))))
        val repository = SplashScreenRepository(dataSource)
        viewModel = GenericViewModelFactory(SplashScreenViewModel(repository)).create(SplashScreenViewModel::class.java)
        observeViewModel()
    }

    override fun observeViewModel() {
        viewModel.getAuthSyncLiveData().observe(this, { response ->
            when (response) {
                is Resource.Success -> {
                    showContent(response.data!!.isSuccess)
                    showError(false, null)
                }
                is Resource.Error -> {
                    showError(true, response.message)
                    showContent(false)
                }
            }
        })
        viewModel.getSyncUser()
    }

    override fun showContent(isContentVisible: Boolean) {
        if (isContentVisible) startActivity(Intent(this, IntroActivity::class.java))
    }

    override fun showError(isErrorEnabled: Boolean, msg: String?) {
        if (isErrorEnabled) {
            Toast.makeText(this, "Move to Login Page", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, AccessActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
    }
}