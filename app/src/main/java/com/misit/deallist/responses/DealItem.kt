package com.misit.deallist.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DealItem {

	@SerializedName("store_id")
	@Expose
	var storeId: Int? = null
	@SerializedName("end_date")
	@Expose
	var endDate: String? = null
	@SerializedName("price")
	@Expose
	var price: Int? = null
	@SerializedName("product_id")
	@Expose
	var productId: Int? = null
	@SerializedName("name")
	@Expose
	var name: String? = null
	@SerializedName("description")
	@Expose
	var description: String? = null
	@SerializedName("photo")
	@Expose
	var photo: String? = null
	@SerializedName("discount")
	@Expose
	var discount: Int? = null
	@SerializedName("id")
	@Expose
	var id: Int? = null
	@SerializedName("start_date")
	@Expose
	var startDate: String? = null
}