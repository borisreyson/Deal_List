package com.misit.deallist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.misit.deallist.adapter.CartItemAdapter
import com.misit.deallist.model.Cart
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity : AppCompatActivity() {
    var cartList : MutableList<Cart?>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        title = "Keranjang"
        val linearLayoutManager = LinearLayoutManager(this@CartActivity)
        recyclerCart?.layoutManager = linearLayoutManager
        cartList = ArrayList()

        val adapater = CartItemAdapter(this@CartActivity,cartList!!)
        recyclerCart?.adapter = adapater
        val realm = Realm.getDefaultInstance()
        val realmRes = realm.where(Cart::class.java).findAll()

        for(i in realmRes.indices){
            val cart = realmRes[i]
            cartList?.add(realm.copyFromRealm(cart))
        }
        adapater.notifyDataSetChanged()
        realm.close()
        CartBtnBuy.setOnClickListener {
            buyNow()
        }
    }

    private fun buyNow() {
        val intent = Intent(this@CartActivity,CheckOutActivity::class.java)
        startActivity(intent)
    }
}
