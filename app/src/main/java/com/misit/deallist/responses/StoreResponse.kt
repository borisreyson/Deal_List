package com.misit.deallist.responses

import com.google.gson.annotations.SerializedName

data class StoreResponse(

	@field:SerializedName("success")
	val success: Int? = null,

	@field:SerializedName("store")
	var store: List<StoreItem>? = null
)