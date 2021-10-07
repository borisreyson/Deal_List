package com.misit.deallist.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CheckOutResponse {
    @SerializedName("success")
    @Expose
    var success:Boolean? = null
}