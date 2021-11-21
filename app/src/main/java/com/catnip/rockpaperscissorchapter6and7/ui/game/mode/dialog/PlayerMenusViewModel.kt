package com.catnip.rockpaperscissorchapter6and7.ui.game.mode.dialog

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catnip.rockpaperscissorchapter6and7.base.model.Resource
import com.catnip.rockpaperscissorchapter6and7.data.model.Player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class PlayerMenusViewModel(
    private val repository: PlayerMenusContract.Repository
) : ViewModel(), PlayerMenusContract.ViewModel {

    val getPlayerResponse = MutableLiveData<Resource<List<Player>>>()


    override fun getAllPlayers() {
        getPlayerResponse.value = Resource.Loading()
        viewModelScope.launch {
            try {
                val response = repository.getAllPlayers()
                viewModelScope.launch(Dispatchers.Main) {
                    getPlayerResponse.value = Resource.Success(response)
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    getPlayerResponse.value = Resource.Error(e.message.orEmpty())
                }
            }
        }
    }

    override fun insertPlayer(player: Player) {
        viewModelScope.launch {
            try {
                repository.insertPlayer(Player(player.id,player.name))
            } catch (e: Exception) {
                Log.d("testing",e.message.orEmpty())
            }
        }
    }
}
