package com.misit.deallist.fragment


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.misit.deallist.MapsActivity
import com.misit.deallist.ProductActivity

import com.misit.deallist.R
import com.misit.deallist.adapter.StoreItemAdapater
import com.misit.deallist.api.ApiClient
import com.misit.deallist.api.ApiEndPoint
import com.misit.deallist.responses.StoreItem
import com.misit.deallist.responses.StoreResponse
import com.misit.deallist.utils.ConnectivityUtil
import com.misit.deallist.utils.PopupUtil
import kotlinx.android.synthetic.main.fragment_store_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class StoreListFragment : Fragment(), StoreItemAdapater.OnItemClickListener {
    private var adapater :StoreItemAdapater? = null
    private var storeList : MutableList<StoreItem>?=null
    private  var lat:Double = 0.0
    private  var lng:Double = 0.0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //loadStore()
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_store_list, container, false)
        setHasOptionsMenu(true)
        val argument = arguments
        lat = argument!!.getDouble(KEY_LAT)
        lng = argument!!.getDouble(KEY_LNG)
        storeList = ArrayList()
        adapater = StoreItemAdapater(activity,storeList!!)
        val recyclerView:RecyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapater
        adapater?.setListener(this)

        if(ConnectivityUtil.isConnected(activity!!)){
            loadStore()
        }else
        {
            PopupUtil.showMsg(Activity(),"NO INTERNET CONNECTION",PopupUtil.SHORT)
        }
        return view
    }

    fun loadStore(){
        PopupUtil.showLoading(Activity(),"","LOADING STORES")
         val apiEndPoint = ApiClient.getClient(activity)!!.create(ApiEndPoint::class.java)
        val call = apiEndPoint.getStore(lat,lng)
        call?.enqueue(object : Callback<StoreResponse> {
            override fun onFailure(call: Call<StoreResponse>, t: Throwable) {
                PopupUtil.dismissDialog()
                Toast.makeText(activity,t.message,Toast.LENGTH_LONG).show()
                loggText.text = t.message
                Log.d("StoreListFragment",t.message)
            }

            override fun onResponse(call: Call<StoreResponse>, response: Response<StoreResponse>) {
                PopupUtil.dismissDialog()
                val storeResponse = response.body()
                if(storeResponse!=null){
                    if(storeResponse.success==0){
                        Log.d("StoreListFragment","Jumlah Store "+storeResponse.store?.size)
                        storeList?.addAll(storeResponse.store!!)
                        activity!!.runOnUiThread{adapater!!.notifyDataSetChanged()}
                    }

                }else{
                    Log.d("StoreListFragment","Response Null")
                }
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if(id==R.id.action_maps){
            val gson=Gson()
            val listOfObject = object : TypeToken<List<StoreItem?>>() { }.type
            val convertedStoreList = gson.toJson(storeList,listOfObject)

            val intent = Intent(activity,MapsActivity::class.java)
            intent.putExtra(MapsActivity.Companion.KEY_LAT,lat)
            intent.putExtra(MapsActivity.Companion.KEY_LNG,lng)
            intent.putExtra(MapsActivity.Companion.KEY_STORE_LIST,convertedStoreList)

            startActivity(intent)



        }
        return true
    }
    override fun onItemClick(storeId: String?) {
        //Log.d("ErrorStoreId",storeId)
        val intent = Intent(activity,ProductActivity::class.java)
        intent.putExtra(ProductActivity.Companion.KEY_STORE_ID,storeId)
        startActivity(intent)
    }
    companion object{
        const val  KEY_LAT = "lat"
        const val  KEY_LNG = "lng"
    }

}
