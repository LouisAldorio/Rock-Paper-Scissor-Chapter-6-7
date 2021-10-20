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


class VideoTutorialDialog : DialogFragment() {

    private lateinit var inAnimation: AlphaAnimation
    private lateinit var outAnimation: AlphaAnimation

    private lateinit var progressBarHolder: FrameLayout

    private var _binding: DialogFragmentVideoTutorialBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        dialog?.window?.setBackgroundDrawableResource(R.drawable.rounded_corner_shape);
        _binding = DialogFragmentVideoTutorialBinding.inflate(inflater, container, false)

        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val webView = binding.wvVideoTutorial
        progressBarHolder = binding.progressBarHolder

        inAnimation = AlphaAnimation(0f, 1f)
        inAnimation.duration = 200
        progressBarHolder.setAnimation(inAnimation);
        progressBarHolder.setVisibility(View.VISIBLE);

        webView.settings.javaScriptEnabled = true
        webView.settings.allowContentAccess = true
        webView.settings.domStorageEnabled = true
        webView.webChromeClient = ProtectedMediaChromeClient()
        webView.webViewClient = object : WebViewClient() {

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

                outAnimation = AlphaAnimation(1f, 0f)
                outAnimation.duration = 200
                progressBarHolder.animation = outAnimation
                progressBarHolder.visibility = View.GONE
            }
        }
        webView.loadUrl("https://m.youtube.com/embed/" + "84ur94_rSss" + "?autoplay=1&vq=small")
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}