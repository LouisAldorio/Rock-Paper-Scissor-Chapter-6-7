package com.catnip.rockpaperscissorchapter6and7.ui.access.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catnip.rockpaperscissorchapter6and7.base.model.Resource
import com.catnip.rockpaperscissorchapter6and7.data.network.model.request.binar.RegisterRequest
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.binar.BaseResponse
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.binar.RegisterData
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RegisterViewModel(private val registerRepository: RegisterRepository) : ViewModel(),
    RegisterContract.ViewModel {

    private val getResponseLiveData =
        MutableLiveData<Resource<BaseResponse<RegisterData, String>>>()

    override fun getResponseLiveData(): LiveData<Resource<BaseResponse<RegisterData, String>>> = getResponseLiveData

    override fun postRegisterUser(registerRequest: RegisterRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = registerRepository.postRegisterUser(registerRequest)
                viewModelScope.launch(Dispatchers.Main) {
                    getResponseLiveData.value = Resource.Success(response)
                }
            } catch (cause: Throwable) {
                when(cause) {
                    is HttpException -> {
                        cause.response()?.errorBody()?.source()?.let {
                            val error = Gson().fromJson(it.readString(Charsets.UTF_8), BaseResponse::class.java)
                            viewModelScope.launch(Dispatchers.Main) {
                                getResponseLiveData.value = Resource.Error(error.errorMsg.toString(), null)
                            }
                        }
                    }
                }
            }


        }
    }
}