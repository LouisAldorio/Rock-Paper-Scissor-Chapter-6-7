package com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth

import com.google.gson.annotations.SerializedName

data class BaseAuthResponse<D,E>(

    @SerializedName("success")
    val isSuccess : Boolean,

    @SerializedName("data")
    var data : D,

    @SerializedName("errors")
    var errorMsg : E
)
