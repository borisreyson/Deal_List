package com.misit.deallist.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.misit.deallist.ProductActivity

import com.misit.deallist.R
import com.misit.deallist.adapter.DealItemAdapter
import com.misit.deallist.adapter.ProductItemAdapter
import com.misit.deallist.api.ApiClient
import com.misit.deallist.api.ApiEndPoint
import com.misit.deallist.responses.DealItem
import com.misit.deallist.responses.DealResponse
import com.misit.deallist.responses.Product
import com.misit.deallist.responses.ProductsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class DealListFragment : Fragment() {

    private var adapter: DealItemAdapter? = null
    private var dealList: MutableList<DealItem>? = null
    private var storeId: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_deal_list, container, false)

        val arguments = arguments
        storeId = arguments?.getString(ProductActivity.Companion.KEY_STORE_ID)
        val rv: RecyclerView = view.findViewById(R.id.recycler_view_deal_list)
        val gridLayoutManager = GridLayoutManager(context, 2)
        rv.layoutManager = gridLayoutManager

        dealList = ArrayList()
        adapter = DealItemAdapter(activity, dealList!!)
        rv.adapter = adapter
        loadDeal()
        return view
    }

    private fun loadDeal() {
        val apiEndPoint = ApiClient.getClient(activity)!!.create(ApiEndPoint::class.java)
        val call = apiEndPoint.getDeal(storeId!!)
        call?.enqueue(object : Callback<DealResponse?> {
            override fun onResponse(call: Call<DealResponse?>, response: Response<DealResponse?>) { //Menghilangkan tampilan loading
                val dealResponse = response.body()
                if (dealResponse != null) {
                    //if (productResponse.success==0) {
                    dealList?.addAll(dealResponse.deal!!)
                    activity?.runOnUiThread {
                        adapter?.notifyDataSetChanged()
                    }
                    //}
                } else {
                    Toast.makeText(activity, "Failed to Fetch Data\n" +
                            "Response is null", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<DealResponse?>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }


}
