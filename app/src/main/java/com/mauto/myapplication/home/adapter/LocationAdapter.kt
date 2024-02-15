package com.mauto.myapplication.home.adapter


import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mauto.myapplication.R
import com.mauto.myapplication.home.model.PaymentMethod
import com.mauto.myapplication.home.model.SwapLocationData
import com.mauto.myapplication.utils.MAutoSharedPref
import com.mauto.myapplication.utils.PrefConstant
import kotlinx.android.synthetic.main.locatio_adapter.view.*
import kotlinx.android.synthetic.main.paytype_cell.view.*
import kotlinx.android.synthetic.main.paytype_cell.view.icon_type
import kotlinx.android.synthetic.main.paytype_cell.view.lay_view
import kotlinx.android.synthetic.main.paytype_cell.view.name_type
import kotlinx.android.synthetic.main.paytype_cell.view.userNumber
import kotlinx.android.synthetic.main.swaping_history_adapter.view.*


class LocationAdapter(val ctx: Context, var data: List<SwapLocationData>) :
    RecyclerView.Adapter<LocationAdapter.ViewHolder>() {


    private var selectedItem = -1


    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.locatio_adapter, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: LocationAdapter.ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.bindItems(data!![position])



//        holder.lay_view.setBackgroundColor(ctx.resources.getColor(R.color.white))
        holder.select_img.setBackgroundResource(R.drawable.ic_unselect)

//        holder.mobileNo.visibility = View.GONE

        if (selectedItem === position) {
//            holder.lay_view.setBackgroundColor(ctx.resources.getColor(R.color.color_pay_sel))
            holder.select_img.setBackgroundResource(R.drawable.ic_select)

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




        holder.itemView.setOnClickListener {
            val previousItem: Int = selectedItem
            selectedItem = position
            notifyItemChanged(previousItem)
            notifyItemChanged(position)
            MAutoSharedPref.getAppPrefs(ctx)
                .saveStringValue(PrefConstant.MAT_PAY_METHOD, data!![position].name)

            MAutoSharedPref.getAppPrefs(ctx)
                .savestactionid(PrefConstant.MAT_STACTION_ID, data!![position].id)

            Log.d("","")
        }


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
        val lay_view = itemView.lay_view
        val select_img= itemView.select_img1
        fun bindItems(model: SwapLocationData) {
            name.text = model.name

            if (model.code.equals("tmoney"))
                icon.setBackgroundResource(R.drawable.ic_icon_pay3)
            if (model.code.equals("moovbn"))
                icon.setBackgroundResource(R.drawable.ic_icon_pay1)
            if (model.code.equals("mtnmomo"))
                icon.setBackgroundResource(R.drawable.ic_icon_pay2)

        }

    }


}