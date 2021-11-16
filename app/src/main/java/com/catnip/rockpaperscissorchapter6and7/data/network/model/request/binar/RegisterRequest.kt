package com.catnip.rockpaperscissorchapter6and7.data.network.model.request.binar

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("email")
    var email: String,
    @SerializedName("username")
    var username: String,
    @SerializedName("password")
    var password: String
)
