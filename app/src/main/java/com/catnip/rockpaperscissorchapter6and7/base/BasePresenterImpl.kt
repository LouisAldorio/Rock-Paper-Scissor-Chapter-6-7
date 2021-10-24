package com.catnip.rockpaperscissorchapter6and7.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

open class BasePresenterImpl: BaseContract.BasePresenter {
    private val coroutineJob = Job()
    val scope = CoroutineScope(Dispatchers.IO + coroutineJob)
    override fun onDestroy() {
        coroutineJob.cancel()
    }
}