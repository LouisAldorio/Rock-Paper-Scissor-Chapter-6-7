package com.catnip.rockpaperscissorchapter6and7.ui.splashscreen

import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.catnip.rockpaperscissorchapter6and7.base.BaseViewModelActivity
import com.catnip.rockpaperscissorchapter6and7.base.GenericViewModelFactory
import com.catnip.rockpaperscissorchapter6and7.base.model.Resource
import com.catnip.rockpaperscissorchapter6and7.data.local.preference.datasource.LocalDataSourceImpl
import com.catnip.rockpaperscissorchapter6and7.data.local.preference.SessionPreference
import com.catnip.rockpaperscissorchapter6and7.data.network.datasource.binar.BinarApiDataSourceImpl
import com.catnip.rockpaperscissorchapter6and7.data.network.model.services.BinarApiService
import com.catnip.rockpaperscissorchapter6and7.databinding.ActivitySplashScreenBinding
import com.catnip.rockpaperscissorchapter6and7.ui.game.MenuActivity

class SplashScreenActivity : BaseViewModelActivity<ActivitySplashScreenBinding>(
    ActivitySplashScreenBinding::inflate
), SplashScreenContract.View {
    private lateinit var viewModel: SplashScreenViewModel

    override fun initView() {
        supportActionBar?.hide()
    }

    override fun initViewModel() {
        val dataSource = BinarApiDataSourceImpl(BinarApiService.invoke(LocalDataSourceImpl(SessionPreference(this))))
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
        if (isContentVisible) startActivity(Intent(this, MenuActivity::class.java))
    }

    override fun showError(isErrorEnabled: Boolean, msg: String?) {
        if (isErrorEnabled) Toast.makeText(this, "Move to Login Page", Toast.LENGTH_SHORT).show()
    }
}