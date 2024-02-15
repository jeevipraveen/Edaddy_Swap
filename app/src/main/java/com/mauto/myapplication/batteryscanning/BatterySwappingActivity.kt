package com.mauto.myapplication.batteryscanning

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mauto.myapplication.R
import com.mauto.myapplication.batteryscanning.livedatda.BatteryLiveData
import com.mauto.myapplication.batteryscanning.model.*
import com.mauto.myapplication.swappayment.SwappingPaymentActivity
import com.mauto.myapplication.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_battery_swapping.*

class BatterySwappingActivity : AppCompatActivity() {
    lateinit var pd: ProgressDialog
    private val COUNTRYCODEREQUESTS = 117
    var barcode = ""
    var vehicle_id:String="1"
    var oldbattery_id:String="2"
    var newbattery_id:String="3"
    var qrcodetype: String = ""
    lateinit var model :BatteryLiveData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_battery_swapping)
//7200638987
        model = ViewModelProvider(this)[BatteryLiveData::class.java]
        pd = ProgressDialog(this)
        pd.setMessage("Loading...")

        var payment_t = findViewById<CardView>(R.id.payment_t)

        bac.setOnClickListener {
            finish()
        }
        vehiclescan.setOnClickListener {
            val mServiceIntent: Intent = Intent(this, ScannedBarcodeActivity::class.java)
            startActivityForResult(mServiceIntent, COUNTRYCODEREQUESTS)
            qrcodetype = "vehicle"

        }
        vehiclescan1.setOnClickListener {
            val mServiceIntent: Intent = Intent(this, ScannedBarcodeActivity::class.java)
            startActivityForResult(mServiceIntent, COUNTRYCODEREQUESTS)
            qrcodetype = "vehicle"

        }
        activeoldbatteryescan.setOnClickListener {
            val mServiceIntent: Intent = Intent(this, ScannedBarcodeActivity::class.java)
            startActivityForResult(mServiceIntent, COUNTRYCODEREQUESTS)
            qrcodetype = "old"
        }
        activeoldbatteryescan1.setOnClickListener {
            val mServiceIntent: Intent = Intent(this, ScannedBarcodeActivity::class.java)
            startActivityForResult(mServiceIntent, COUNTRYCODEREQUESTS)
            qrcodetype = "old"
        }
        activ_new_battery_scan.setOnClickListener {
            val mServiceIntent: Intent = Intent(this, ScannedBarcodeActivity::class.java)
            startActivityForResult(mServiceIntent, COUNTRYCODEREQUESTS)
            qrcodetype = "new"
        }
        activ_new_battery_scan1.setOnClickListener {
            val mServiceIntent: Intent = Intent(this, ScannedBarcodeActivity::class.java)
            startActivityForResult(mServiceIntent, COUNTRYCODEREQUESTS)
            qrcodetype = "new"
        }




        payment_t.setOnClickListener {
        var i = Intent(this@BatterySwappingActivity,SwappingPaymentActivity::class.java)
            i.putExtra("newbattery_id", newbattery_id)
            i.putExtra("vehicle_id", vehicle_id)
            i.putExtra("oldbattery_id", oldbattery_id)

        startActivity(i)
        }
    }
    private fun vehicleQR(barcode: String) {
        try {
            val imm: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        var request: VheicleRequest = VheicleRequest(barcode)


        if(model==null)
            model = ViewModelProvider(this)[BatteryLiveData::class.java]
        pd.show()
        //Calling API from Observer
        model.loginlocation(request)
            ?.observe(this, Observer { this.setOTPStatus(it) })


    }


    private fun setOTPStatus(userStatus: VehicleResponse) {
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

    private fun oldQR(barcode: String) {
        try {
            val imm: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }


        var request: OldBatteryRequest = OldBatteryRequest(barcode,vehicle_id)


        if(model==null)
            model = ViewModelProvider(this)[BatteryLiveData::class.java]
        pd.show()
        //Calling API from Observer
        model.oldbattery(request)
            ?.observe(this, Observer { this.oldQrStatus(it) })


    }


    private fun newQR(barcode: String) {
        try {
            val imm: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }


        var request: NewBatteryRequest = NewBatteryRequest(barcode,vehicle_id,oldbattery_id)


        if(model==null)
            model = ViewModelProvider(this)[BatteryLiveData::class.java]
        pd.show()
        //Calling API from Observer
        model.newbattery(request)
            ?.observe(this, Observer { this.newQrStatus(it) })


    }
    private fun oldQrStatus(userStatus: OldBatteryResponse) {
        if (userStatus!!.status.equals("1")) {
            if (pd != null)
                pd.dismiss()

            ToastUtils.showToast(applicationContext, "okok")
            activeoldbattery_result.visibility = View.VISIBLE
            activ_new_battery_scan.visibility = View.VISIBLE
            vehicle_result.visibility = View.VISIBLE
            vehicleOld.visibility = View.GONE
            vehiclescan.visibility = View.GONE
            vehicleNew.visibility = View.GONE
            activeoldbatteryescan.visibility = View.GONE

        }else{
            if (pd != null)
                pd.dismiss()
            activeoldbattery_result.visibility = View.VISIBLE
            activ_new_battery_scan.visibility = View.VISIBLE
            vehicle_result.visibility = View.VISIBLE
            vehicleOld.visibility = View.GONE
            vehiclescan.visibility = View.GONE
            vehicleNew.visibility = View.GONE
            activeoldbatteryescan.visibility = View.GONE

            ToastUtils.showToast(applicationContext, "intha user ellayam")
        }

    } private fun newQrStatus(userStatus: NewBatteryResponse) {
        if (userStatus!!.status.equals("1")) {
            if (pd != null)
                pd.dismiss()

            ToastUtils.showToast(applicationContext, "okok")
            payment_t.visibility=View.VISIBLE
            activeoldbattery_result.visibility = View.VISIBLE
            activenewbattery_result.visibility = View.VISIBLE
            activ_new_battery_scan.visibility = View.VISIBLE
            vehicle_result.visibility = View.VISIBLE
            vehicleOld.visibility = View.GONE
            vehiclescan.visibility = View.GONE
            vehicleNew.visibility = View.GONE
            activeoldbatteryescan.visibility = View.GONE
            activ_new_battery_scan.visibility = View.GONE

        }else{
            if (pd != null)
                pd.dismiss()
            payment_t.visibility=View.VISIBLE
            activeoldbattery_result.visibility = View.VISIBLE
            activenewbattery_result.visibility = View.VISIBLE
            activ_new_battery_scan.visibility = View.GONE
            vehicle_result.visibility = View.VISIBLE
            vehicleOld.visibility = View.GONE
            vehicleNew.visibility = View.GONE
            vehiclescan.visibility = View.GONE
            activeoldbatteryescan.visibility = View.GONE


            ToastUtils.showToast(applicationContext, "intha user ellayam")
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("check_battert", resultCode.toString())
        if (resultCode == Activity.RESULT_OK) {
            barcode = data?.getStringExtra("code").toString()


            if (qrcodetype.equals("vehicle")) {
                vehicleQR(barcode)
                ToastUtils.showToast(applicationContext, barcode)


            } else if (qrcodetype.equals("old")){

                     oldQR(barcode)
                    ToastUtils.showToast(applicationContext, barcode)
            }else if (qrcodetype.equals("new")){

                    newQR(barcode)
                ToastUtils.showToast(applicationContext, barcode)
            }

            else {



            }




        }
    }

}