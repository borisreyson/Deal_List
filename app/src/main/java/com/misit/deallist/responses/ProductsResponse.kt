package com.misit.deallist.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/*Menambahkan Product POJO untuk setter getter data product #43.1*/
class ProductsResponse {
    @SerializedName("success")
    @Expose
    var success: Int? = null
    @SerializedName("product")
    @Expose
    var product: List<Product>? = null

}