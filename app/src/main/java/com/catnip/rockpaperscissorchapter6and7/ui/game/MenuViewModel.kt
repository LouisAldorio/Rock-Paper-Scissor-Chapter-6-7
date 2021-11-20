package com.catnip.rockpaperscissorchapter6and7.ui.game

import androidx.lifecycle.ViewModel

class MenuViewModel(private val repository: MenuRepository) : ViewModel(),
    MenuContract.ViewModel {

    override fun deleteSession() {
        repository.deleteSession()
    }


}