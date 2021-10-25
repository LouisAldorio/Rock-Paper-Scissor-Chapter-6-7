package com.catnip.rockpaperscissorchapter6and7.data.local.room.datasource

import com.catnip.rockpaperscissorchapter6and7.data.model.TeamMember


class AboutDataSourceImpl : AboutDataSource {
    override fun getAllPlayers(): List<TeamMember> {
        return arrayListOf(
            TeamMember("Louis Aldorio","Product Manager", "https://avatars.githubusercontent.com/u/64881244?v=4"),
            TeamMember("Achmad Yafizh", "Developer", "https://avatars.githubusercontent.com/u/80019698?s=400&u=5eb7bf8abad516c6672ebc694842f2bbe395695f&v=4"),
            TeamMember("Zidan Nur Karim","Scrum Master" ,"https://avatars.githubusercontent.com/u/46629310?v=4"),
            TeamMember("Muhammad Gibran","Product Designer", "https://avatars.githubusercontent.com/gibranran")
        )
    }
}