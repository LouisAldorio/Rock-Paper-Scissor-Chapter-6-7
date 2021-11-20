package com.catnip.rockpaperscissorchapter6and7.ui.game.historyoptions

import com.catnip.rockpaperscissorchapter6and7.base.BaseViewModelActivity
import com.catnip.rockpaperscissorchapter6and7.databinding.ActivityGameHistoryOptionsBinding
import com.catnip.rockpaperscissorchapter6and7.enumeration.GameHistoryType
import com.catnip.rockpaperscissorchapter6and7.ui.game.history.GameHistoryActivity

class GameHistoryOptionsActivity : BaseViewModelActivity<ActivityGameHistoryOptionsBinding>(
    ActivityGameHistoryOptionsBinding::inflate
), GameHistoryOptionsContract.View {

    override fun initView() {
        supportActionBar?.hide()
        setClickListeners()
    }

    override fun setClickListeners() {
        getViewBinding().cvLocalHistory.setOnClickListener {
            GameHistoryActivity.startActivity(this,
                GameHistoryType.LOCAL_HISTORY
            )
        }
        getViewBinding().cvRemoteHistory.setOnClickListener {
            GameHistoryActivity.startActivity(this,
                GameHistoryType.REMOTE_HISTORY
            )
        }
    }
    override fun initViewModel() {
        GameHistoryOptionsViewModel()
    }
}