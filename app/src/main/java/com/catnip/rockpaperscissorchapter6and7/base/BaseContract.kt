package com.catnip.rockpaperscissorchapter6and7.base

interface BaseContract {
    interface BasePresenter {
        fun onDestroy()
    }

    interface BaseView {
        fun showContent(isContentVisible: Boolean)
        fun showLoading(isLoading: Boolean)
        fun showError(isErrorEnabled: Boolean, msg: String?)
    }
}