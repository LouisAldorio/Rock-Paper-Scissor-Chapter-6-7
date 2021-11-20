package com.catnip.rockpaperscissorchapter6and7.ui.intro.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.catnip.rockpaperscissorchapter6and7.R
import com.catnip.rockpaperscissorchapter6and7.enumeration.IntroType
import com.catnip.rockpaperscissorchapter6and7.databinding.FragmentIntroBinding
import com.catnip.rockpaperscissorchapter6and7.ui.game.MenuActivity
import com.catnip.rockpaperscissorchapter6and7.ui.intro.IntroActivity

class IntroFragment : Fragment() {

    private var type: IntroType = IntroType.ONE
    private lateinit var binding: FragmentIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            type = it.getSerializable(ARG_INTRO_TYPE) as IntroType
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIntroBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setPage()
    }
    private fun setPage() {
        when (type) {
            IntroType.ONE -> {
                binding.ivBanner.setImageResource(R.drawable.img_intro_page_1)
                binding.tvTitleImage.text = getString(R.string.text_title_image_intro_page_first)
                binding.tvInstruction.text = getString(R.string.text_intro_page_first)
                binding.ivNext.setOnClickListener { (activity as IntroActivity).nextSlide() }
            }
            IntroType.TWO -> {
                binding.ivBanner.setImageResource(R.drawable.img_intro_page_2)
                binding.tvTitleImage.text = getString(R.string.text_title_image_intro_page_second)
                binding.tvInstruction.text = getString(R.string.text_intro_page_second)
                binding.ivNext.setOnClickListener { (activity as IntroActivity).nextSlide() }
            }
            IntroType.THREE -> {
                binding.ivBanner.setImageResource(R.drawable.img_intro_page_3)
                binding.tvTitleImage.text = getString(R.string.text_title_image_intro_page_third)
                binding.tvInstruction.text = getString(R.string.text_intro_page_third)
                binding.ivNext.setOnClickListener { goToAccessPage() }

            }
        }
    }

    private fun goToAccessPage() {
        val intent = Intent(context, MenuActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    companion object {

        private const val ARG_INTRO_TYPE = "ARG_INTRO_TYPE"

        @JvmStatic
        fun newInstance(type: IntroType) =
            IntroFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_INTRO_TYPE, type)
                }
            }
    }
}