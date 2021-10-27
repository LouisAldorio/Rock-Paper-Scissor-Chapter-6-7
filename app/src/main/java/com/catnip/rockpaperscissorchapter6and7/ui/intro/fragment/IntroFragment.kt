package com.catnip.rockpaperscissorchapter6and7.ui.intro.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.catnip.rockpaperscissorchapter6and7.R
import com.catnip.rockpaperscissorchapter6and7.base.model.Resource
import com.catnip.rockpaperscissorchapter6and7.enumeration.IntroType
import com.catnip.rockpaperscissorchapter6and7.data.local.preference.UserPreference
import com.catnip.rockpaperscissorchapter6and7.data.local.room.PlayersDatabase
import com.catnip.rockpaperscissorchapter6and7.data.local.room.dao.PlayersDao
import com.catnip.rockpaperscissorchapter6and7.data.model.Player
import com.catnip.rockpaperscissorchapter6and7.databinding.FragmentIntroBinding
import com.catnip.rockpaperscissorchapter6and7.ui.game.MenuActivity
import com.catnip.rockpaperscissorchapter6and7.ui.intro.IntroActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class IntroFragment : Fragment() {

    private var type: IntroType = IntroType.ONE
    private lateinit var binding: FragmentIntroBinding
    private var player: Player? = null

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
                isInsertName(false)
                binding.ivBanner.setImageResource(R.drawable.img_intro_page_1)
                binding.tvTitleImage.text = getString(R.string.text_title_image_intro_page_first)
                binding.tvInstruction.text = getString(R.string.text_intro_page_first)
            }
            IntroType.TWO -> {
                isInsertName(false)
                binding.ivBanner.setImageResource(R.drawable.img_intro_page_2)
                binding.tvTitleImage.text = getString(R.string.text_title_image_intro_page_second)
                binding.tvInstruction.text = getString(R.string.text_intro_page_second)
            }
            IntroType.THREE -> {
                isInsertName(false)
                binding.ivBanner.setImageResource(R.drawable.img_intro_page_3)
                binding.tvTitleImage.text = getString(R.string.text_title_image_intro_page_third)
                binding.tvInstruction.text = getString(R.string.text_intro_page_third)
            }
            IntroType.FOUR -> {
                isInsertName(true)
                binding.ivInsertPlayerName.setImageResource(R.drawable.img_input_name_page)
                UserPreference(requireContext()).player?.let {
                    binding.tietPlayerName.setText(it.name)
                    player = it
                }
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
        if (binding.tietPlayerName.text.toString().isNotBlank()) {
            if (player?.name == binding.tietPlayerName.text.toString()) {
                val coroutineJob = Job()
                val scope = CoroutineScope(Dispatchers.IO + coroutineJob)

                scope.launch {
                    val playerId = PlayersDatabase.getInstance(requireContext()).playersDao().insertPlayer(Player(player?.id, binding.tietPlayerName.text.toString()))
                    UserPreference(requireContext()).player = Player(playerId, binding.tietPlayerName.text.toString())
                }
            } else {
                val coroutineJob = Job()
                val scope = CoroutineScope(Dispatchers.IO + coroutineJob)

                scope.launch {
                    val playerId = PlayersDatabase.getInstance(requireContext()).playersDao().insertPlayer(Player(null, binding.tietPlayerName.text.toString()))
                    UserPreference(requireContext()).player = Player(playerId, binding.tietPlayerName.text.toString())
                }
            }
            goToMenu()
        }
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