package com.sehatq.data.remote

import com.sehatq.data.Resource
import com.sehatq.data.dto.home.Home



internal interface RemoteDataSource {
    suspend fun requestHome(): Resource<Home>
    suspend fun requestSearch(keyword: String): Resource<Home>
}
