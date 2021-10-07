package com.misit.deallist

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import com.misit.deallist.api.ApiClient
import com.misit.deallist.api.ApiEndPoint
import com.misit.deallist.responses.CsrfTokenResponse
import com.misit.deallist.responses.LoginResponse
import com.misit.deallist.responses.StoreResponse
import com.misit.deallist.utils.PopupUtil
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private var csrf_token : String?=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        getToken()
        Log.d("tokenId",csrf_token)
        cekUser()
        val actionBar = supportActionBar
        actionBar?.hide()
        btnLogin.setOnClickListener {
            if(csrf_token!=""){
                login()
            }else{
                Toast.makeText(this,"TOKEN TIDAK DITEMUKAN",Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun cekUser() {
        val sharedPreferences = getSharedPreferences("com.misit.deallist",Context.MODE_PRIVATE)
        val cekUsers = sharedPreferences.getBoolean("isLoggiedIn",false)
        if(cekUsers){
            val intent = Intent(this@LoginActivity,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun login() {
        PopupUtil.showLoading(this@LoginActivity,"Logging In","Please Wait")
        val apiEndPoint = ApiClient.getClient(this)!!.create(ApiEndPoint::class.java)
        val call = apiEndPoint.login(username.text.toString().trim(),
            password.text.toString().trim(),
            csrf_token
            )
        call?.enqueue(object : Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                PopupUtil.dismissDialog()
                Toast.makeText(this@LoginActivity,"Error : $t",Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                PopupUtil.dismissDialog()
                var loginResponse = response.body()
                if(loginResponse!=null){
                    val sharedPreferences = getSharedPreferences("com.misit.deallist",Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("id",loginResponse.user?.id)
                    editor.putBoolean("isLoggiedIn",true)
                    editor.apply()
                    runOnUiThread {
                        val intent = Intent(this@LoginActivity,MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        })

    }

    fun getToken(){
        val apiEndPoint = ApiClient.getClient(this)!!.create(ApiEndPoint::class.java)
        val call = apiEndPoint.getToken("csrf_token")
        call?.enqueue(object : Callback<CsrfTokenResponse> {
            override fun onFailure(call: Call<CsrfTokenResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity,"Error : $t",Toast.LENGTH_SHORT).show()
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
