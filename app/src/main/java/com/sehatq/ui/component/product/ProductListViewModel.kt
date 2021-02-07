package com.sehatq.ui.component.product

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sehatq.data.DataRepositorySource
import com.sehatq.data.Resource
import com.sehatq.data.dto.home.Home
import com.sehatq.data.dto.product.ProductItem
import com.sehatq.ui.base.BaseViewModel
import com.sehatq.utils.SingleEvent
import com.sehatq.utils.wrapEspressoIdlingResource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


class ProductListViewModel @Inject
constructor(private val dataRepositoryRepository: DataRepositorySource) : BaseViewModel() {

    /**
     * Data --> LiveData, Exposed as LiveData, Locally in viewModel as MutableLiveData
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val homeLiveDataPrivate = MutableLiveData<Resource<Home>>()
    val homeLiveData: LiveData<Resource<Home>> get() = homeLiveDataPrivate


    //TODO check to make them as one Resource
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val productSearchFoundPrivate: MutableLiveData<ProductItem> = MutableLiveData()
    val productSearchFound: LiveData<ProductItem> get() = productSearchFoundPrivate

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val noSearchFoundPrivate: MutableLiveData<Unit> = MutableLiveData()
    val noSearchFound: LiveData<Unit> get() = noSearchFoundPrivate

    /**
     * UI actions as event, user action is single one time event, Shouldn't be multiple time consumption
     */

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val openProductDetailsPrivate = MutableLiveData<SingleEvent<ProductItem>>()
    val openProductDetails: LiveData<SingleEvent<ProductItem>> get() = openProductDetailsPrivate

    /**
     * Error handling as UI
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val showSnackBarPrivate = MutableLiveData<SingleEvent<Any>>()
    val showSnackBar: LiveData<SingleEvent<Any>> get() = showSnackBarPrivate

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val showToastPrivate = MutableLiveData<SingleEvent<Any>>()
    val showToast: LiveData<SingleEvent<Any>> get() = showToastPrivate

    fun getHome() {
        viewModelScope.launch {
            homeLiveDataPrivate.value = Resource.Loading()
            wrapEspressoIdlingResource {
                dataRepositoryRepository.requestHome().collect {
                    homeLiveDataPrivate.value = it
                }
            }
        }
    }

    fun getSearch(keyword: String) {
        viewModelScope.launch {
            homeLiveDataPrivate.value = Resource.Loading()
            wrapEspressoIdlingResource {
                dataRepositoryRepository.requestSearch(keyword).collect {
                    homeLiveDataPrivate.value = it
                }
            }
        }
    }

    fun openProductDetails(product: ProductItem) {
        openProductDetailsPrivate.value = SingleEvent(product)
    }

    fun showToastMessage(errorCode: Int) {
        val error = errorManager.getError(errorCode)
        showToastPrivate.value = SingleEvent(error.description)
    }
}
