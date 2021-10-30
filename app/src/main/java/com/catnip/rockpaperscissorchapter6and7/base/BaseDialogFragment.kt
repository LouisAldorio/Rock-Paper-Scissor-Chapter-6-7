package com.catnip.rockpaperscissorchapter6and7.base

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.catnip.rockpaperscissorchapter6and7.R

abstract class BaseDialogFragment <B : ViewBinding, P : BaseContract.BasePresenter>(
    val bindingFactory: (LayoutInflater, ViewGroup?, Boolean) -> B
) : DialogFragment(), BaseContract.BaseView {
    private lateinit var binding: B
    private lateinit var presenter: P

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.rounded_corner_shape);
        binding = bindingFactory(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPresenter()
        initView()
    }

    fun getViewBinding(): B = binding
    fun getPresenter(): P = presenter
    fun setPresenter(presenter: P) {
        this.presenter = presenter
    }
    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
    abstract fun initView()
    abstract fun initPresenter()
    override fun showContent(isContentVisible: Boolean) {}
    override fun showLoading(isLoading: Boolean) {}
    override fun showError(isErrorEnabled: Boolean, msg: String?) {}
}