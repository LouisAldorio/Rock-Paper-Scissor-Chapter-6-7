package com.catnip.rockpaperscissorchapter6and7.ui.access.register

import com.catnip.rockpaperscissorchapter6and7.data.network.model.request.binar.RegisterRequest
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.binar.BaseResponse
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.binar.UserData

class RegisterViewModel(private val registerRepository: RegisterRepository): RegisterContract.ViewModel {
    override fun postRegisterUser(registerRequest: RegisterRequest): BaseResponse<UserData, String> {

    }
}