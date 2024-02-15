package com.mauto.myapplication.home.adapter


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
import com.mauto.myapplication.utils.MAutoSharedPref
import com.mauto.myapplication.utils.PrefConstant
import kotlinx.android.synthetic.main.map_adapter.view.*
import kotlinx.android.synthetic.main.paytype_cell.view.*
import kotlinx.android.synthetic.main.paytype_cell.view.icon_type
import kotlinx.android.synthetic.main.paytype_cell.view.name_type
import kotlinx.android.synthetic.main.paytype_cell.view.userNumber


class MapAdapter(val ctx: Context, var data: List<SwapLocationData>
//, internal var mCustomOnClickListener: mapSwapingOnclick
) :
    RecyclerView.Adapter<MapAdapter.ViewHolder>() {


    private var selectedItem = -1


    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.map_adapter, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: MapAdapter.ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.bindItems(data!![position])



//        holder.lay_view.setBackgroundColor(ctx.resources.getColor(R.color.white))

//        holder.mobileNo.visibility = View.GONE
        holder.overalll

        if (selectedItem === position) {
//            holder.lay_view.setBackgroundColor(ctx.resources.getColor(R.color.color_pay_sel))

//            holder.mobileNo.visibility = View.VISIBLE

        }


        holder.overalll.setOnClickListener {
            val previousItem: Int = selectedItem
            selectedItem = position
            notifyItemChanged(previousItem)
            notifyItemChanged(position)
            MAutoSharedPref.getAppPrefs(ctx).saveStringValue(PrefConstant.MAT_PAY_METHOD, data!![position].code);
//            mCustomOnClickListener.onItemClickListener(position)

        }


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

        val icon = itemView.icon_type
        val name = itemView.name_type
        val mobileNo = itemView.userNumber
        val stactin = itemView.stactin
        val battery_count = itemView.battery_count
        val active = itemView.active
        val unavilble = itemView.unavilble
         val darectin = itemView.darectin
        val overalll = itemView.overalll
//        val lay_view = itemView.lay_view
        fun bindItems(model: SwapLocationData) {
            name.text = model.name
            val code = model.code
            val geolocation = model.geolocation
            val status = model.status

    val available_batteries = model.available_batteries
    stactin.setText(code+","+geolocation)

    battery_count.setText(available_batteries+" Batteries")
    if (available_batteries.equals("0")){
        active.visibility=View.GONE
        battery_count.visibility=View.GONE
        unavilble.visibility=View.VISIBLE

    }else{
        active.visibility=View.VISIBLE
        battery_count.visibility=View.VISIBLE

    }
//    darectin.setOnClickListener {
//        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?q=loc:" + lat+ "," +lng))
//        startActivity(intent)
//    }




        }

    }


}