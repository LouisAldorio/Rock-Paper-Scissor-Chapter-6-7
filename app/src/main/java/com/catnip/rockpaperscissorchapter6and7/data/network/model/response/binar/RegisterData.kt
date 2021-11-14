package com.catnip.rockpaperscissorchapter6and7.data.network.model.response.binar


import com.google.gson.annotations.SerializedName

data class RegisterData(
    @SerializedName("email")
    var email: String,
    @SerializedName("_id")
    var id: String,
    @SerializedName("username")
    var username: String
)