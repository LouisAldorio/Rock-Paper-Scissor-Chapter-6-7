package com.catnip.rockpaperscissorchapter6and7.ui.profile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catnip.rockpaperscissorchapter6and7.base.model.Resource
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.BaseAuthResponse
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository : ProfileContract.Repository) : ViewModel(), ProfileContract.ViewModel {

    val transactionResult = MutableLiveData<Resource<BaseAuthResponse<UserData, String>>>()

    override fun getProfileData() {
        transactionResult.value = Resource.Loading()
        viewModelScope.launch {
            try {
                val userData = repository.getProfileData()
                viewModelScope.launch (Dispatchers.Main){
                    transactionResult.value = Resource.Success(userData)
                }
            } catch (e: Exception) {
                viewModelScope.launch (Dispatchers.Main){
                    Log.i("masuk", e.toString())
                    transactionResult.value = Resource.Error(e.message.orEmpty())
                }
            }
        }
    }

    override fun updateProfile(username: String, email: String) {
        transactionResult.value = Resource.Loading()
        viewModelScope.launch {
            try {
                val userData = repository.updateProfile(username, email)
                viewModelScope.launch (Dispatchers.Main){
                    transactionResult.value = Resource.Success(userData)
                }
            } catch (e: Exception) {
                viewModelScope.launch (Dispatchers.Main){
                    transactionResult.value = Resource.Error(e.message.orEmpty())
                }
            }
        }
    }

}