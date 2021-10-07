package com.misit.deallist.responses

import com.google.gson.annotations.SerializedName

data class StoreItem(

	@field:SerializedName("telp")
	val telp: String? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("lng")
	val lng: Double? = null,

	@field:SerializedName("distance")
	val distance: Double? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("photo")
	val photo: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("open_hour")
	val openHour: String? = null,

	@field:SerializedName("lat")
	val lat: Double? = null
)