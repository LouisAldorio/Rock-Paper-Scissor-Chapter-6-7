package com.catnip.rockpaperscissorchapter6and7.ui.game.gameplay.dialog

import android.view.ViewGroup
import com.catnip.rockpaperscissorchapter6and7.R
import com.catnip.rockpaperscissorchapter6and7.base.BaseViewModelDialogFragment
import com.catnip.rockpaperscissorchapter6and7.base.GenericViewModelFactory
import com.catnip.rockpaperscissorchapter6and7.data.model.Player
import com.catnip.rockpaperscissorchapter6and7.databinding.FragmentResultDialogBinding
import com.catnip.rockpaperscissorchapter6and7.enumeration.GameResult

class ResultDialogFragment(
    private val gameResult: GameResult,
    private val resetState : () -> Unit,
    private val finishToMenu : () -> Unit,
    private val player : Player
) :
    BaseViewModelDialogFragment<FragmentResultDialogBinding>(
        FragmentResultDialogBinding::inflate
    ), ResultDialogContract.View {

    private lateinit var viewModel: ResultDialogViewModel

    override fun initView() {
        dialog?.getWindow()?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        determineWinner()
        initListeners()
    }

    private fun initListeners() {
        getViewBinding().btnBackToMenu.setOnClickListener {
            resetState()
            dismiss()
            finishToMenu()
        }

        getViewBinding().btnPlayAgain.setOnClickListener {
            resetState()
            dismiss()
        }
    }

    private fun determineWinner() {
        if(gameResult.equals(GameResult.WIN)) {
            getViewBinding().tvGameResult.setText(getString(R.string.text_you_win, player.name))
        }else if (gameResult.equals(GameResult.LOSE)) {
            getViewBinding().tvGameResult.setText(getString(R.string.text_you_lose, player.name))
        }else {
            getViewBinding().tvGameResult.setText(getString(R.string.text_draw))
        }
    }

    override fun initViewModel() {
        viewModel = GenericViewModelFactory(ResultDialogViewModel()).create(ResultDialogViewModel::class.java)
    }
}