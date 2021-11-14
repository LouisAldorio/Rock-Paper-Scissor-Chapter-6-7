package com.catnip.rockpaperscissorchapter6and7.ui.access.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catnip.rockpaperscissorchapter6and7.base.model.Resource
import com.catnip.rockpaperscissorchapter6and7.data.network.model.request.binar.RegisterRequest
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.binar.BaseResponse
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.binar.RegisterData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    getResponseLiveData.value = Resource.Error(e.message.orEmpty())
                }
            }
        }
    }
}