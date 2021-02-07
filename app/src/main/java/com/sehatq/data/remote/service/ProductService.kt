package com.sehatq.data.remote.service

import com.sehatq.data.dto.home.HomeRes
import retrofit2.Response
import retrofit2.http.GET



interface ProductService {

    @GET("home")
    suspend fun fetchHome(): Response<List<HomeRes>>
}
