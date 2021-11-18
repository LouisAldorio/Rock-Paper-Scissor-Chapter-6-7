package com.catnip.rockpaperscissorchapter6and7.ui.tutorial


class VideoTutorialRepository : VideoTutorialContract.Repository {
    override suspend fun getVideoId(): String {
        return "2dsHuU10udY"
    }
}