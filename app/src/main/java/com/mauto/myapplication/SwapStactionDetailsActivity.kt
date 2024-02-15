package com.mauto.myapplication

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
import com.mauto.myapplication.batteryDetails.FirstAdapter
import com.mauto.myapplication.batteryDetails.FourthAdapter
import com.mauto.myapplication.batteryDetails.SecondAdapter
import com.mauto.myapplication.batteryDetails.ThirdAdapter
import com.mauto.myapplication.batteryscanning.BatterySwappingActivity
import com.mauto.myapplication.profile.ProfileActivity
import com.mauto.myapplication.stactionhistory.StactionInforResponce
import com.mauto.myapplication.stactionhistory.StactionRequest
import com.mauto.myapplication.stactionhistory.adapter.FirstStactionAdapter
import com.mauto.myapplication.stactionhistory.adapter.FourthStactionAdapter
import com.mauto.myapplication.stactionhistory.adapter.SecondStactionAdapter
import com.mauto.myapplication.stactionhistory.adapter.ThirdStactionAdapter
import com.mauto.myapplication.swapingHistory.SwapingHisttoryActivity
import com.mauto.myapplication.swapingHistory.livedata.SawpPaymentHistory
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_dashboard.agentname
import kotlinx.android.synthetic.main.activity_swap_staction_details.*

class SwapStactionDetailsActivity : AppCompatActivity() {
    var staction_id:String=""
    lateinit var pd: ProgressDialog
    private lateinit var model: SawpPaymentHistory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swap_staction_details)
        model = ViewModelProvider(this)[SawpPaymentHistory::class.java]

        pd = ProgressDialog(this)
        pd.setMessage("Loading...")

        staction_id= intent.getStringExtra("staction_id").toString()
        Log.d("gdyfghfgghhdjkz","$staction_id")

        makePayment(staction_id)
        bac.setOnClickListener {
            finish()
        }
        map1.setOnClickListener{
            var i = Intent(this,SwapStactionActivity::class.java)
            startActivity(i)
        }
        batterswap4.setOnClickListener{
            var i = Intent(this, BatterySwappingActivity::class.java)
            startActivity(i)
        }
        swap_history1.setOnClickListener {
            var i = Intent(this, SwapingHisttoryActivity::class.java)
            startActivity(i)
        }
        profile1.setOnClickListener{
            var i = Intent(this, ProfileActivity::class.java)
            startActivity(i)
        }
        dashboard1.setOnClickListener {
            var i = Intent(this, DashboardActivity::class.java)
            startActivity(i)
        }

        f1.setOnClickListener {
            f1.visibility=View.GONE
            b1.visibility=View.VISIBLE
            b2.visibility=View.GONE
            b3.visibility=View.GONE
            b4.visibility=View.GONE
            f2.visibility=View.VISIBLE
            f3.visibility=View.VISIBLE
            f4.visibility=View.VISIBLE

            //recyler
            first1.visibility=View.VISIBLE
            second1.visibility=View.GONE
            third1.visibility=View.GONE
            fourth1.visibility=View.GONE
        }
        f2.setOnClickListener {
            f2.visibility=View.GONE
            b2.visibility=View.VISIBLE
            b3.visibility=View.GONE
            b4.visibility=View.GONE
            b1.visibility=View.GONE
            f1.visibility=View.VISIBLE
            f3.visibility=View.VISIBLE
            f4.visibility=View.VISIBLE

            //recyler
            first1.visibility=View.GONE
            second1.visibility=View.VISIBLE
            third1.visibility=View.GONE
            fourth1.visibility=View.GONE
        }
        f3.setOnClickListener {
            f3.visibility=View.GONE
            b3.visibility=View.VISIBLE
            b2.visibility=View.GONE
            b4.visibility=View.GONE
            b1.visibility=View.GONE
            f1.visibility=View.VISIBLE
            f2.visibility=View.VISIBLE
            f4.visibility=View.VISIBLE

            //recyler
            first1.visibility=View.GONE
            second1.visibility=View.GONE
            third1.visibility=View.VISIBLE
            fourth1.visibility=View.GONE
        }
     f4.setOnClickListener {
            f4.visibility=View.GONE
            b4.visibility=View.VISIBLE
            b2.visibility=View.GONE
            b3.visibility=View.GONE
            b1.visibility=View.GONE
            f1.visibility=View.VISIBLE
            f2.visibility=View.VISIBLE
            f3.visibility=View.VISIBLE
         //recyler
         first1.visibility=View.GONE
         second1.visibility=View.GONE
         third1.visibility=View.GONE
         fourth1.visibility=View.VISIBLE
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
            agentname.text = userStatus.response.name+""
            agentnumber.text= userStatus.response.contact_number
            var data =userStatus.response.avail_battery
            Log.d("fdkzhfkjhszlkjfdojd","$data")
            if (data.isNotEmpty()) {
                battery.text = data[0].value+""
                battery_2.text = data[1].value+""
                battery_3.text = data[2].value+""
                battery_4.text = data[3].value+""

                val first = data[0].batteries
                val first_view =findViewById<RecyclerView>(R.id.first1)
                first_view.layoutManager = LinearLayoutManager(this)
                var adapter = FirstStactionAdapter(this, first!!)
                first_view.adapter = adapter

                val second = data[1].batteries
                val second_view =findViewById<RecyclerView>(R.id.second1)
                second_view.layoutManager = LinearLayoutManager(this)
                var adapter1 = SecondStactionAdapter(this, second!!)
                second_view.adapter = adapter1

                val third = data[2].batteries

                val third_view =findViewById<RecyclerView>(R.id.third1)
                third_view.layoutManager = LinearLayoutManager(this)
                var adapter2 = ThirdStactionAdapter(this, third!!)
                third_view.adapter = adapter2

                val fourth = data[3].batteries
                val four_view =findViewById<RecyclerView>(R.id.fourth1)
                four_view.layoutManager = LinearLayoutManager(this)
                var adapter3 = FourthStactionAdapter(this, fourth!!)
                four_view.adapter = adapter3


            }

        }else{
            if (pd != null)
                pd.dismiss()

        }
    }
}