package com.sehatq.data.dto.product


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = false)
@Parcelize
data class ProductItem(
        @Json(name = "id")
        val id: String = "",
        @Json(name = "imageUrl")
        val imageUrl: String = "",
        @Json(name = "title")
        val title: String = "",
        @Json(name = "description")
        val description: String = "",
        @Json(name = "price")
        val price: String = "",
        @Json(name = "loved")
        val love: Int = 0,
) : Parcelable
