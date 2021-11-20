package com.catnip.rockpaperscissorchapter6and7.ui.about

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catnip.rockpaperscissorchapter6and7.base.model.Resource
import com.catnip.rockpaperscissorchapter6and7.data.model.TeamMember
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AboutViewModel(private val repository: AboutContract.Repository) : ViewModel(),AboutContract.ViewModel {

    val transactionResult = MutableLiveData<Resource<List<TeamMember>>>()

    override fun getData() {
        viewModelScope.launch {
            try {
                val teamMembers = repository.getTeamMembers()
                viewModelScope.launch (Dispatchers.Main){
                    transactionResult.value = Resource.Success(teamMembers)
                }
            } catch (e: Exception) {
                viewModelScope.launch (Dispatchers.Main){
                    transactionResult.value = Resource.Error(e.message.orEmpty())
                }
            }
        }
    }
}