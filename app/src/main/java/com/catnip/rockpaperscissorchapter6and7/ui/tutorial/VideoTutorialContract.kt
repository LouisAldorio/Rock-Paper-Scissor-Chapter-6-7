package com.catnip.rockpaperscissorchapter6and7.ui.tutorial

import com.catnip.rockpaperscissorchapter6and7.base.BaseContract
import com.catnip.rockpaperscissorchapter6and7.base.model.Resource
import com.catnip.rockpaperscissorchapter6and7.data.model.Player

interface VideoTutorialContract {
    interface View: BaseContract.BaseView {
        fun onDataCallback(response: Resource<String>)
        fun setUpYoutubePlayer(videoId : String)
    }

    interface Presenter: BaseContract.BasePresenter {
        fun getVideoId()
    }

    interface Repository {
        suspend fun getVideoId() : String
    }
}