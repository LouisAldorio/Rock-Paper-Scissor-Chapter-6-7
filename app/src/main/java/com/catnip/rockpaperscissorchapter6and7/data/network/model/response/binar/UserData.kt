package com.catnip.rockpaperscissorchapter6and7.data.network.model.response.binar

import com.google.gson.annotations.SerializedName

data class UserData(
    @SerializedName("_id")
    val id: String?,
    @SerializedName("username")
    val username: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("token")
    val token: String?
)
