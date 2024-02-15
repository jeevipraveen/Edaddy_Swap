package com.mauto.myapplication.swapingHistory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mauto.myapplication.DashboardActivity
import com.mauto.myapplication.R
import com.mauto.myapplication.SwapStactionActivity
import com.mauto.myapplication.batteryscanning.BatterySwappingActivity
import com.mauto.myapplication.profile.ProfileActivity
import kotlinx.android.synthetic.main.activity_swap_historry_array.*
import kotlinx.android.synthetic.main.activity_swap_historry_array.batterswap3
import kotlinx.android.synthetic.main.activity_swap_historry_array.dashboard
import kotlinx.android.synthetic.main.activity_swap_historry_array.map
import kotlinx.android.synthetic.main.activity_swap_historry_array.profile
import kotlinx.android.synthetic.main.activity_swap_historry_array.swap_history
import kotlinx.android.synthetic.main.activity_swaping_histtory.*
import java.text.SimpleDateFormat
import java.util.*

class SwapHistorryArrayActivity : AppCompatActivity() {
    var id:String=""
    var timestamp:String=""
    var vehicle_number:String=""
    var station:String=""
    var status:String=""
    var amount:String=""
    var cycle_distance:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swap_historry_array)

        id= intent.getStringExtra("id").toString()
        timestamp=intent.getStringExtra("timestamp").toString()
        vehicle_number=intent.getStringExtra("vehicle_number").toString()
        station=intent.getStringExtra("station").toString()
        status=intent.getStringExtra("status").toString()
        amount=intent.getStringExtra("amount").toString()
        cycle_distance=intent.getStringExtra("cycle_distance").toString()
        timeConverter(timestamp)
        nuber.setText(vehicle_number)

        money.setText("₹"+amount)

//        date.setText()
        complet.setText(status)

        staction.setText(station)

        kmg.setText(cycle_distance+"")

        back.setOnClickListener {
            finish()
        }

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
    fun timeConverter(timestamp: String): String {
        try {
            // Trim the input string to remove leading/trailing whitespace
            val trimmedTimestamp = timestamp.trim()

            // Convert the trimmed string to a Long
            val timestamp = trimmedTimestamp.toLong()

            // Convert the timestamp to a Date and format it
            val date = Date(timestamp * 1000)
            val sdf = SimpleDateFormat("yyyy-MM-dd  hh:mm a", Locale.getDefault())
            val  formattedTime = sdf.format(date)
            date_jj.setText(formattedTime)
            return sdf.format(date)

        } catch (e: NumberFormatException) {
            // Handle exception if liteltime is not a valid Long
            e.printStackTrace()
            return "Invalid timestamp format"
        } catch (e: Exception) {
            // Handle other exceptions
            e.printStackTrace()
            return "Error formatting time"
        }
    }
}