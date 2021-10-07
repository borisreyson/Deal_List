package com.misit.deallist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.misit.deallist.api.ApiClient
import com.misit.deallist.api.ApiEndPoint
import com.misit.deallist.model.Cart
import com.misit.deallist.responses.CheckOutResponse
import com.misit.deallist.responses.CsrfTokenResponse
import com.misit.deallist.utils.PopupUtil
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_check_out.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CheckOutActivity : AppCompatActivity() {

    private var csrf_token : String?=""
    var productList : MutableList<String>? = ArrayList()
    var priceList : MutableList<String>? = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_out)
        getToken()

        btnCheckOut.setOnClickListener {
            if(csrf_token!=""){
                checkOut()
            }else{
                Toast.makeText(this,"TOKEN TIDAK DITEMUKAN",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkOut() {
        var realm = Realm.getDefaultInstance()
        var realmRess = realm.where(Cart::class.java).findAll()
        productList?.clear()
        priceList?.clear()
    for(i in realmRess.indices){
        val cart= realmRess[i]
        productList?.add(cart?.productId!!)
        priceList?.add(cart?.price.toString()!!)
    }
    realm.close()

        PopupUtil.showLoading(this@CheckOutActivity,"Please Wait","Ordering Your Item")
        val apiEndPoint = ApiClient.getClient(this@CheckOutActivity)!!.create(ApiEndPoint::class.java)
        val call = apiEndPoint.checkout(productList,priceList,csrf_token)
        call.enqueue(object : Callback<CheckOutResponse>{
            override fun onFailure(call: Call<CheckOutResponse>, t: Throwable) {
                Log.d("checkGagal","Errror : $t")
                PopupUtil.dismissDialog()
            }

            override fun onResponse(
                call: Call<CheckOutResponse>,
                response: Response<CheckOutResponse>
            ) {
                Log.d("checkBerhasil",response.body().toString())
                PopupUtil.dismissDialog()
            }

        })

    }

    fun getToken(){
        val apiEndPoint = ApiClient.getClient(this)!!.create(ApiEndPoint::class.java)
        val call = apiEndPoint.getToken("csrf_token")
        call?.enqueue(object : Callback<CsrfTokenResponse> {
            override fun onFailure(call: Call<CsrfTokenResponse>, t: Throwable) {
                Toast.makeText(this@CheckOutActivity,"Error : $t", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(
                call: Call<CsrfTokenResponse>,
                response: Response<CsrfTokenResponse>
            ) {
                csrf_token = response.body()?.csrfToken
            }
        })

    }
}
