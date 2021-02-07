package com.sehatq.data.dto.home


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = false)
@Parcelize
data class HomeRes(
        @Json(name = "data")
        var data: HomeData = HomeData(),
) : Parcelable
