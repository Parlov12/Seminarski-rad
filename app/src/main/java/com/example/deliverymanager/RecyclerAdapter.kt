package com.example.deliverymanager

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter( private var shop_name : List<String>, private var phone_number : List<String>, private var adress : List<String>, private var time_limit : List<String>,private var check : List<Boolean>, var context : Context) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val shopName : TextView = itemView.findViewById(R.id.shop_name)
        val phoneNumber : TextView = itemView.findViewById(R.id.contact_number)
        val location : TextView = itemView.findViewById(R.id.location)
        val statusCheck : CheckBox = itemView.findViewById(R.id.complete_check)
        val timeLimit : TextView = itemView.findViewById(R.id.time_limit)

        init {
            itemView.setOnClickListener{ v : View ->
                val position : Int = adapterPosition
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v : View = LayoutInflater.from(parent.context).inflate(R.layout.prikup_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.shopName.text = shop_name[position]
        holder.phoneNumber.text = phone_number[position]
        holder.location.text = adress[position]
        holder.statusCheck.isChecked = check[position]
        holder.timeLimit.text = time_limit[position]

        holder.statusCheck.setOnClickListener {holder.statusCheck.isChecked = check[position]} // click event


        holder.phoneNumber.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.data = Uri.parse(holder.phoneNumber.text.toString())
            context.startActivity(intent)
        }


    }



    override fun getItemCount(): Int {
        return shop_name.size
    }




    fun update(ime: List<String>, broj: List<String>, lokacija : List<String>, timeLim : List<String>, provjera : List<Boolean> ){
        shop_name = ime
        phone_number = broj
        adress = lokacija
        check = provjera
        time_limit = timeLim
        this!!.notifyDataSetChanged()
    }


}


