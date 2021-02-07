package com.sehatq.ui.base.listeners

import com.sehatq.data.dto.category.CategoryItem



interface RecyclerCategoryItemListener {
    fun onItemSelected(category : CategoryItem)
}
