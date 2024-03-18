package com.mauto.myapplication

import android.Manifest
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.graphics.drawable.VectorDrawable
import android.location.Location
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.mauto.myapplication.adapter.MapAdapter2
import com.mauto.myapplication.batteryscanning.BatterySwappingActivity
import com.mauto.myapplication.home.adapter.MapAdapter
import com.mauto.myapplication.home.model.SwapLocationData
import com.mauto.myapplication.login.livedata.LoginLiveData
import com.mauto.myapplication.login.model.LocationResponse
import com.mauto.myapplication.login.model.LoginRequest
import com.mauto.myapplication.profile.ProfileActivity
import com.mauto.myapplication.swapingHistory.SwapingHisttoryActivity
import com.mauto.myapplication.utils.MAutoSharedPref
import com.mauto.myapplication.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_swap_staction.*
import kotlinx.android.synthetic.main.cancel_history.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import java.util.*

class SwapStactionActivity : AppCompatActivity(), OnMapReadyCallback {
    lateinit var pd: ProgressDialog
    private lateinit var supportMapFragment: SupportMapFragment
    private lateinit var mMap: GoogleMap
    private lateinit var currentLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var lat:String=""
    lateinit var model :LoginLiveData
    var lng:String=""
    private var googlemap: GoogleMap? = null
    lateinit var triplist: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swap_staction)
        model = ViewModelProvider(this)[LoginLiveData::class.java]
        pd = ProgressDialog(this)
        pd.setMessage("Loading...")
        triplist = findViewById(R.id.stactions2)


        map.setOnClickListener{
            var i = Intent(this@SwapStactionActivity,SwapStactionActivity::class.java)
            startActivity(i)
        }
        dashboard.setOnClickListener {
            var i = Intent(this, DashboardActivity::class.java)
            startActivity(i)
        }
        batterswap3.setOnClickListener {
            var i = Intent(this, BatterySwappingActivity::class.java)
            startActivity(i)
        }
        profile.setOnClickListener {
            var i = Intent(this, ProfileActivity::class.java)
            startActivity(i)
        }
        swap_history.setOnClickListener {
            var i = Intent(this, SwapingHisttoryActivity::class.java)
            startActivity(i)
        }




        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        supportMapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        supportMapFragment.getMapAsync(this)
    }
    override fun onMapReady(map: GoogleMap) {
        mMap = map

        // Check for location permissions
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // If permissions are granted, enable My Location layer and move camera to current location
            mMap.isMyLocationEnabled = true
            getAndMoveToCurrentLocation()
        } else {
            // Request location permissions
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }
    }
    private fun     getAndMoveToCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            location?.let {
                val currentLatLng = LatLng(it.latitude, it.longitude)
                lat= it.latitude.toString()
                lng=it.longitude.toString()

//                val customMarkerIcon = BitmapDescriptorFactory.fromBitmap(drawableToBitmap(
//                    AppCompatResources.getDrawable(this, R.drawable.swap_curen_location)))
//                mMap.addMarker(MarkerOptions().position(currentLatLng).icon(customMarkerIcon))

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 18f))

                Log.d("ghjhjljl" ,"$lng")
                loginSuccess(lng, lng)
            }
        }
    }
    private fun drawableToBitmap(drawable: Drawable?): Bitmap {
        val bitmap = Bitmap.createBitmap(
            drawable!!.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }
    private fun loginSuccess(curent_lat: String, curent_lagt: String) {
        try {
            val imm: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }



        var request: LoginRequest = LoginRequest(curent_lat, curent_lagt)

        //Declaring View model
        if(model==null)
            model = ViewModelProvider(this)[LoginLiveData::class.java]
        pd.show()
        //Calling API from Observer
        model.loginlocation(request)
            ?.observe(this, Observer { this.setUserStatus(it) })



    }


    private fun setUserStatus(userStatus: LocationResponse?){
        if (userStatus!!.status.equals("1")) {
            if (pd != null)
                pd.dismiss()
            var info = (userStatus)
            for (i in 0 until info!!.response!!.size){
                val latLng = LatLng(info.response!![i]!!.lat!!.toDouble(),userStatus.response!![i]!!.lng!!.toDouble())
                Log.d("hgjsdzhgfjls","$latLng")
                val swapmarkers =  mMap.addMarker(MarkerOptions().position(latLng).icon(getBitmapDescriptorFromVectorDrawable(R.drawable.swap_curen_location)))!!
                swapmarkers.tag = info.response!![i]
                swapmarkers.tag as SwapLocationData
            }

            MAutoSharedPref.getStactionList(this)
                .saveLocationlist(userStatus?.response)

            val payType = MAutoSharedPref.getAppPrefs(this).getDarction()

            val recyclerView =findViewById<RecyclerView>(R.id.stactions)

            recyclerView.layoutManager = LinearLayoutManager(this)
            var adapter = MapAdapter(this, payType!!)
            recyclerView.adapter = adapter
            initrecyclerviews(payType!!)





        } else  if (userStatus!!.status.equals("0")){
            ToastUtils.showToast(applicationContext, "Error")
        }
        else {
            if (pd != null)
                pd.dismiss()


            ToastUtils.showToast(applicationContext, "Error")
        }
    }
    private fun getBitmapDescriptorFromVectorDrawable(id: Int): BitmapDescriptor? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val vectorDrawable: VectorDrawable? = getDrawable(id) as VectorDrawable?
            val h: Int = vectorDrawable!!.getIntrinsicHeight()
            val w: Int = vectorDrawable.getIntrinsicWidth()
            vectorDrawable.setBounds(0, 0, w, h)
            val bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bm)
            vectorDrawable.draw(canvas)
            BitmapDescriptorFactory.fromBitmap(bm)
        } else {
            BitmapDescriptorFactory.fromResource(id)
        }
    }
    fun getpaymentpage(id:String?){
        val Id = id
        Log.d("hfvhaskjf","$Id")
        var i = Intent(this,   SwapStactionDetailsActivity::class.java)
        i.putExtra("staction_id", Id)
        startActivity(i)

    }
    fun track(lat:String?,lng:String?){
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?q=loc:" + lat+ "," +lng))
        startActivity(intent)
    }

    private fun initrecyclerviews(payType: ArrayList<SwapLocationData>) {
//        triplist.layoutManager = LinearLayoutManager(this)
        triplist.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        var adapter = MapAdapter2(this, payType!!, object : rideCustomOnClickListener {
            override fun onItemClickListener(view: View, position: Int, rideid: String)
            {
                val offerTtem = payType[position]

                getpaymentpage(offerTtem.id)
            }
        },object : dearcCustomOnClickListener{
            override fun onItemClickListener(view: View, position: Int, rideid: String) {
                val latalng = payType[position]

                track(latalng.lat,latalng.lng)
            }
        })
        triplist.adapter = adapter
        triplist.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

            }
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }



    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, enable My Location layer and move camera to current location
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    mMap.isMyLocationEnabled = true
                    getAndMoveToCurrentLocation()
                }
            } else {
                // Permission denied, handle accordingly
            }
        }
    }


}