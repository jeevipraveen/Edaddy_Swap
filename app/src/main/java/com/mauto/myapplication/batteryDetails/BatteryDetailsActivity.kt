package com.mauto.myapplication.batteryDetails

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mauto.myapplication.DashboardActivity
import com.mauto.myapplication.R
import com.mauto.myapplication.SwapStactionActivity
import com.mauto.myapplication.batteryscanning.BatterySwappingActivity
import com.mauto.myapplication.home.adapter.MapAdapter
import com.mauto.myapplication.login.livedata.LoginLiveData
import com.mauto.myapplication.login.model.LoginRequest
import com.mauto.myapplication.profile.ProfileActivity
import com.mauto.myapplication.stactionhistory.StactionInforResponce
import com.mauto.myapplication.stactionhistory.StactionRequest
import com.mauto.myapplication.swapingHistory.SwapingHisttoryActivity
import com.mauto.myapplication.swapingHistory.livedata.SawpPaymentHistory
import com.mauto.myapplication.swapingHistory.model.SawapHistoryResponse
import com.mauto.myapplication.utils.MAutoSharedPref
import com.mauto.myapplication.utils.PrefConstant
import com.mauto.myapplication.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_battery_details.*
import kotlinx.android.synthetic.main.activity_dashboard.batterswap3
import kotlinx.android.synthetic.main.activity_dashboard.dashboard
import kotlinx.android.synthetic.main.activity_dashboard.map
import kotlinx.android.synthetic.main.activity_dashboard.profile
import kotlinx.android.synthetic.main.activity_dashboard.swap_history
import kotlinx.android.synthetic.main.activity_swap_staction_details.*

class BatteryDetailsActivity : AppCompatActivity() {
    lateinit var pd: ProgressDialog
    var addamount:String=""
    var staction_id:String=""
    private lateinit var model: SawpPaymentHistory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_battery_details)
        model = ViewModelProvider(this)[SawpPaymentHistory::class.java]
        addamount= intent.getStringExtra("mapimage").toString()
//        staction_id= intent.getStringExtra("stactin_id").toString()
        pd = ProgressDialog(this)
        pd.setMessage("Loading...")

        ToastUtils.showToast(applicationContext, "$staction_id")
        staction_id = MAutoSharedPref.getAppPrefs(this).getStringValue(PrefConstant.MAT_STACTION_ID)
        makePayment(staction_id)
        agentname2.setText(addamount)
        if (addamount.equals("75%-100%")){
            first.visibility =View.VISIBLE
            ToastUtils.showToast(applicationContext, "75%-100%")

        }else if (addamount.equals("50%-75%")){
            ToastUtils.showToast(applicationContext, "50%-75%")
            second.visibility =View.VISIBLE
        }else if (addamount.equals("25%-50%")){
            ToastUtils.showToast(applicationContext, "25%-50%")
            third.visibility =View.VISIBLE
        }else {
            ToastUtils.showToast(applicationContext, "25%-0%")
            four.visibility =View.VISIBLE
        }


        map.setOnClickListener{
            var i = Intent(this,SwapStactionActivity::class.java)
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
        back.setOnClickListener {
            finish()
        }
    }


    fun makePayment(staction_id:String) {
        if (model == null)
            model = ViewModelProvider(this)[SawpPaymentHistory::class.java]
        pd.show()
        //Calling API from Observer

        var request: StactionRequest = StactionRequest(staction_id)
        model.swapstaioninfo(request)
            ?.observe(this, Observer { this.setPayStatus(it) })
    }
    private fun setPayStatus(userStatus: StactionInforResponce) {
        if (userStatus!!.status.equals("1")) {
            if (pd != null)
                pd.dismiss()
            var data =userStatus.response.avail_battery
            Log.d("fdkzhfkjhszlkjfdojd","$data")
            if (data.isNotEmpty()) {

                val first = data[0].batteries
                val first_view =findViewById<RecyclerView>(R.id.first)
                first_view.layoutManager = LinearLayoutManager(this)
                var adapter = FirstAdapter(this, first!!)
                first_view.adapter = adapter

                val second = data[1].batteries
                val second_view =findViewById<RecyclerView>(R.id.second)
                second_view.layoutManager = LinearLayoutManager(this)
                var adapter1 = SecondAdapter(this, second!!)
                second_view.adapter = adapter1

                val third = data[2].batteries
                val third_view =findViewById<RecyclerView>(R.id.third)
                third_view.layoutManager = LinearLayoutManager(this)
                var adapter2 = ThirdAdapter(this, third!!)
                third_view.adapter = adapter2



                val fourth = data[3].batteries
                val four_view =findViewById<RecyclerView>(R.id.four)
                four_view.layoutManager = LinearLayoutManager(this)
                var adapter3 = FourthAdapter(this, fourth!!)
                four_view.adapter = adapter3




            }
        }else{
            if (pd != null)
                pd.dismiss()




        }
    }

}