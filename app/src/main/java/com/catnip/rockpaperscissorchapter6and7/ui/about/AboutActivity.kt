package com.catnip.rockpaperscissorchapter6and7.ui.about

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.catnip.rockpaperscissorchapter6and7.base.*
import com.catnip.rockpaperscissorchapter6and7.base.model.Resource
import com.catnip.rockpaperscissorchapter6and7.data.local.room.datasource.AboutDataSourceImpl
import com.catnip.rockpaperscissorchapter6and7.data.model.TeamMember
import com.catnip.rockpaperscissorchapter6and7.databinding.ActivityAboutBinding
import com.catnip.rockpaperscissorchapter6and7.ui.about.adapter.TeamMemberRecyclerViewAdapter

class AboutActivity : BaseViewModelActivity<ActivityAboutBinding>(
    ActivityAboutBinding::inflate
), AboutContract.View {

    private lateinit var viewModel: AboutViewModel

    override fun renderTeamMembers(members: List<TeamMember>) {
        val adapter = TeamMemberRecyclerViewAdapter(members)
        getViewBinding().rvTeamMembers.adapter = adapter
        getViewBinding().rvTeamMembers.layoutManager = LinearLayoutManager(this)
    }

    override fun initView() {
        supportActionBar?.hide()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getData()
    }

    override fun initViewModel() {
        val dataSource = AboutDataSourceImpl()
        val repository = AboutRespository(dataSource)
        viewModel = GenericViewModelFactory(AboutViewModel(repository)).create(AboutViewModel::class.java)

        viewModel.transactionResult.observe(this, { response ->
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
                            Log.i("tempok", it.toString())
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
        })
    }
}