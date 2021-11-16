package com.catnip.rockpaperscissorchapter6and7.ui.about

import com.catnip.rockpaperscissorchapter6and7.data.local.room.datasource.AboutDataSource
import com.catnip.rockpaperscissorchapter6and7.data.model.TeamMember

class AboutRespository(private val dataSource: AboutDataSource) : AboutContract.Repository  {

    override fun getTeamMembers(): List<TeamMember> {
        return dataSource.getAllPlayers()
    }
}