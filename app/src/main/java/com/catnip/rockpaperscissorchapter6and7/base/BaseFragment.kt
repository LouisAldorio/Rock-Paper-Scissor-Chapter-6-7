package com.catnip.rockpaperscissorchapter6and7.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<B : ViewBinding>(
    val bindingFactory: (LayoutInflater, ViewGroup?, Boolean) -> B
) : Fragment(), BaseContract.BaseView {
    private lateinit var binding: B

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = bindingFactory(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
    }

    fun getViewBinding(): B = binding
    override fun onDestroy() {
        super.onDestroy()
    }
    abstract fun initView()
    abstract fun initViewModel()
    override fun showContent(isContentVisible: Boolean) {}
    override fun showLoading(isLoading: Boolean) {}
    override fun showError(isErrorEnabled: Boolean, msg: String?) {}
}