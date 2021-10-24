package com.catnip.rockpaperscissorchapter6and7.ui.about

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.catnip.rockpaperscissorchapter6and7.base.BaseActivity
import com.catnip.rockpaperscissorchapter6and7.base.BaseDialogFragment
import com.catnip.rockpaperscissorchapter6and7.base.BaseFragment
import com.catnip.rockpaperscissorchapter6and7.base.model.Resource
import com.catnip.rockpaperscissorchapter6and7.data.local.room.PlayersDatabase
import com.catnip.rockpaperscissorchapter6and7.data.local.room.datasource.AboutDataSourceImpl
import com.catnip.rockpaperscissorchapter6and7.data.local.room.datasource.PlayersDataSourceImpl
import com.catnip.rockpaperscissorchapter6and7.data.model.TeamMember
import com.catnip.rockpaperscissorchapter6and7.databinding.ActivityAboutBinding
import com.catnip.rockpaperscissorchapter6and7.databinding.DialogFragmentVideoTutorialBinding
import com.catnip.rockpaperscissorchapter6and7.ui.about.adapter.TeamMemberRecyclerViewAdapter
import com.catnip.rockpaperscissorchapter6and7.ui.game.mode.dialog.PlayerMenusPresenter
import com.catnip.rockpaperscissorchapter6and7.ui.game.mode.dialog.PlayerMenusRepository
import com.catnip.rockpaperscissorchapter6and7.ui.tutorial.VideoTutorialContract

class AboutActivity : BaseActivity<ActivityAboutBinding, AboutContract.Presenter>(
    ActivityAboutBinding::inflate
), AboutContract.View {

    override fun initView() {
        supportActionBar?.hide()
    }

    override fun onResume() {
        super.onResume()
        getPresenter().getTeamMembers()
    }

    override fun initPresenter() {
        val dataSource = AboutDataSourceImpl()
        val repository = AboutRespository(dataSource)
        setPresenter(AboutPresenter(this@AboutActivity, repository))
    }

    override fun renderTeamMembers(members : List<TeamMember>) {
        val adapter = TeamMemberRecyclerViewAdapter(this, members)
        getViewBinding().rvTeamMembers.adapter = adapter
        getViewBinding().rvTeamMembers.layoutManager = LinearLayoutManager(this)
    }

    override fun onDataCallback(response: Resource<List<TeamMember>>) {
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
                        renderTeamMembers(it)
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

}