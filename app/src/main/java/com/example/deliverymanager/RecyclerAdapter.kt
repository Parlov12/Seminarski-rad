package com.example.deliverymanager

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(var shop_name : List<String>, var phone_number : List<String>, var adress : List<String>, var check : List<Boolean>, var context : Context, var data : SharedPreferences, var dataManager : DataManager) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){

    var editor : SharedPreferences.Editor = data.edit()


    // view - prikaz koji se povezuje s elementima modela prikup_layout.xml
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskName : TextView = itemView.findViewById(R.id.shop_name)
        val phNumber : TextView = itemView.findViewById(R.id.contact_number)
        val location : TextView = itemView.findViewById(R.id.location)
        val statusCheck : CheckBox = itemView.findViewById(R.id.complete_check)
        val completed : TextView = itemView.findViewById(R.id.completed_cover)
        val delete : TextView = itemView.findViewById(R.id.time_limit)

        init {
            itemView.setOnClickListener{ v : View ->
                val position : Int = adapterPosition
            }
        }

    }

    // funkcija koja omogucuje prikaz odredenog elementa u trenutnom activity-u - LayoutInflater
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v : View = LayoutInflater.from(parent.context).inflate(R.layout.prikup_layout, parent, false)
        return ViewHolder(v)
    }

    // stvaranje RecyclerAdapter objekta, svakom elementu se pridruzuje vrijednost iz lista koje su ucitane kroz argumente objekta
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.taskName.text = shop_name[position]
        holder.phNumber.text = phone_number[position]
        holder.location.text = adress[position]
        holder.statusCheck.isChecked = check[position]
        holder.delete.text = "X"
        holder.completed.visibility = if (data.getBoolean("STATUS_CHECK$position", false)){
            View.VISIBLE
        } else{
            View.INVISIBLE
        }

        // prikazivanje sivog ekrana ovisno je li
        holder.statusCheck.setOnClickListener { holder.completed.visibility = if (holder.completed.visibility == View.VISIBLE){
            View.INVISIBLE
        } else{
            View.VISIBLE
        }

        editor.putBoolean("STATUS_CHECK$position", !data.getBoolean("STATUS_CHECK$position",false))
        editor.commit()

            dataManager.refreshTempList()
        } // click event


        // otvaranje telefona s brojem pridruzenim odredenom zadatku
        holder.phNumber.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.data = Uri.parse("tel:"+holder.phNumber.text.toString())
            context.startActivity(intent)
        }

        // brisanej jednog elementa
        holder.delete.setOnClickListener {
            dataManager.removeAnElement(position)
            update(dataManager.shopName, dataManager.number, dataManager.location, dataManager.statusCheck)
        }


    }

    override fun getItemCount(): Int {
        return shop_name.size
    }


    fun update(ime: List<String>, broj: List<String>, lokacija : List<String>, provjera : List<Boolean> ){
        shop_name = ime
        phone_number = broj
        adress = lokacija
        check = provjera
        this!!.notifyDataSetChanged()
    }



}


