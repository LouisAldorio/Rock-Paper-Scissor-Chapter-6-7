package com.catnip.rockpaperscissorchapter6and7.ui.auth.register

import androidx.lifecycle.*
import com.catnip.rockpaperscissorchapter6and7.base.model.Resource
import com.catnip.rockpaperscissorchapter6and7.data.local.room.PlayersDatabase
import com.catnip.rockpaperscissorchapter6and7.data.model.Player
import com.catnip.rockpaperscissorchapter6and7.data.network.model.request.binar.RegisterRequest
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.BaseResponse
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.RegisterData
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RegisterViewModel(private val registerRepository: RegisterRepository) : ViewModel(),
    RegisterContract.ViewModel {

    private val getResponseLiveData =
        MutableLiveData<Resource<BaseResponse<RegisterData, String>>>()

    override fun saveToDao(userName: String, isAdd: Boolean, db: PlayersDatabase) {
        var isAddToDao = isAdd
        viewModelScope.launch {
            db.playersDao().getAllPlayers().forEach {
                if (userName == it.name) {
                    isAddToDao = false
                }
            }
            if (isAddToDao) db.playersDao().insertPlayer(Player(null, userName))
        }
    }

    override fun getResponseLiveData(): LiveData<Resource<BaseResponse<RegisterData, String>>> = getResponseLiveData

    override fun postRegisterUser(registerRequest: RegisterRequest) {
        getResponseLiveData.value = Resource.Loading()
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