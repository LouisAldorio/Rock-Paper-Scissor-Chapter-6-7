package com.catnip.rockpaperscissorchapter6and7.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseViewModelActivity<B : ViewBinding>(
    val bindingFactory: (LayoutInflater) -> B
) : AppCompatActivity(), BaseContract.BaseView {

    private lateinit var binding: B

    fun getViewBinding() : B = binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindingFactory(layoutInflater)
        setContentView(binding.root)
        initView()
        initViewModel()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    abstract fun initView()
    abstract fun initViewModel()
    override fun showContent(isContentVisible: Boolean) {}
    override fun showLoading(isLoading: Boolean) {}
    override fun showError(isErrorEnabled: Boolean, msg: String?) {}
}