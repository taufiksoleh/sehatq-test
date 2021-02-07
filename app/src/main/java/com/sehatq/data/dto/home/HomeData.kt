package com.sehatq.data.dto.home


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.sehatq.data.dto.category.CategoryItem
import com.sehatq.data.dto.product.ProductItem
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = false)
@Parcelize
data class HomeData(
    @Json(name = "category")
        val category: List<CategoryItem> = listOf(),
    @Json(name = "productPromo")
        var productList: List<ProductItem> = listOf(),
) : Parcelable
