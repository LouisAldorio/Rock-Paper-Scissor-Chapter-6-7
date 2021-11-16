package com.catnip.rockpaperscissorchapter6and7.ui.tutorial

import com.catnip.rockpaperscissorchapter6and7.base.BaseViewModelDialogFragment
import com.catnip.rockpaperscissorchapter6and7.base.GenericViewModelFactory
import com.catnip.rockpaperscissorchapter6and7.base.model.Resource
import com.catnip.rockpaperscissorchapter6and7.databinding.DialogFragmentVideoTutorialBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener


class VideoTutorialDialog : BaseViewModelDialogFragment<DialogFragmentVideoTutorialBinding>(
    DialogFragmentVideoTutorialBinding::inflate
), VideoTutorialContract.View {

    private lateinit var viewTutorialViewModel: VideoTutorialViewModel

    override fun initViewModel() {
        val repository = VideoTutorialRepository()
        viewTutorialViewModel = GenericViewModelFactory(VideoTutorialViewModel(repository)).create(VideoTutorialViewModel::class.java)

        viewTutorialViewModel.transactionResult.observe(this, { response ->
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
        })
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
}