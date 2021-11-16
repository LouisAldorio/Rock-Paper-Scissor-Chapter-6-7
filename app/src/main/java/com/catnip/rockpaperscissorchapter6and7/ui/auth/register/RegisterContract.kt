package com.catnip.rockpaperscissorchapter6and7.ui.auth.register

import androidx.lifecycle.LiveData
import com.catnip.rockpaperscissorchapter6and7.base.model.Resource
import com.catnip.rockpaperscissorchapter6and7.data.network.model.request.binar.RegisterRequest
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.BaseResponse
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.RegisterData


interface RegisterContract {
    interface View {
        fun observeViewModel()
        fun initViewModel()
        fun setClickListener()
    }
    interface ViewModel {
        fun getResponseLiveData(): LiveData<Resource<BaseResponse<RegisterData, String>>>
        fun postRegisterUser(registerRequest: RegisterRequest)
    }
    interface Repository {
        suspend fun postRegisterUser(registerRequest: RegisterRequest): BaseResponse<RegisterData, String>
    }
}