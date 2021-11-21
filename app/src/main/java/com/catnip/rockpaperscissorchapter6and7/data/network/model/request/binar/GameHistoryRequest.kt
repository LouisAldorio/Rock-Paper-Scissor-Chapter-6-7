package com.catnip.rockpaperscissorchapter6and7.data.network.model.request.binar


import com.google.gson.annotations.SerializedName

data class GameHistoryRequest(
    @SerializedName("mode")
    var mode: String,
    @SerializedName("result")
    var result: String
)