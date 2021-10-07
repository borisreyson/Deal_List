package com.misit.deallist.api

import com.misit.deallist.responses.*
import retrofit2.Call
import retrofit2.http.*

interface ApiEndPoint{
    @GET("neardeal/get/json")
    fun getStore(@Query("lat") lat:Double,@Query("lng") lng:Double): Call<StoreResponse>?
    @GET("neardeal/get/json")
    fun getProduct(@Query("store_id") store_id:String): Call<ProductsResponse>?
    @GET("neardeal/get/json")
    fun getToken(@Query("csrf_token") tokenId:String): Call<CsrfTokenResponse>?
    @GET("neardeal/get/json")
    fun getDeal(@Query("deal_id") deal_id:String): Call<DealResponse>?
    @GET("neardeal/get/json")
    fun getDetail(@Query("product_detail") product_detail:String): Call<DetailProductResponse>?

    @FormUrlEncoded
    @POST("/neardeal/get/json")
    fun login(@Field("username") username:String?,
              @Field("password") password:String?,
              @Field("_token") csrf_token:String?): Call<LoginResponse>

    @FormUrlEncoded
    @POST("/neardeal/get/json")
    fun checkout(@Field("products") products:MutableList<String>?,
              @Field("prices") prices:MutableList<String>?,
              @Field("_token") csrf_token:String?): Call<CheckOutResponse>
}