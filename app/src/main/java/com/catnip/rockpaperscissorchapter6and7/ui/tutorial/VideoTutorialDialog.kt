package com.catnip.rockpaperscissorchapter6and7.ui.tutorial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.catnip.rockpaperscissorchapter6and7.R
import com.catnip.rockpaperscissorchapter6and7.base.BaseDialogFragment
import com.catnip.rockpaperscissorchapter6and7.base.model.Resource
import com.catnip.rockpaperscissorchapter6and7.data.local.room.PlayersDatabase
import com.catnip.rockpaperscissorchapter6and7.data.local.room.datasource.PlayersDataSourceImpl
import com.catnip.rockpaperscissorchapter6and7.databinding.DialogFragmentVideoTutorialBinding
import com.catnip.rockpaperscissorchapter6and7.databinding.FragmentPlayerMenusBinding
import com.catnip.rockpaperscissorchapter6and7.ui.game.mode.dialog.PlayerMenusContract
import com.catnip.rockpaperscissorchapter6and7.ui.game.mode.dialog.PlayerMenusPresenter
import com.catnip.rockpaperscissorchapter6and7.ui.game.mode.dialog.PlayerMenusRepository
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener


class VideoTutorialDialog : BaseDialogFragment<DialogFragmentVideoTutorialBinding, VideoTutorialContract.Presenter>(
    DialogFragmentVideoTutorialBinding::inflate
), VideoTutorialContract.View {

    override fun onDataCallback(response: Resource<String>) {
        when (response) {
            is Resource.Loading -> {
                showLoading(true)
                showError(false, null)
                showContent(false)
            }
            is Resource.Success -> {
                showLoading(false)
                response.data?.let {
                    if (it.isEmpty()) {
                        showError(true, "Error")
                        showContent(false)
                    } else {
                        showError(false, null)
                        showContent(true)
                        setUpYoutubePlayer(it)
                    }
                }
            }
            is Resource.Error -> {
                showLoading(false)
                showError(true, response.message)
                showContent(false)
            }
        }
    }

    override fun setUpYoutubePlayer(videoId : String) {
        val youtubePlayerView = getViewBinding().youtubePlayerView
        lifecycle.addObserver(youtubePlayerView)

        youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)

                youTubePlayer.loadVideo(videoId, 0.0F)
            }
        })
    }

    override fun initView() {}

    override fun initPresenter() {
        context?.let {
            val repository = VideoTutorialRepository()
            setPresenter(VideoTutorialPresenter(this@VideoTutorialDialog, repository))
        }
    }
}