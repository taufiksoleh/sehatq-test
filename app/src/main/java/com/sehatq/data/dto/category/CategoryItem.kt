package com.sehatq.data.dto.category


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = false)
@Parcelize
data class CategoryItem(
        @Json(name = "id")
        val id: String = "",
        @Json(name = "imageUrl")
        val imageUrl: String = "",
        @Json(name = "name")
        val name: String = "",
) : Parcelable
