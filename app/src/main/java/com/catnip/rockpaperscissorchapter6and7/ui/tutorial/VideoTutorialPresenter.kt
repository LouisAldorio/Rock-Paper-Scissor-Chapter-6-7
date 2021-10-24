package com.catnip.rockpaperscissorchapter6and7.ui.tutorial

import com.catnip.rockpaperscissorchapter6and7.base.BasePresenterImpl
import com.catnip.rockpaperscissorchapter6and7.base.model.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class VideoTutorialPresenter(
    private val view: VideoTutorialContract.View,
    private val repository: VideoTutorialContract.Repository
) : VideoTutorialContract.Presenter, BasePresenterImpl() {

    override fun getVideoId() {
        view.onDataCallback(Resource.Loading())
        scope.launch {
            try {
                val videoId : String = repository.getVideoId()
                scope.launch(Dispatchers.Main) {
                    view.onDataCallback(Resource.Success(videoId))
                }
            } catch (e: Exception) {
                scope.launch(Dispatchers.Main) {
                    view.onDataCallback(Resource.Error(e.message.orEmpty()))
                }
            }
        }
    }
}