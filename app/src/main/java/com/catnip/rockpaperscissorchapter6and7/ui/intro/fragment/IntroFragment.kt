package com.catnip.rockpaperscissorchapter6and7.ui.intro.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.catnip.rockpaperscissorchapter6and7.R
import com.catnip.rockpaperscissorchapter6and7.enumeration.IntroType
import com.catnip.rockpaperscissorchapter6and7.data.preference.UserPreference
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
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.ivNext.setOnClickListener { (activity as IntroActivity).nextSlide() }
        binding.btnGetStarted.setOnClickListener { getStarted() }
    }

    private fun setPage() {
        when (type) {
            IntroType.ONE -> {
//                binding.ivBanner.setImageResource(R.drawable.ic_player_to_player)
                isInsertName(false)
                binding.tvInstruction.text = getString(R.string.text_intro_page_first)
                binding.tvTitleImage.text = getString(R.string.text_title_image_intro_page_first)
            }
            IntroType.TWO -> {
//                binding.ivBanner.setImageResource(R.drawable.ic_player_to_com)
                isInsertName(false)
                binding.tvInstruction.text = getString(R.string.text_intro_page_second)
                binding.tvTitleImage.text = getString(R.string.text_title_image_intro_page_second)
            }
            IntroType.THREE -> {
//                binding.ivBanner.setImageResource(R.drawable.ic_person)
                isInsertName(false)
                binding.tvInstruction.text = getString(R.string.text_intro_page_third)
                binding.tvTitleImage.text = getString(R.string.text_title_image_intro_page_third)

//                binding.etUsername.setText(UserPreference(requireContext()).username)
            }
            IntroType.FOUR -> {
                if (!UserPreference(requireContext()).username.isNullOrBlank())
                    binding.tietPlayerName.setText(UserPreference(requireContext()).username)
                isInsertName(true)
            }
        }
    }

    private fun isInsertName(insertName: Boolean) {
        if (insertName) {
            binding.groupIntro.visibility = View.GONE
            binding.groupInsertPlayerName.visibility = View.VISIBLE
        } else {
            binding.groupIntro.visibility = View.VISIBLE
            binding.groupInsertPlayerName.visibility = View.GONE
        }
    }

    private fun getStarted() {
        UserPreference(requireContext()).username = binding.tietPlayerName.text.toString()
        goToMenu()
    }

    private fun goToMenu() {
        val intent = Intent(context, MenuActivity::class.java)
        // do not add current activity to stack after navigate to other activity
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