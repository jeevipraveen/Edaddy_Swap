package com.mauto.myapplication.swapingHistory.adapter



import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.mauto.myapplication.R
import com.mauto.myapplication.home.model.SwapLocationData
import com.mauto.myapplication.rideCustomOnClickListener
import com.mauto.myapplication.swapingHistory.model.SwapingArrayResponse
import kotlinx.android.synthetic.main.map_adapter.view.*
import kotlinx.android.synthetic.main.paytype_cell.view.*
import kotlinx.android.synthetic.main.paytype_cell.view.icon_type
import kotlinx.android.synthetic.main.paytype_cell.view.name_type
import kotlinx.android.synthetic.main.paytype_cell.view.userNumber
import kotlinx.android.synthetic.main.swaping_history_adapter.view.*
import java.text.SimpleDateFormat
import java.util.*


class SwapingHistoryAdapter2(val context: Context, var countrymodedata: ArrayList<SwapingArrayResponse>,
                             internal var mCustomOnClickListener: rideCustomOnClickListener
) :
    RecyclerView.Adapter<SwapingHistoryAdapter2.ViewHolder>() {

    override fun onBindViewHolder(p0: ViewHolder, p1: Int)
    {

        val status=countrymodedata[p1].status
        val cycle_distance = countrymodedata[p1].cycle_distance
        val station = countrymodedata[p1].station
        val vehicle_number = countrymodedata[p1].vehicle_number
        val timestamp =countrymodedata[p1].timestamp
        val amount =countrymodedata[p1].amount
        p0?.timeConverter(timestamp!!)
        p0?.complet?.setText(status)
        p0?.v_number?.setText(vehicle_number)
        p0?.count?.setText("₹"+amount)
        p0?.stactin_name?.setText(station+" . "+cycle_distance+".")
        p0?.itemView!!.setOnClickListener{
            countrymodedata[p1].id?.let { id ->
                mCustomOnClickListener.onItemClickListener(p0.itemView, p1, id)
            }
        }


    }
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0?.context).inflate(R.layout.swaping_history_adapter2, p0, false)


        return ViewHolder(v)
    }
    override fun getItemCount(): Int
    {
        return countrymodedata.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
                date_time.setText(formattedTime)
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

        val v_number = itemView.name_type
        val count = itemView.count
        val complet = itemView.complet
        val stactin_name = itemView.stactin_name
        val date_time = itemView.date_time
    }


    }