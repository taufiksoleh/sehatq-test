package com.sehatq.data

import com.sehatq.data.dto.login.LoginRequest
import com.sehatq.data.dto.login.LoginResponse
import com.sehatq.data.dto.home.Home
import com.sehatq.data.dto.product.ProductItem
import com.sehatq.data.dto.purchase.Purchase
import com.sehatq.data.local.LocalData
import com.sehatq.data.remote.RemoteData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext




class DataRepository @Inject constructor(private val remoteRepository: RemoteData, private val localRepository: LocalData, private val ioDispatcher: CoroutineContext) : DataRepositorySource {

    override suspend fun requestPurchase(): Flow<Resource<Purchase>> {
        return flow{
            emit(localRepository.getPurchaseData())
        }.flowOn(ioDispatcher)
    }

    override suspend fun requestHome(): Flow<Resource<Home>> {
        return flow {
            emit(remoteRepository.requestHome())
        }.flowOn(ioDispatcher)
    }

    override suspend fun requestSearch(keyword: String): Flow<Resource<Home>> {
        return flow {
            emit(remoteRepository.requestSearch(keyword))
        }.flowOn(ioDispatcher)
    }

    override suspend fun doLogin(loginRequest: LoginRequest): Flow<Resource<LoginResponse>> {
        return flow {
            emit(localRepository.doLogin(loginRequest))
        }.flowOn(ioDispatcher)
    }

    override suspend fun insertPurchase(product: ProductItem): Flow<Resource<Boolean>> {
        return flow {
            val list: MutableList<ProductItem> = mutableListOf()
            localRepository.getPurchases().let { it ->
                it.data?.let { data ->
                    data.forEach {
                        list.add(it)
                    }
                    list.add(product)
                    emit(localRepository.savePurchase(list))
                }
                it.errorCode?.let { errorCode ->
                    emit(Resource.DataError<Boolean>(errorCode))
                }
            }
        }.flowOn(ioDispatcher)
    }
}
