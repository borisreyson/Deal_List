package com.misit.deallist

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.misit.deallist.responses.StoreItem

class MapsActivity : FragmentActivity(), OnMapReadyCallback {

    private var mMap : GoogleMap?= null
    private var lat = 0.0
    private var lng = 0.0
    private var storeList :String? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        mapFragment?.getMapAsync(this)

        lat = intent.getDoubleExtra(KEY_LAT,0.0)
        lng = intent.getDoubleExtra(KEY_LNG,0.0)
        storeList = intent.getStringExtra(KEY_STORE_LIST)

    }
    companion object{
        val KEY_LAT = "key_lat"
        val KEY_LNG = "key_lng"
        val KEY_STORE_LIST = "key_store_list"
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap
        val userLocation = LatLng(lat,lng)
        mMap?.addMarker(MarkerOptions().position(userLocation).title("LOKASI ANDA"))
        mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation,15f))
        if(ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)!=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
        != PackageManager.PERMISSION_GRANTED){ return }
        mMap?.isMyLocationEnabled = true

        val gson= Gson()
        val listOfObject = object : TypeToken<List<StoreItem?>?>() {}.type

        val convertedList = gson.fromJson<List<StoreItem>>(storeList,listOfObject)
        if(convertedList!=null){
            for (i in convertedList.indices){
                val store = convertedList[i]
                val storeLatLng = LatLng(store.lat!!.toDouble(),store.lng!!.toDouble())
                mMap?.addMarker(MarkerOptions()
                    .position(storeLatLng)
                    .title(store.name)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.store)))
            }
        }

    }
}
