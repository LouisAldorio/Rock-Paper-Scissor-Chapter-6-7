package com.catnip.rockpaperscissorchapter6and7.data.network.model.request.binar

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("username")
    var username: String? = null,
    @SerializedName("email")
    var email: String? = null,
    @SerializedName("password")
    var password: String? = null
)
