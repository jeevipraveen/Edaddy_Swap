package com.mauto.myapplication.adapter



import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.mauto.myapplication.R
import com.mauto.myapplication.dearcCustomOnClickListener
import com.mauto.myapplication.home.model.SwapLocationData
import com.mauto.myapplication.rideCustomOnClickListener
import kotlinx.android.synthetic.main.map_adapter.view.*
import kotlinx.android.synthetic.main.paytype_cell.view.*
import kotlinx.android.synthetic.main.paytype_cell.view.icon_type
import kotlinx.android.synthetic.main.paytype_cell.view.name_type
import kotlinx.android.synthetic.main.paytype_cell.view.userNumber
import java.util.ArrayList


class MapAdapter2(
    val context: Context, var countrymodedata: ArrayList<SwapLocationData>,
    internal var mCustomOnClickListener: rideCustomOnClickListener,
    internal var mCustomDeractOnClickListener: dearcCustomOnClickListener
) :
    RecyclerView.Adapter<MapAdapter2.ViewHolder>() {

    override fun onBindViewHolder(p0: ViewHolder, p1: Int)
    {
        p0?.name?.text = countrymodedata[p1].name

        val code = countrymodedata[p1].code
        val geolocation =countrymodedata[p1].geolocation
        val status =countrymodedata[p1].status

        val lat = countrymodedata[p1].lat
        val lng = countrymodedata[p1].lng
        val latlng = lat+lng
        val available_batteries = countrymodedata[p1].available_batteries
        p0?.stactin?.setText(code+","+geolocation)

        p0?.battery_count?.setText(available_batteries+" Batteries")
        if (available_batteries.equals("0")){
           p0?. active?.visibility=View.GONE
            p0?.battery_count?.visibility=View.GONE
            p0?. unavilble?.visibility=View.VISIBLE

        }else{
            p0?.active?.visibility=View.VISIBLE
            p0?.battery_count?.visibility=View.VISIBLE

        }
        p0?.itemView!!.setOnClickListener{
            countrymodedata[p1].id?.let { id ->
                mCustomOnClickListener.onItemClickListener(p0.itemView, p1, id)
            }
        }
        p0?.darectin!!.setOnClickListener{
            countrymodedata[p1].id?.let { id ->

                mCustomDeractOnClickListener.onItemClickListener(p0.itemView, p1,latlng)
            }
        }


    }
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0?.context).inflate(R.layout.map_adapter, p0, false)


        return ViewHolder(v)
    }
    override fun getItemCount(): Int
    {
        return countrymodedata.size
    }
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
    }


    }