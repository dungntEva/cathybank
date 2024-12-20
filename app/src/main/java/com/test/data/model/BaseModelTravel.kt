package com.test.data.model

import com.google.gson.annotations.SerializedName

data class BaseModelTravel(
    @SerializedName("total")
    val total: Int,
    @SerializedName("data")
    val data: List<TravelItem>
)
