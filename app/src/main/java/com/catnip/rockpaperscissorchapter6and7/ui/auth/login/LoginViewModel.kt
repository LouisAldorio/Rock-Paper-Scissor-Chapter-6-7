package com.catnip.rockpaperscissorchapter6and7.ui.auth.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catnip.rockpaperscissorchapter6and7.base.model.Resource
import com.catnip.rockpaperscissorchapter6and7.data.network.model.request.auth.AuthRequest
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.BaseAuthResponse
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class LoginViewModel(private val repository: LoginRepository) : ViewModel(),
    LoginContract.ViewModel {

    val loginResponse = MutableLiveData<Resource<BaseAuthResponse<UserData, String>>>()

    override fun loginUser(loginRequest: AuthRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.postLoginUser(loginRequest)
                viewModelScope.launch(Dispatchers.Main) {
                    loginResponse.value = Resource.Success(response)
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    loginResponse.value = Resource.Error(e.message.orEmpty())
                }
            }
        }
    }
}