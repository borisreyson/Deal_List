package com.misit.deallist.responses

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("user")
    var user: User? = null
)