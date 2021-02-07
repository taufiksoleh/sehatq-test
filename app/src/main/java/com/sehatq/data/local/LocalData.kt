package com.sehatq.data.local

import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.sehatq.App
import com.sehatq.PURCHASES_KEY
import com.sehatq.SHARED_PREFERENCES_FILE_NAME
import com.sehatq.data.Resource
import com.sehatq.data.dto.login.LoginRequest
import com.sehatq.data.dto.login.LoginResponse
import com.sehatq.data.dto.product.ProductItem
import com.sehatq.data.dto.purchase.Purchase
import com.sehatq.data.error.DEFAULT_ERROR
import com.sehatq.data.error.PASS_WORD_ERROR
import javax.inject.Inject


class LocalData @Inject constructor() {

    fun doLogin(loginRequest: LoginRequest): Resource<LoginResponse> {
        if (loginRequest == LoginRequest("test@gmail.com", "123456")) {
            return Resource.Success(LoginResponse("1", "Taufik", "Soleh",
                    "Pinang raya", "1", "15017", "Banten",
                    "Indonesia", "test@gmail.com"))
        }
        return Resource.DataError(PASS_WORD_ERROR)
    }

    fun getPurchaseData(): Resource.Success<Purchase> {
        val product: MutableList<ProductItem> = mutableListOf()
        product.add(
            ProductItem(
                "1",
                "https://static.zara.net/photos///2021/V/0/2/p/5320/316/251/72/w/850/5320316251_2_1_1.jpg?ts=1611926130290",
                "Corduroy Puffer Gilet",
                "",
                "\$910",
            ))
        product.add(
            ProductItem(
                "6725",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d5/Nintendo-ds-lite.svg/430px-Nintendo-ds-lite.svg.png",
                "Nitendo DS Lite",
                "",
                "\$110",
            ))
        getPurchases().data?.forEach {
            product.add(it)
        }
        return Resource.Success(data = Purchase(product as ArrayList<ProductItem>))
    }

    fun savePurchase(value: MutableList<ProductItem>): Resource<Boolean> {
        Log.e("savePurchase", "")

        //save list purchase data
        val purchase = Gson().toJson(value)
        val sharedPref = App.context.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, 0)
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(PURCHASES_KEY, purchase)
        editor.apply()
        val isSuccess = editor.commit()
        return Resource.Success(isSuccess)
    }

    fun getPurchases(): Resource<MutableList<ProductItem>> {
        val sharedPref = App.context.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, 0)
        val json = sharedPref.getString(PURCHASES_KEY,null)
        if(json != null){
            val list = Gson().fromJson(json,Array<ProductItem>::class.java)
            val product: MutableList<ProductItem> = mutableListOf()
            list.forEach {
                product.add(it)
            }
            return Resource.Success(data = product)
        }

        return Resource.DataError(DEFAULT_ERROR)
    }

}

