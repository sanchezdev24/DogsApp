package com.example.network.data.remote

import com.google.gson.annotations.SerializedName

data class DogDto(
    @SerializedName("dogName")
    val dogName: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("age")
    val age: Int,
    @SerializedName("image")
    val url: String
)