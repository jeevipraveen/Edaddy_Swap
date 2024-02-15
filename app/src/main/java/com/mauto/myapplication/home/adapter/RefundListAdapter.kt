package com.mauto.myapplication.home.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mauto.myapplication.R
import com.mauto.myapplication.home.model.TransactionData
import kotlinx.android.synthetic.main.item_cancel_payment.view.*


class RefundListAdapter(val ctx: Context, var data: List<TransactionData>) :
    RecyclerView.Adapter<RefundListAdapter.ViewHolder>() {


    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RefundListAdapter.ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_cancel_payment, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: RefundListAdapter.ViewHolder, position: Int) {
        holder.bindItems(data!![position])


    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return data!!.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(model: TransactionData) {
            val name = itemView.txt_cell1
            val amount = itemView.txt_cell2
            val id = itemView.txt_cell4
            val date = itemView.txt_cell5


            name.text = model.customerName
            amount.text = model.txnAmount
            id.text = model.gateway
            date.text = model.txnDate


        }

    }


}