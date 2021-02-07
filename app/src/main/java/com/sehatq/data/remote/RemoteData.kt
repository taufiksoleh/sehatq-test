package com.sehatq.data.remote

import com.sehatq.data.Resource
import com.sehatq.data.dto.home.Home
import com.sehatq.data.dto.home.HomeRes
import com.sehatq.data.dto.product.ProductItem
import com.sehatq.data.error.NETWORK_ERROR
import com.sehatq.data.error.NO_INTERNET_CONNECTION
import com.sehatq.data.remote.service.ProductService
import com.sehatq.utils.NetworkConnectivity
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject




class RemoteData @Inject
constructor(private val serviceGenerator: ServiceGenerator, private val networkConnectivity: NetworkConnectivity) : RemoteDataSource {

    override suspend fun requestHome(): Resource<Home> {
        val recipesService = serviceGenerator.createService(ProductService::class.java)
        return when (val response = processCall(recipesService::fetchHome)) {
            is List<*> -> {
                Resource.Success(data = Home(response as ArrayList<HomeRes>))
            }
            else -> {
                Resource.DataError(errorCode = response as Int)
            }
        }
    }

    override suspend fun requestSearch(keyword: String): Resource<Home> {
        val recipesService = serviceGenerator.createService(ProductService::class.java)
        return when (val response = processCall(recipesService::fetchHome)) {
            is List<*> -> {
                val res = response as ArrayList<HomeRes>
                val filtered: ArrayList<ProductItem> = arrayListOf()
                res.forEach { it ->
                    it.data.productList.forEach {
                        if (it.description.toLowerCase().contains(keyword.toLowerCase()) ||
                            it.title.toLowerCase().contains(keyword.toLowerCase())){
                            filtered.add(it)
                        }
                    }
                }
                val homeRes = HomeRes()
                homeRes.data.productList = filtered

                val arrayList: ArrayList<HomeRes> = arrayListOf()
                arrayList.add(homeRes)

                Resource.Success(data = Home(homeList = arrayList))
            }
            else -> {
                Resource.DataError(errorCode = response as Int)
            }
        }
    }

    private suspend fun processCall(responseCall: suspend () -> Response<*>): Any? {
        if (!networkConnectivity.isConnected()) {
            return NO_INTERNET_CONNECTION
        }
        return try {
            val response = responseCall.invoke()
            val responseCode = response.code()
            if (response.isSuccessful) {
                response.body()
            } else {
                responseCode
            }
        } catch (e: IOException) {
            NETWORK_ERROR
        }
    }
}
