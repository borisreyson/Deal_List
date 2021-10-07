package com.misit.deallist.responses

import com.google.gson.annotations.SerializedName

data class DetailProductResponse(

	@field:SerializedName("store_id")
	val storeId: Int? = null,

	@field:SerializedName("telp")
	val telp: String? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("lng")
	val lng: Double? = null,

	@field:SerializedName("photo_store")
	val photoStore: String? = null,

	@field:SerializedName("photo")
	val photo: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("product_photo")
	val productPhoto: String? = null,

	@field:SerializedName("price")
	val price: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("open_hour")
	val openHour: String? = null,

	@field:SerializedName("newPrice")
	val newPrice: String? = null,


	@field:SerializedName("lat")
	val lat: Double? = null
)