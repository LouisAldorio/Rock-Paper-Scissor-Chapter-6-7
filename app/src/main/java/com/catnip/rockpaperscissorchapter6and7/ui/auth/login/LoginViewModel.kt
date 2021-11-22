package com.catnip.rockpaperscissorchapter6and7.ui.auth.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.catnip.rockpaperscissorchapter6and7.base.model.Resource
import com.catnip.rockpaperscissorchapter6and7.data.local.room.PlayersDatabase
import com.catnip.rockpaperscissorchapter6and7.data.model.Player
import com.catnip.rockpaperscissorchapter6and7.data.network.model.request.auth.AuthRequest
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.BaseAuthResponse
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.BaseResponse
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.UserData
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel(private val repository: LoginRepository) : ViewModel(),
    LoginContract.ViewModel {

    val loginResponse = MutableLiveData<Resource<BaseAuthResponse<UserData, String>>>()

    override fun saveUsername(userName: String) {
        var isAddToDao = true
        viewModelScope.launch(Dispatchers.Main) {
            repository.getAllPlayers().forEach {
                if (userName == it.name) {
                    isAddToDao = false
                }
            }
            if (isAddToDao) repository.insertPlayer(Player(null, userName))
            delay(1000)
            val player = repository.getPlayerByUsername(userName)
            repository.saveUserPreference(player)
        }
    }

    override fun saveSession(authToken: String) {
        repository.saveSession(authToken)
    }

    override fun loginUser(loginRequest: AuthRequest) {
        loginResponse.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.postLoginUser(loginRequest)
                viewModelScope.launch(Dispatchers.Main) {
                    loginResponse.value = Resource.Success(response)
                }
            } catch (cause: Throwable) {
                when (cause) {
                    is HttpException -> {
                        cause.response()?.errorBody()?.source()?.let {
                            val error = Gson().fromJson(
                                it.readString(Charsets.UTF_8),
                                BaseResponse::class.java
                            )
                            viewModelScope.launch(Dispatchers.Main) {
                                loginResponse.value =
                                    Resource.Error(error.errorMsg.toString(), null)
                            }
                        }
                    }
                }
            }
        }
    }
}