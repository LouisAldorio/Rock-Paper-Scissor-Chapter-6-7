package com.catnip.rockpaperscissorchapter6and7.ui.about

import com.catnip.rockpaperscissorchapter6and7.base.BaseContract
import com.catnip.rockpaperscissorchapter6and7.base.model.Resource
import com.catnip.rockpaperscissorchapter6and7.data.model.TeamMember

interface AboutContract {
    interface View : BaseContract.BaseView {
        fun renderTeamMembers(members : List<TeamMember>)
        fun onDataCallback(response: Resource<List<TeamMember>>)
    }

    interface Presenter : BaseContract.BasePresenter {
        fun getTeamMembers()
    }

    interface Repository {
        fun getTeamMembers() : List<TeamMember>
    }
}