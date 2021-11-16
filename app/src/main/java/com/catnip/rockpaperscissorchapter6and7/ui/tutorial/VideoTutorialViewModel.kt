package com.catnip.rockpaperscissorchapter6and7.ui.tutorial

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catnip.rockpaperscissorchapter6and7.base.model.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VideoTutorialViewModel(private val repository : VideoTutorialContract.Repository) : ViewModel(), VideoTutorialContract.ViewModel {

    val transactionResult = MutableLiveData<Resource<String>>()

    override fun getVideoId() {
        viewModelScope.launch {
            try {
                val videoId = repository.getVideoId()
                viewModelScope.launch (Dispatchers.Main){
                    transactionResult.value = Resource.Success(videoId)
                }
            } catch (e: Exception) {
                viewModelScope.launch (Dispatchers.Main){
                    transactionResult.value = Resource.Error(e.message.orEmpty())
                }
            }
        }
    }
}