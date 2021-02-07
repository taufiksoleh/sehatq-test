package com.sehatq.ui.component.details

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sehatq.data.DataRepositorySource
import com.sehatq.data.dto.product.ProductItem
import com.sehatq.ui.base.BaseViewModel
import javax.inject.Inject


open class DetailsViewModel @Inject constructor(private val dataRepository: DataRepositorySource) : BaseViewModel() {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val productPrivate = MutableLiveData<ProductItem>()
    val productData: LiveData<ProductItem> get() = productPrivate

    fun initIntentData(product: ProductItem) {
        productPrivate.value = product
    }
}
