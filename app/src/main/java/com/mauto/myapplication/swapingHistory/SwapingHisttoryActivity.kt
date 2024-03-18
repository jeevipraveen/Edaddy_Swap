package com.mauto.myapplication.swapingHistory

import android.app.*
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mauto.myapplication.DashboardActivity
import com.mauto.myapplication.R
import com.mauto.myapplication.SwapStactionActivity
import com.mauto.myapplication.batteryscanning.BatterySwappingActivity
//import com.mauto.myapplication.home.BottomSheetDialog
import com.mauto.myapplication.profile.ProfileActivity
import com.mauto.myapplication.swapingHistory.livedata.SawpPaymentHistory
import com.mauto.myapplication.swapingHistory.model.SawapHistoryResponse
import com.mauto.myapplication.utils.ToastUtils
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.mauto.myapplication.home.model.SwapLocationData
import com.mauto.myapplication.login.livedata.LoginLiveData
import com.mauto.myapplication.login.model.FiltterRequest
import com.mauto.myapplication.login.model.LocationResponse
import com.mauto.myapplication.login.model.LoginRequest
import com.mauto.myapplication.rideCustomOnClickListener
import com.mauto.myapplication.swapingHistory.adapter.SwapHistoryAdapter
import com.mauto.myapplication.swapingHistory.adapter.SwapingHistoryAdapter2
import com.mauto.myapplication.swapingHistory.adapter.SwapingHistoryFiltterAdapter2
import com.mauto.myapplication.swapingHistory.model.SawapFiltterHistoryResponse
import com.mauto.myapplication.swapingHistory.model.SwapingArrayResponse
import com.mauto.myapplication.swapingHistory.model.SwapingFiltteringArrayResponse
import com.mauto.myapplication.utils.MAutoApplication
import com.mauto.myapplication.utils.MAutoSharedPref
import com.mauto.myapplication.utils.PrefConstant
import kotlinx.android.synthetic.main.activity_battery_swapping.*
import kotlinx.android.synthetic.main.activity_swaping_histtory.*
import kotlinx.android.synthetic.main.activity_swaping_histtory.batterswap3
import kotlinx.android.synthetic.main.activity_swaping_histtory.dashboard
import kotlinx.android.synthetic.main.activity_swaping_histtory.map
import kotlinx.android.synthetic.main.activity_swaping_histtory.profile
import kotlinx.android.synthetic.main.activity_swaping_histtory.swap_history
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SwapingHisttoryActivity : AppCompatActivity() {
    lateinit var pd: ProgressDialog
    lateinit var model : SawpPaymentHistory
    lateinit var model2 : LoginLiveData
    private lateinit var mContext: Activity
    var date_from:String=""
    var cal = Calendar.getInstance()
    var date_to:String=""
    var vehicle_number:String=""
    var battery_number:String=""
    var station_id:String=""
    var show_all:String=""
//    var show_all:String=""
    lateinit var  txt :TextInputLayout
    lateinit var  txtto :TextInputLayout
    var datevalue =""
    var datevaluenew =""
    var startdate =""
    lateinit var triplist: RecyclerView
    lateinit var triplist2: RecyclerView

    var vehicle_numbers =""




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swaping_histtory)

        model = ViewModelProvider(this)[SawpPaymentHistory::class.java]
        model2 = ViewModelProvider(this)[LoginLiveData::class.java]
        pd = ProgressDialog(this)
        pd.setMessage("Loading...")
        mContext = this@SwapingHisttoryActivity
        var filter = findViewById<ImageView>(R.id.filter)
        var attendance1 = findViewById<TextView>(R.id.attendance1)
        var hedaer = findViewById<LinearLayout>(R.id.hedaer)
        triplist = findViewById(R.id.swap_1history)
        triplist2= findViewById(R.id.filter_ans)
          makePayment()
        val curent_lat =
            MAutoSharedPref.getAppPrefs(MAutoApplication.getInstance().context).getStringValue(
                PrefConstant.CURENT_LAT)
        val curent_lagt =
            MAutoSharedPref.getAppPrefs(MAutoApplication.getInstance().context).getStringValue(
                PrefConstant.CURENT_LANG)
        loginSuccess(curent_lat,curent_lagt)

        attendance1.setOnClickListener{
//            hedaer.visibility= View.GONE
            swapworkin.visibility= View.GONE
            attendance1.visibility=View.GONE
            attendance_working.visibility=View.VISIBLE
            swap_unwork.visibility=View.VISIBLE
            swaps.visibility=View.GONE
            attendance.visibility=View.VISIBLE
        }
        swap_unwork.setOnClickListener{

            swapworkin.visibility= View.VISIBLE
            attendance1.visibility=View.VISIBLE
            attendance_working.visibility=View.GONE
            swap_unwork.visibility=View.GONE
            swaps.visibility=View.VISIBLE
            attendance.visibility=View.GONE
        }

        close_de.setOnClickListener {
            filter_result  .visibility=View.GONE
            hedaer.visibility=View.VISIBLE
        }
        filter.setOnClickListener {

//            paymentlistdialog()
            filterdialog()

        }
//        history(station_id,date_from,date_to,vehicle_number,battery_number,show_all)

        map.setOnClickListener{
            var i = Intent(this, SwapStactionActivity::class.java)
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
    }

    private fun history(station_id: String,date_from:String,date_to:String,vehicle_number:String,battery_number:String,show_all:String) {
        try {
            val imm: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }

//        var request: HistoryRequest = HistoryRequest(barcode)


        if(model==null)
            model = ViewModelProvider(this)[SawpPaymentHistory::class.java]
        pd.show()
        //Calling API from Observer
//        model.loginlocation(request)
//            ?.observe(this, Observer { this.setOTPStatus(it) })


    }

    fun makePayment() {
        if (model == null)
            model = ViewModelProvider(this)[SawpPaymentHistory::class.java]
        pd.show()
        //Calling API from Observer
        model.swapoverallHistory()
            ?.observe(this, Observer { this.setPayStatus(it) })
    }

    fun paymentlistdialog() {
        val dialog =BottomSheetDialog(this)
        val contentView: View = getLayoutInflater().inflate(R.layout.bottom, null);
        dialog.setCancelable(true)
        dialog.setContentView(contentView)



        dialog!!.show()


    }




    fun filterdialog() {
        val bottomSheetDialogs =BottomSheetDialog(this)
        val contentView: View= getLayoutInflater().inflate(R.layout.bottom, null);
        bottomSheetDialogs!!.setContentView(contentView)
        bottomSheetDialogs!!.setCancelable(true)

        val mcalendar = contentView.findViewById(R.id.calendarss) as ImageView
        val calendarsto = contentView.findViewById(R.id.calendarsto) as ImageView
        val apply = contentView.findViewById<CardView>(R.id.apply)
        val cancel = contentView.findViewById<LinearLayout>(R.id.cancel)

////        lateinit var  txt :TextInputLayout
////        lateinit var  txtto :TextInputLayout
//        var txt=contentView.findViewById(R.id.txt) as TextInputLayout

        val not_ok_img_qr = contentView.findViewById<ImageView>(R.id.not_ok_img_qr)
        val ok_img_qr  = contentView.findViewById<ImageView>(R.id.ok_img_qr1)
        txt= contentView.findViewById(R.id.txt) as TextInputLayout
        txtto= contentView.findViewById(R.id.txtto) as TextInputLayout
        val v_number = contentView.findViewById(R.id.v_number)as TextInputEditText
        val B_number = contentView.findViewById(R.id.B_number)as TextInputEditText
        val locat = contentView.findViewById(R.id.locat)as TextInputEditText
        val arttyu = contentView.findViewById(R.id.arttyu)as Spinner

        val payType = MAutoSharedPref.getAppPrefs(mContext).getDarction()
        val swapLocations = listOf(
            SwapLocationData(id ="",
                code = "",
                name = "",
                contact_number = "",
                country_code = "",
                image = "",
                geolocation = "",
                lat = "",
                lng = "",
                total_batteries = "",
                status = "",
                charging_batteries = "",
                available_batteries = "",
                total_swaps_no = "",
                total_swaps_amount = "",
                total_energy_served = "",
                total_swap_duration = ""))

        val stationNames = swapLocations.map { it.name }

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, stationNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        arttyu.adapter = adapter

        ////        val Filterclick = contentView.findViewById(R.id.Filterclick) as Button
//        var vehicle_number_textinput=contentView.findViewById(R.id.vehicle_number_textinput) as TextInputEditText
//        Filterclick.setOnClickListener {
//            if (isDateValid()==true){
//                vehicle_numbers=vehicle_number_textinput.text.toString()
////                mSessionManager.setvehicle_numbers(vehicle_numbers)
////                mSessionManager.setfrome_date(datevalue)
////                mSessionManager.setto_date(datevaluenew)
////                getFaqruser()
//                bottomSheetDialogs!!.dismiss()
//
//            }else{
//
//            }

//        }

        not_ok_img_qr.setOnClickListener {
            ok_img_qr.visibility = View.VISIBLE
            not_ok_img_qr.visibility = View.GONE
            show_all="true"
            Log.d("jhbfhjbgdhkjhghgv","$show_all")
        }
        ok_img_qr.setOnClickListener {
            not_ok_img_qr.visibility = View.VISIBLE
            ok_img_qr.visibility = View.GONE
            show_all=""
            Log.d("jhhgv","$show_all")
        }

        mcalendar.setOnClickListener {
            DatePickerDialog(this,
                dateSetListener,
                // set DatePickerDialog to point to today's date when it loads up
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }
//
        calendarsto.setOnClickListener {
            DatePickerDialog(this,
                dateSetListenernew,
                // set DatePickerDialog to point to today's date when it loads up
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }
//
//
////

        cancel.setOnClickListener {
            bottomSheetDialogs!!.dismiss()
        }
        apply.setOnClickListener {

            var Vehicle_number = v_number.text.toString()
            var Battery_number = B_number.text.toString()
             var Location = locat.text.toString()

            apihit(datevalue,datevaluenew,Vehicle_number,Battery_number,Location,show_all)
            bottomSheetDialogs!!.dismiss()


        }
        bottomSheetDialogs!!.show()
        bottomSheetDialogs.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        bottomSheetDialogs.getWindow()?.setLayout(ActionBar.LayoutParams.MATCH_PARENT,
            ActionBar.LayoutParams.WRAP_CONTENT);
        bottomSheetDialogs.getWindow()?.getAttributes()?.windowAnimations =R.style.DialogAnimation
    }


    fun apihit(satrtdate:String,fromdate:String,Vehicle_number:String,Battery_number:String,Location:String,show_all:String){
////       var  Satrtdate = satrtdate
////       var Fromdate = fromdate
//        Log.d("fdfejysgdfjyga",Satrtdate)
//        Log.d("hgasgdjfjdbvdguyvka",Fromdate)
        var request: FiltterRequest = FiltterRequest(satrtdate,fromdate,Vehicle_number,Battery_number,Location,show_all)
        if (model == null)
            model = ViewModelProvider(this)[SawpPaymentHistory::class.java]
        pd.show()


        model.swapoverallFillterHistory(request)
            ?.observe(this, Observer { this.setfilterPayStatus(it) })
    }



    private fun setfilterPayStatus(userStatus: SawapFiltterHistoryResponse) {
        if (userStatus!!.status.equals("1")) {
            if (pd != null)
                pd.dismiss()
            val filterarray = userStatus?.response!!.swap_history
            filtterarrayview(filterarray!!)

            hedaer.visibility=View.GONE
            filter_result.visibility=View.VISIBLE
            ToastUtils.showToast(applicationContext, "intha filtter eruku ")
        }
    }

    private fun setPayStatus(userStatus: SawapHistoryResponse) {
        if (userStatus!!.status.equals("1")) {
            if (pd != null)
                pd.dismiss()
            swap_count.text=userStatus.response.stats.no_of_swaps+""
            energy_count.text=userStatus.response.stats.total_energy+"KW"
            duration.text=userStatus.response.stats.time_taken+"hrs"
//            ToastUtils.showToast(applicationContext, "okok")

//            MAutoSharedPref.getStactionList(this).saveSwapinHistory(userStatus?.response!!.avail_battery)
//            val payType = MAutoSharedPref.getAppPrefs(this).getSwapHistory()
            val histlist =userStatus?.response!!.swap_history
            recycler_view.layoutManager = LinearLayoutManager(this)
            recycler_view.addItemDecoration(DividerItemDecoration(recycler_view.context, DividerItemDecoration.VERTICAL))
            Log.d("vennai","$histlist[0].value+")

            Log.d("khhfjkh","$histlist")
            var adapter = SwapHistoryAdapter(this, histlist!!)
            recycler_view.adapter = adapter
//            recycler_view.visibility=View.VISIBLE
            initrecyclerviews(histlist!!)
            attendanceview(histlist)


        }else{
            if (pd != null)
                pd.dismiss()
            ToastUtils.showToast(applicationContext, "intha user ellayam")
        }
    }
    companion object {
        private var instances2: SwapingHisttoryActivity? = null
        fun getInstance(): SwapingHisttoryActivity? {
            return instances2
        }
    }


    private fun initrecyclerviews(histlist: ArrayList<SwapingArrayResponse>) {
        triplist.layoutManager = LinearLayoutManager(this)

        var adapter = SwapingHistoryAdapter2(this, histlist!!, object : rideCustomOnClickListener {
            override fun onItemClickListener(view: View, position: Int, rideid: String)
            {
                val offerTtem = histlist[position]

                arraysgaring(offerTtem.id,
                            offerTtem.timestamp,
                            offerTtem.vehicle_number,
                            offerTtem.station,
                            offerTtem.status,
                            offerTtem.amount,
                            offerTtem.cycle_distance)
//                getpaymentpage(offerTtem.id)
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

    private fun filtterarrayview(filterarray: ArrayList<SwapingFiltteringArrayResponse>) {
        triplist2.layoutManager = LinearLayoutManager(this)

        var adapter = SwapingHistoryFiltterAdapter2(this, filterarray!!, object : rideCustomOnClickListener {
            override fun onItemClickListener(view: View, position: Int, rideid: String)
            {
                val offerTtem = filterarray[position]

                arraysgaring(offerTtem.id,
                    offerTtem.timestamp,
                    offerTtem.vehicle_number,
                    offerTtem.station,
                    offerTtem.status,
                    offerTtem.amount,
                    offerTtem.cycle_distance)
//                getpaymentpage(offerTtem.id)
            }
        })
        triplist2.adapter = adapter
        triplist2.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

            }
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })

    }
    private fun attendanceview(histlist: ArrayList<SwapingArrayResponse>) {

    }

    fun arraysgaring(id:String?,timestamp:String?, vehicle_number:String?,station:String?,status:String?,amount:String?,cycle_distance:String?){
        var i = Intent(this, SwapHistorryArrayActivity::class.java)
        i.putExtra("id",id)
        i.putExtra("timestamp",timestamp)
        i.putExtra("vehicle_number",vehicle_number)
        i.putExtra("station",station)
        i.putExtra("status",status)
        i.putExtra("amount",amount)
        i.putExtra("cycle_distance",cycle_distance)
        startActivity(i)

    }

    private fun setOTPStatus(userStatus: SawapHistoryResponse) {
        if (userStatus!!.status.equals("1")) {
            if (pd != null)
                pd.dismiss()

            ToastUtils.showToast(applicationContext, "okok")

            vehicle_result.visibility = View.VISIBLE
            activeoldbatteryescan.visibility = View.VISIBLE
            vehicleOld.visibility = View.GONE
            vehiclescan.visibility = View.GONE

        }else{
            if (pd != null)
                pd.dismiss()
            vehicle_result.visibility = View.VISIBLE
            activeoldbatteryescan.visibility = View.VISIBLE
            vehicleOld.visibility = View.GONE
            vehiclescan.visibility = View.GONE
            ToastUtils.showToast(applicationContext, "intha user ellayam")
        }

    }
    val dateSetListenernew = object : DatePickerDialog.OnDateSetListener {
        override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                               dayOfMonth: Int) {
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateInViewnew()
        }
    }
    private fun updateDateInViewnew() {
        val myFormat = "yyyy-MM-dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        datevaluenew  = sdf.format(cal.getTime())
        Log.d("chechk22a", datevaluenew)
        startdate=datevaluenew
        Log.d("startdate", startdate)
        txtto.getEditText()!!.setText(datevaluenew)

    }
    val dateSetListener = object : DatePickerDialog.OnDateSetListener {
        override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                               dayOfMonth: Int) {
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateInView()
        }
    }
    private fun updateDateInView() {
        val myFormat = "yyyy-MM-dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        datevalue  = sdf.format(cal.getTime())
        Log.d("chechk22a", datevalue)
        txt.getEditText()!!.setText(datevalue)

    }
    private fun loginSuccess(curent_lat: String, curent_lagt: String) {
        try {
            val imm: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }


//        val split = defaultflag.split(",")
        var request: LoginRequest = LoginRequest(curent_lat, curent_lagt)

        //Declaring View model
        if(model2==null)
            model2 = ViewModelProvider(this)[LoginLiveData::class.java]
        pd.show()
        //Calling API from Observer
        model2.loginlocation(request)
            ?.observe(this, Observer { this.setUserStatus(it) })
//            ?.observe(this, Observer { this.setUserStatus(it) })


    }
    private fun setUserStatus(userStatus: LocationResponse?){
        if (userStatus!!.status.equals("1")) {
            if (pd != null)
                pd.dismiss()

//            val SwapLocationData = userStatus?.response
//            val swapLocations = listOf(
//                SwapLocationData(id ="",
//                    code = "",
//                    name = "",
//                    contact_number = "+",
//                    country_code = "",
//                    image = "",
//                    geolocation = "",
//                    lat = "",
//                    lng = "",
//                    total_batteries = "",
//                    status = "Active",
//                    charging_batteries = "",
//                    available_batteries = "",
//                    total_swaps_no = "",
//                    total_swaps_amount = "",
//                    total_energy_served = "",
//                    total_swap_duration = ""))
//
//
//            val stationNames = swapLocations.map { it.name }
//
//            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, stationNames)
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            spinner.adapter = adapter
////            MAutoSharedPref.getStactionList(mContext).saveLocationlist(userStatus?.response)
//



        } else  if (userStatus!!.status.equals("0")){
            ToastUtils.showToast(applicationContext, "Error")
        }
        else {
            if (pd != null)
                pd.dismiss()
            MAutoSharedPref.getAppPrefs(applicationContext).saveBooleanValue(
                PrefConstant.LOGIN_STATUS,
                false)

            ToastUtils.showToast(applicationContext, "Error")
        }
    }



    fun isDateValid():Boolean{
        if (datevalue.isEmpty()){
//            toast("Selected From  date")
            return false
        }
        if (datevaluenew.isEmpty()){
//            toast("Selected To date")
            return false
        }
        return true
    }

}
