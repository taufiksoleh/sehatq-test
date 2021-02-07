package com.sehatq.data

import com.sehatq.data.dto.login.LoginRequest
import com.sehatq.data.dto.login.LoginResponse
import com.sehatq.data.dto.home.Home
import com.sehatq.data.dto.product.ProductItem
import com.sehatq.data.dto.purchase.Purchase
import kotlinx.coroutines.flow.Flow



interface DataRepositorySource {
    suspend fun requestPurchase(): Flow<Resource<Purchase>>
    suspend fun requestHome(): Flow<Resource<Home>>
    suspend fun requestSearch(keyword: String): Flow<Resource<Home>>
    suspend fun doLogin(loginRequest: LoginRequest): Flow<Resource<LoginResponse>>
    suspend fun insertPurchase(product: ProductItem): Flow<Resource<Boolean>>
}
