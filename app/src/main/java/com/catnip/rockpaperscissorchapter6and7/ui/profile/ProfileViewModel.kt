package com.catnip.rockpaperscissorchapter6and7.ui.profile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catnip.rockpaperscissorchapter6and7.base.model.Resource
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.BaseAuthResponse
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.UserData
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class ProfileViewModel(private val repository : ProfileContract.Repository) : ViewModel(), ProfileContract.ViewModel {

    val transactionResult = MutableLiveData<Resource<BaseAuthResponse<UserData, String>>>()
    val updateTransactionResult = MutableLiveData<Resource<BaseAuthResponse<UserData, String>>>()

    private val gson = Gson()

    override fun getProfileData() {
        transactionResult.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {

            try {
                val userData = repository.getProfileData()
                viewModelScope.launch (Dispatchers.Main){
                    transactionResult.value = Resource.Success(userData)
                }
            } catch (cause: Throwable) {
                when(cause) {
                    is HttpException -> {
                        cause.response()?.errorBody()?.source()?.let {
                            val error = gson.fromJson(it.readString(Charsets.UTF_8), BaseAuthResponse::class.java)
                            viewModelScope.launch (Dispatchers.Main){
                                transactionResult.value = Resource.Error(error.errorMsg.toString(), null)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun updateProfile(username: String, email: String) {
        transactionResult.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val userData = repository.updateProfile(username, email)
                viewModelScope.launch (Dispatchers.Main){
                    updateTransactionResult.value = Resource.Success(userData)
                }
            } catch (e: Exception) {
                viewModelScope.launch (Dispatchers.Main){
                    updateTransactionResult.value = Resource.Error(e.message.orEmpty())
                }
            }
        }
    }


}