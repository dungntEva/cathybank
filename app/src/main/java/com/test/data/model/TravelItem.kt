package com.test.data.model

import com.google.gson.annotations.SerializedName

data class TravelItem(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("introduction")
    val introduction: String,
    @SerializedName("open_time")
    val open_time: String,
    @SerializedName("zipcode")
    val zipcode: String,
    @SerializedName("distric")
    val distric: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("tel")
    val tel: String,
    @SerializedName("images")
    val images: List<Image>,
)
