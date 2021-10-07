package com.misit.deallist

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.misit.deallist.fragment.StoreListFragment
import com.misit.deallist.utils.PopupUtil

    class MainActivity : AppCompatActivity(),LocationListener {

    private var storeListFragment:StoreListFragment? = null
    private var mLocationManager : LocationManager?=null
    private var mLocation : Location?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mLocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
            if(ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED ){
                PackageManager.PERMISSION_GRANTED
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),1)
            return
            }

        }
        assert(mLocationManager!=null)
        mLocationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER,
            10,10f,this)


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode==1){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                if(ActivityCompat.checkSelfPermission(this,
                     Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    &&
                    ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED)
                    {
                        return
                    }else
                    {
                        Toast.makeText(this,"Near Deal tidak mendapatkan lokasi",Toast.LENGTH_SHORT).show()
                    }
                mLocationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    10,10f,this)

                PopupUtil.showLoading(this,"","Finding your location")
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private  fun loadFragment(){
        storeListFragment = StoreListFragment()
        val argument = Bundle()
        argument.putDouble(StoreListFragment.Companion.KEY_LAT,mLocation!!.latitude)
        argument.putDouble(StoreListFragment.Companion.KEY_LNG,mLocation!!.longitude)
        storeListFragment?.setArguments(argument)
        supportFragmentManager.beginTransaction().replace(R.id.idContainers,storeListFragment!!)
            .commit()
    }

    override fun onLocationChanged(location: Location?) {
        PopupUtil.dismissDialog()
        mLocation=location
        mLocationManager?.removeUpdates(this)
        Log.d(
            "MainActivity",
            String.format("onLocationChange: %f %f",
                location?.latitude,
                location?.longitude))
        loadFragment()
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

    }

    override fun onProviderEnabled(provider: String?) {

    }

    override fun onProviderDisabled(provider: String?) {

    }
}
