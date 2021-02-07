package com.sehatq.ui.base.listeners

import com.sehatq.data.dto.product.ProductItem



interface RecyclerProductItemListener {
    fun onItemSelected(product : ProductItem)
}
