package com.misit.neardeal.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.misit.deallist.ProductActivity
import com.misit.deallist.ProductDetailActivity
import com.misit.deallist.R
import com.misit.deallist.adapter.ProductItemAdapter
import com.misit.deallist.api.ApiClient
import com.misit.deallist.api.ApiEndPoint
import com.misit.deallist.responses.Product
import com.misit.deallist.responses.ProductItem
import com.misit.deallist.responses.ProductsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class ProductListFragment : Fragment(), ProductItemAdapter.OnItemClickListener {
    private var adapter: ProductItemAdapter? = null
    private var productList: MutableList<Product>? = null
    private var storeId: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_product_list, container, false)

        val arguments = arguments
        storeId = arguments?.getString(ProductActivity.Companion.KEY_STORE_ID)
        val rv: RecyclerView = view.findViewById(R.id.recycler_view_product_list)
        val gridLayoutManager = GridLayoutManager(context, 2)
        rv.layoutManager = gridLayoutManager

        productList = ArrayList()
        adapter = ProductItemAdapter(activity, productList!!)
        rv.adapter = adapter
        adapter?.setListener(this)
        loadProduct()
        return view
    }

    private fun loadProduct() {
        val apiEndPoint = ApiClient.getClient(activity)!!.create(ApiEndPoint::class.java)
        val call = apiEndPoint.getProduct(storeId!!)
        call?.enqueue(object : Callback<ProductsResponse?> {
            override fun onResponse(call: Call<ProductsResponse?>, response: Response<ProductsResponse?>) { //Menghilangkan tampilan loading
                val productResponse = response.body()
                if (productResponse != null) {
                    //if (productResponse.success==0) {
                        productList?.addAll(productResponse.product!!)
                        activity?.runOnUiThread {
                            adapter?.notifyDataSetChanged()
                        }
                    //}
                } else {
                    Toast.makeText(activity, "Failed to Fetch Data\n" +
                            "Response is null", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<ProductsResponse?>, t: Throwable) { //                PopupUtil.dismissDialog();
                Toast.makeText(activity, "Failed to Fetch \n Reason : $t", Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onItemClick(ProductId: String?) {
        val intent = Intent(activity,ProductDetailActivity::class.java)
        intent.putExtra(ProductDetailActivity.Companion.KEY_PRODUCT_ID,ProductId)
        startActivity(intent)
    }

}