package com.catnip.rockpaperscissorchapter6and7.ui.dialog

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.DialogFragment
import com.catnip.rockpaperscissorchapter6and7.R
import com.catnip.rockpaperscissorchapter6and7.databinding.DialogFragmentVideoTutorialBinding
import com.catnip.rockpaperscissorchapter6and7.utils.ProtectedMediaChromeClient
import android.widget.FrameLayout
import android.view.animation.AlphaAnimation
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener


class VideoTutorialDialog : DialogFragment() {


    private var _binding: DialogFragmentVideoTutorialBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        dialog?.window?.setBackgroundDrawableResource(R.drawable.rounded_corner_shape);
        _binding = DialogFragmentVideoTutorialBinding.inflate(inflater, container, false)

        setUpYoutubePlayer()
        return binding.root
    }

    private fun setUpYoutubePlayer() {
        val youtubePlayerView = binding.youtubePlayerView
        lifecycle.addObserver(youtubePlayerView)

        youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)

                youTubePlayer.loadVideo("84ur94_rSss", 0.0F)
            }
        })
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}