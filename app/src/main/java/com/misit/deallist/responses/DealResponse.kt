package com.misit.deallist.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

 class DealResponse {

	 @SerializedName("deal")
	 @Expose
	 var deal: List<DealItem>? = null

	 @SerializedName("success")
	 @Expose
	 var success: Int? = null
 }