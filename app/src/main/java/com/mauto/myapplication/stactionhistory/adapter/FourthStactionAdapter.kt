package com.mauto.myapplication.stactionhistory.adapter


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.mauto.myapplication.R
import com.mauto.myapplication.home.model.PaymentMethod
import com.mauto.myapplication.home.model.SwapLocationData
import com.mauto.myapplication.mapSwapingOnclick
import com.mauto.myapplication.profile.ProfileActivity
import com.mauto.myapplication.stactionhistory.StactionBatteryArrayResponse
import com.mauto.myapplication.utils.MAutoSharedPref
import com.mauto.myapplication.utils.PrefConstant
import kotlinx.android.synthetic.main.first_adapter.view.*
import kotlinx.android.synthetic.main.map_adapter.view.*
import kotlinx.android.synthetic.main.paytype_cell.view.*
import kotlinx.android.synthetic.main.paytype_cell.view.icon_type
import kotlinx.android.synthetic.main.paytype_cell.view.name_type
import kotlinx.android.synthetic.main.paytype_cell.view.userNumber


class FourthStactionAdapter(val ctx: Context, var data: List<StactionBatteryArrayResponse>
) :
    RecyclerView.Adapter<FourthStactionAdapter.ViewHolder>() {


    private var selectedItem = -1


    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FourthStactionAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.fourth_staction_adapter, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: FourthStactionAdapter.ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.bindItems(data!![position])








    }


    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return data!!.size
    }
    private fun openGoogleMaps(codeg:String) {
//        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?q=loc:$lat,$lng"))
//        context.startActivity(intent)
    }


    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name_type = itemView.name_type
        val persent = itemView.persent

        fun bindItems(model: StactionBatteryArrayResponse) {
            name_type.text = model.battery_no
            persent.text = model.soc+"%"



        }

    }


}