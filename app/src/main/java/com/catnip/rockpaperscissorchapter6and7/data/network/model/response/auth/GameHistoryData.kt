package com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth


import com.google.gson.annotations.SerializedName

data class GameHistoryData(
    @SerializedName("createdAt")
    var createdAt: String,
    @SerializedName("_id")
    var id: String,
    @SerializedName("message")
    var message: String,
    @SerializedName("mode")
    var mode: String,
    @SerializedName("result")
    var result: String,
    @SerializedName("updatedAt")
    var updatedAt: String
)