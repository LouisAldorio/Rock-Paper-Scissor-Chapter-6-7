package com.catnip.rockpaperscissorchapter6and7.ui.about

import com.catnip.rockpaperscissorchapter6and7.base.BaseViewModelContract
import com.catnip.rockpaperscissorchapter6and7.data.model.TeamMember

interface AboutViewModelContract {
    interface ViewModel {
        fun getData()

    }

    interface View : BaseViewModelContract.BaseView {
        fun renderTeamMembers(members : List<TeamMember>)
    }

    interface Repository {
        fun getTeamMembers() : List<TeamMember>
    }
}