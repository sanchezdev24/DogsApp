package com.example.network.data.remote

import com.example.network.NetworkConstants
import retrofit2.Response
import retrofit2.http.GET

interface DogApiService {
    @GET(NetworkConstants.DOGS_ENDPOINT)
    suspend fun getDogs(): Response<List<DogDto>>
}