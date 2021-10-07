package com.misit.deallist.responses

import com.google.gson.annotations.SerializedName

data class ProductResponse(

	@field:SerializedName("product")
	var product: List<ProductItem?>? = null,

	@field:SerializedName("success")
	val success: Int? = null
)