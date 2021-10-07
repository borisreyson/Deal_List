package com.misit.deallist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.misit.deallist.api.ApiClient
import com.misit.deallist.api.ApiEndPoint
import com.misit.deallist.model.Cart
import com.misit.deallist.responses.DetailProductResponse
import com.misit.deallist.utils.PopupUtil
import com.squareup.picasso.Picasso
import io.realm.Realm
import io.realm.Sort
import kotlinx.android.synthetic.main.activity_product_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class ProductDetailActivity : AppCompatActivity() {

    var productId : String? = null
    var detailResponse : DetailProductResponse? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        productId = intent.getStringExtra(KEY_PRODUCT_ID)
        if(productId!=null){
            loadProduct(productId!!)
        }
        btnBuy.setOnClickListener {
            buyProduct()
        }
    }

    private fun buyProduct() {
        val bulder = AlertDialog.Builder(this@ProductDetailActivity)
        bulder.setTitle("Ingin Lanjut Belanja?")
        bulder.setIcon(R.drawable.ic_warning_black_24dp)
        bulder.setNegativeButton("Lanjut Belanja"){
            dialog, which ->
            buyProducts()
            finish()
        }
        bulder.setNeutralButton("Bayar"){
            dialog, which ->

            buyProducts()
            val intent = Intent(this@ProductDetailActivity,CartActivity::class.java)
            startActivity(intent)
            finish()
        }
        bulder.setPositiveButton("Cancel"){
            dialog, which ->
            dialog.cancel()
        }
        bulder.show()
    }

    private fun buyProducts() {
        var realm = Realm.getDefaultInstance()
        val name = detailResponse?.name
        val price = detailResponse?.newPrice!!.toDouble()
        val photo = detailResponse?.photo

        realm.executeTransaction{
            val carts = realm.where(Cart::class.java)
                .findAllSorted("id",Sort.DESCENDING)

            var lastId=0
            if(carts.size>0){
                lastId=carts.first()!!.id
            }
            val cart = Cart()
            cart.id = lastId+1
            cart.productId = productId
            cart.productName = name
            cart.price = price
            cart.photo = photo

            try {
                realm.copyToRealm(cart)
                PopupUtil.showMsg(this@ProductDetailActivity,"Berhasil Ditambah Ke Keranjang Belanja",
                    PopupUtil.SHORT)

            }catch (e:Exception){
                Toast.makeText(this@ProductDetailActivity,"Pembelian Produk Gagal : $e",Toast.LENGTH_SHORT).show()
            }
        }
        realm.close()
    }

    private fun loadProduct(productId:String) {
        val apiEndpoint = ApiClient.getClient(this@ProductDetailActivity)!!.create(ApiEndPoint::class.java)
        val call = apiEndpoint.getDetail(productId)
        call?.enqueue(object : Callback<DetailProductResponse?>{
            override fun onFailure(call: Call<DetailProductResponse?>, t: Throwable) {
                Toast.makeText(this@ProductDetailActivity,"ERROR LOAT $t",Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<DetailProductResponse?>,
                response: Response<DetailProductResponse?>
            ) {
                detailResponse = response.body()
                if(detailResponse!=null){
                    runOnUiThread {
                        Picasso.get().load(detailResponse?.productPhoto).into(imageView)
                        Picasso.get().load(detailResponse?.photoStore).into(profileImg)
                        tv_store_name.text= detailResponse?.name
                        tv_description.text= detailResponse?.description
                        tvHargaOld.text= detailResponse?.price.toString()
                        tvHargaNew.text = detailResponse?.newPrice.toString()
                        title = detailResponse?.name
                    }
                }
            }

        })
    }

    companion object{
        const val KEY_PRODUCT_ID = "product_id"
    }
}
