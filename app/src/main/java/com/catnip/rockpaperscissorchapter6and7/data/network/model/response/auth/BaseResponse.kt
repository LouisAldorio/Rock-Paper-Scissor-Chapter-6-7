package com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth

import com.google.gson.annotations.SerializedName

data class BaseResponse<D, E>(
    @SerializedName("success")
    val isSuccess: Boolean,
    @SerializedName("data")
    val data: D,
    @SerializedName("errors")
    val errorMsg: E
)