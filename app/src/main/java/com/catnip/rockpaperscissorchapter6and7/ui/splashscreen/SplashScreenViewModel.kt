package com.catnip.rockpaperscissorchapter6and7.ui.splashscreen

import android.annotation.SuppressLint
import androidx.lifecycle.*
import com.catnip.rockpaperscissorchapter6and7.base.model.Resource
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.BaseAuthResponse
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.UserData
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

@SuppressLint("CustomSplashScreen")
class SplashScreenViewModel(private val repository: SplashScreenRepository) :
    ViewModel(), SplashScreenContract.ViewModel {

    private val authResponseLiveData =
        MutableLiveData<Resource<BaseAuthResponse<UserData, String>>>()


    override fun getAuthSyncLiveData(): LiveData<Resource<BaseAuthResponse<UserData, String>>> = authResponseLiveData

    override fun deleteSession() {
        repository.deleteSession()
    }

    override fun getSyncUser() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getSyncUser()
                viewModelScope.launch(Dispatchers.Main) {
                    authResponseLiveData.value = Resource.Success(response)
                }
            } catch (cause: Throwable) {
                when(cause) {
                    is HttpException -> {
                        cause.response()?.errorBody()?.source()?.let {
                            val error = Gson().fromJson(it.readString(Charsets.UTF_8), BaseAuthResponse::class.java)
                            viewModelScope.launch(Dispatchers.Main) {
                                authResponseLiveData.value = Resource.Error(error.errorMsg.toString(), null)
                            }
                        }
                    }
                }
            }
        }
    }
}