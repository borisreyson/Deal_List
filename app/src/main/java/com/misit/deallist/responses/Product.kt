package com.misit.deallist.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/*Menambahkan ProductResponse untuk hasil koneksi pada akses database product #43.2*/

class Product {
    @SerializedName("id")
    @Expose
    var id: String? = null
    @SerializedName("store_id")
    @Expose
    var storeId: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("price")
    @Expose
    var price: String? = null
    @SerializedName("description")
    @Expose
    var description: String? = null
    @SerializedName("photo")
    @Expose
    var photo: String? = null

}