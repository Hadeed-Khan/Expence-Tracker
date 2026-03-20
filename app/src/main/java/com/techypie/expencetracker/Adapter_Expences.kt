package com.techypie.expencetracker

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter_Expences (context: Context, expence : MutableList<Expences>) : RecyclerView.Adapter<Adapter_Expences.ViewHolder>(){

    var context = context
    var expencelist = expence
    init {
        this.context = context
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter_Expences.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.expence_item,parent,false))
    }

    override fun onBindViewHolder(holder: Adapter_Expences.ViewHolder, position: Int) {
       // val parts = expencelist[position].date.split("/")

        holder.id.text = "${position+1}"
        holder.date.text = expencelist[position].date
        holder.itemName.text = expencelist[position].itemName
        holder.itemPrice.text = expencelist[position].itemPrice
        holder.itemQuantity.text = expencelist[position].itemQuantity
        holder.itemTotalPrice.text = expencelist[position].totalPriceItem

        holder.itemView.setOnClickListener {
            context.startActivity(Intent(context, Update_item_Activity::class.java)
                .putExtra("item",expencelist[position]))
        }
    }

    override fun getItemCount(): Int {
       return expencelist.size
    }
    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var id = itemView.findViewById<TextView>(R.id.sNo)
        var date = itemView.findViewById<TextView>(R.id.date)
        var itemName = itemView.findViewById<TextView>(R.id.itemName)
        var itemPrice = itemView.findViewById<TextView>(R.id.itemPrice)
        var itemQuantity = itemView.findViewById<TextView>(R.id.itemQuantity)
        var itemTotalPrice = itemView.findViewById<TextView>(R.id.totalItemPrice)
    }
}