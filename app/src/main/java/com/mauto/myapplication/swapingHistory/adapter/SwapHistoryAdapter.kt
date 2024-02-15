package com.mauto.myapplication.swapingHistory.adapter


import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mauto.myapplication.R
import com.mauto.myapplication.swapingHistory.model.SwapingArrayResponse
import com.mauto.myapplication.utils.MAutoSharedPref
import com.mauto.myapplication.utils.PrefConstant
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.paytype_cell.view.*
import kotlinx.android.synthetic.main.paytype_cell.view.icon_type
import kotlinx.android.synthetic.main.paytype_cell.view.name_type
import kotlinx.android.synthetic.main.paytype_cell.view.userNumber
import kotlinx.android.synthetic.main.swaping_history_adapter.view.*
import java.text.SimpleDateFormat
import java.util.*


class SwapHistoryAdapter(val ctx: Context, var data: List<SwapingArrayResponse>) :
    RecyclerView.Adapter<SwapHistoryAdapter.ViewHolder>() {


    private var selectedItem = -1


    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SwapHistoryAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.swaping_history_adapter, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: SwapHistoryAdapter.ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.bindItems(data!![position])



//        holder.lay_view.setBackgroundColor(ctx.resources.getColor(R.color.white))

//        holder.mobileNo.visibility = View.GONE

        if (selectedItem === position) {
//            holder.lay_view.setBackgroundColor(ctx.resources.getColor(R.color.color_pay_sel))

//            holder.mobileNo.visibility = View.VISIBLE

        }

        holder.mobileNo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                try {
                    if (editable.toString().isNotEmpty()) {

                        MAutoSharedPref.getAppPrefs(ctx)
                            .saveStringValue(PrefConstant.MAT_PAY_MOBILE, editable.toString());
                    }

                } catch (nfe: Exception) { //or whatever exception you get
                    //do some handling if you need to
                }

            }
        })





    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return data!!.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val icon = itemView.icon_type
        val name = itemView.name_type
        val mobileNo = itemView.userNumber
        val date_time = itemView.date_time
        val count = itemView.count
        val complet = itemView.complet
        val stactin_name = itemView.stactin_name
//        val lay_view = itemView.lay_view
        fun bindItems(model: SwapingArrayResponse) {
            name.text = model.vehicle_number
             val result =  model.amount
            val station = model.station

            val cycle_distance = model.cycle_distance
             stactin_name.setText(station+" . "+cycle_distance+".")
            complet.text=model.status
             count.setText("₹"+result)
            val time = model.timestamp
            if (time != null) {
                timeConverter(time)
            }
//            ToastUtils.showToast(this, "intha user ellayam")

//            if (model.code.equals("tmoney"))
//                icon.setBackgroundResource(R.drawable.ic_icon_pay3)
//            if (model.code.equals("moovbn"))
//                icon.setBackgroundResource(R.drawable.ic_icon_pay1)
//            if (model.code.equals("mtnmomo"))
//                icon.setBackgroundResource(R.drawable.ic_icon_pay2)

        }

        fun timeConverter(time: String): String {
            try {
                // Trim the input string to remove leading/trailing whitespace
                val trimmedTimestamp = time.trim()

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


    }


}