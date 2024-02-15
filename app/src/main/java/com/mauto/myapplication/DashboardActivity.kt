package com.mauto.myapplication

import android.app.ProgressDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jjoe64.graphview.GraphView
import com.mauto.myapplication.batteryDetails.BatteryDetailsActivity
import com.mauto.myapplication.batteryscanning.BatterySwappingActivity
import com.mauto.myapplication.home.dashModel.DashboardResponce
import com.mauto.myapplication.login.livedata.LoginLiveData
import com.mauto.myapplication.profile.ProfileActivity
import com.mauto.myapplication.swapingHistory.SwapingHisttoryActivity
import com.mauto.myapplication.utils.MAutoApplication
import com.mauto.myapplication.utils.MAutoSharedPref
import com.mauto.myapplication.utils.PrefConstant
import com.mauto.myapplication.utils.ToastUtils

import kotlinx.android.synthetic.main.activity_dashboard.*
import java.text.SimpleDateFormat
import java.util.*


class DashboardActivity : AppCompatActivity() {
    var graphView: GraphView? = null
    lateinit var pd: ProgressDialog
    private lateinit var model: LoginLiveData
    var first:String="75%-100%"
    var second:String="50%-75%"
    var third:String="25%-50%"
    var fourth:String="0%-25%"
    val fiest_array=""

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        var map = findViewById<ImageView>(R.id.map)
        var battery1 = findViewById<CardView>(R.id.battery1)
        var batterswap3 = findViewById<ImageView>(R.id.batterswap3)
        model = ViewModelProvider(this)[LoginLiveData::class.java]
        pd = ProgressDialog(this@DashboardActivity)
        pd.setMessage("Loading...")
        logoutFromApp()

        val stactin_id =
            MAutoSharedPref.getAppPrefs(this).getStringValue(PrefConstant.MAT_STACTION_ID);

        polam.setOnClickListener{
            var i = Intent(this@DashboardActivity, SwapingHisttoryActivity::class.java)
            startActivity(i)

        }

        map.setOnClickListener{
            var i = Intent(this@DashboardActivity,SwapStactionActivity::class.java)
            startActivity(i)
        }

        batterswap3.setOnClickListener{
            var i = Intent(this@DashboardActivity, BatterySwappingActivity::class.java)
            startActivity(i)
        }
        battery1.setOnClickListener{
            var i = Intent(this@DashboardActivity,BatteryDetailsActivity::class.java)
            i.putExtra("mapimage", first)
            i.putExtra("stactin_id",stactin_id)
            startActivity(i)
        }
        battery2.setOnClickListener{
            var i = Intent(this@DashboardActivity,BatteryDetailsActivity::class.java)
            i.putExtra("mapimage", second)
            i.putExtra("stactin_id",stactin_id)
            startActivity(i)
        }
        battery3.setOnClickListener{
            var i = Intent(this@DashboardActivity,BatteryDetailsActivity::class.java)
            i.putExtra("mapimage", third)
            i.putExtra("stactin_id",stactin_id)
            startActivity(i)
        }
        battery4.setOnClickListener{
            var i = Intent(this@DashboardActivity,BatteryDetailsActivity::class.java)
            i.putExtra("mapimage", fourth)
            i.putExtra("stactin_id",stactin_id)
            startActivity(i)
        }

        swap_history.setOnClickListener {
            var i = Intent(this@DashboardActivity, SwapingHisttoryActivity::class.java)
            startActivity(i)
        }

        profile.setOnClickListener{
            var i = Intent(this@DashboardActivity, ProfileActivity::class.java)
            startActivity(i)
        }
    }


    private fun logoutFromApp() {
        try {
            val imm: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        pd.show()
//        Calling API from Observer

        if (model == null)
            model = ViewModelProvider(this)[LoginLiveData::class.java]

        model.dash()
            ?.observe(this, Observer { this.setStatus(it) })
//

    }



    private fun setStatus(userStatus: DashboardResponce) {

        if (userStatus!!.status.equals("1")) {
            if (pd != null)
                pd.dismiss()




            stactin_name.text = userStatus.response.data.station + " "
            date.text = userStatus.response.data.date + " "
            swap_count.text=userStatus.response.data.today_stats.no_of_swaps + " "
            energy_count.text=userStatus.response.data.today_stats.total_energy + "KW"
            time.text=userStatus.response.data.today_stats.time_taken + " "

            val motnth=userStatus.response.data.monthlyStats
            Log.d("jhghj","$motnth")
            val liteltime =userStatus.response.data.in_time + " "
//            val monthly= userStatus.response.data.monthly_stats
//            Log.d("gyskjfhlkJSDGlkjdjkghb","$monthly")


            var data =userStatus.response.data.avail_battery
            if (data.isNotEmpty()) {
                // Set the first element to the TextView
                battery_count.text = data[0].value+""
                battery_count2.text = data[1].value+""
                battery_count3.text = data[2].value+""
                battery_count4.text = data[3].value+""
//                fiest_array=data.


            }

            timeConverter(liteltime)






        }
        else {
            if (pd != null)
                pd.dismiss()
            MAutoSharedPref.getAppPrefs(
                MAutoApplication.getInstance().applicationContext
            ).clearAllSharedPrefs()



        }

    }

    private fun timeConverter(liteltime: String): String {
        try {
            // Trim the input string to remove leading/trailing whitespace
            val trimmedTimestamp = liteltime.trim()

            // Convert the trimmed string to a Long
            val timestamp = trimmedTimestamp.toLong()

            // Convert the timestamp to a Date and format it
            val date = Date(timestamp * 1000)
            val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
            val  formattedTime = sdf.format(date)
            time_enter.setText("In: "+formattedTime)
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