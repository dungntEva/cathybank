package com.test.data.model

import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("src")
    val src: String,
    @SerializedName("subject")
    val subject: String,
    @SerializedName("ext")
    val ext: String,
)
