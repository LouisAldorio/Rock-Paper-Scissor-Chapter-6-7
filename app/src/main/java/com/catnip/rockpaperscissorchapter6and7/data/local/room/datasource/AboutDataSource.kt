package com.catnip.rockpaperscissorchapter6and7.data.local.room.datasource

import com.catnip.rockpaperscissorchapter6and7.data.model.TeamMember

interface AboutDataSource {
    fun getAllPlayers(): List<TeamMember>
}