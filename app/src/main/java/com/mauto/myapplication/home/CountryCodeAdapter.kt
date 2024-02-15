package com.mauto.myapplication.home



import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mauto.myapplication.R


class CountryCodeAdapter(val context : Context, var countrymodedata: ArrayList<CountrySelectionModel>,  var mCustomOnClickListener: CountryCustomOnClickListener) : RecyclerView.Adapter<CountryCodeAdapter.ViewHolder>()
{
    override fun onBindViewHolder(p0: ViewHolder, p1: Int)
    {
        p0?.countryCode?.text = "(+"+countrymodedata[p1].countrycode+")"
        p0?.countryName?.text = countrymodedata[p1].countryname
        p0?.flagImage?.setImageBitmap(countrymodedata[p1].flag)


        // view clickable
        p0?.countryCode?.setOnClickListener { view -> mCustomOnClickListener.onItemClickListener(view, p0.adapterPosition , countrymodedata[p1].countrycode.toString(), countrymodedata[p1].countryshortname.toString(), countrymodedata[p1].countryname.toString()) }
        p0?.countryName?.setOnClickListener { view -> mCustomOnClickListener.onItemClickListener(view, p0.adapterPosition , countrymodedata[p1].countrycode.toString(), countrymodedata[p1].countryshortname.toString(), countrymodedata[p1].countryname.toString()) }
        p0?.flagImage?.setOnClickListener { view -> mCustomOnClickListener.onItemClickListener(view, p0.adapterPosition , countrymodedata[p1].countrycode.toString(), countrymodedata[p1].countryshortname.toString(), countrymodedata[p1].countryname.toString()) }
    }
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder
    {
        val v = LayoutInflater.from(p0?.context).inflate(R.layout.countryselectionadapter, p0, false)
        return ViewHolder(v)
    }
    override fun getItemCount(): Int
    {
        return countrymodedata.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val countryName: TextView? = itemView.findViewById<TextView>(R.id.countryName)
        val countryCode: TextView? = itemView.findViewById<TextView>(R.id.countryCode)
        val flagImage: ImageView? = itemView.findViewById<ImageView>(R.id.flagImage)
    }
    fun filterList(filteredCourseList: ArrayList<CountrySelectionModel>)
    {
        this.countrymodedata = filteredCourseList;
        notifyDataSetChanged();
    }
}
