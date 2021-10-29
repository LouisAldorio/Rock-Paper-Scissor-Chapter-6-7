package com.catnip.rockpaperscissorchapter6and7.ui.game.gameplay.dialog

import android.content.DialogInterface
import com.catnip.rockpaperscissorchapter6and7.R
import com.catnip.rockpaperscissorchapter6and7.base.BaseDialogFragment
import com.catnip.rockpaperscissorchapter6and7.data.model.Player
import com.catnip.rockpaperscissorchapter6and7.databinding.FragmentResultDialogBinding
import com.catnip.rockpaperscissorchapter6and7.enumeration.GameResult

class ResultDialogFragment(
    private val gameResult: GameResult,
    private val resetState : () -> Unit,
    private val finishToMenu : () -> Unit,
    private val player : Player
) :
    BaseDialogFragment<FragmentResultDialogBinding, ResultDialogContract.Presenter>(
        FragmentResultDialogBinding::inflate
    ), ResultDialogContract.View {


    override fun initView() {
        determineWinner()
        initListeners()
    }

    override fun initPresenter() {
        setPresenter(ResultDialogPresenter(this))
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

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        resetState()
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
}