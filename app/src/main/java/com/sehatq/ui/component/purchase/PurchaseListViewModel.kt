package com.sehatq.ui.component.purchase

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sehatq.data.DataRepositorySource
import com.sehatq.data.Resource
import com.sehatq.data.dto.product.ProductItem
import com.sehatq.data.dto.purchase.Purchase
import com.sehatq.ui.base.BaseViewModel
import com.sehatq.utils.wrapEspressoIdlingResource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject



class PurchaseListViewModel @Inject
constructor(private val dataRepositoryRepository: DataRepositorySource) : BaseViewModel() {

    /**
     * Data --> LiveData, Exposed as LiveData, Locally in viewModel as MutableLiveData
     */

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val purchaseLiveDataPrivate = MutableLiveData<Resource<Purchase>>()
    val purchaseLiveData: LiveData<Resource<Purchase>> get() = purchaseLiveDataPrivate


    fun getPurchase() {
        viewModelScope.launch {
            purchaseLiveDataPrivate.value = Resource.Loading()
            wrapEspressoIdlingResource {
                dataRepositoryRepository.requestPurchase().collect {
                    purchaseLiveDataPrivate.value = it
                }
            }
        }
    }

    fun insertPurchase(product: ProductItem){
        viewModelScope.launch {
            wrapEspressoIdlingResource {
                dataRepositoryRepository.insertPurchase(product)
            }
        }
    }
}
