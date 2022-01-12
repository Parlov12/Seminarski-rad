package com.example.deliverymanager

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast

class DataManager (data : SharedPreferences){
    var shopName : MutableList<String> = mutableListOf()
    var number : MutableList<String> = mutableListOf()
    var location : MutableList<String> = mutableListOf()
    var statusCheck : MutableList<Boolean> = mutableListOf()
    var timeLimit : MutableList<String> = mutableListOf()
    var refData : SharedPreferences = data

    var counter : Int = 0
    var editor: SharedPreferences.Editor = data.edit()
    var i = 0

    var message : Array<String> = arrayOf("", "", "", "")

    init {
        counter = data.getInt("COUNTER", 0)

        if(counter == 0){
            // do nothing
        }
        else{
            for (i in 0..counter-1){
                shopName.add(data.getString("NAME$i", "NULL")!!)
                number.add(data.getString("NUMBER$i","NULL")!!)
                location.add(data.getString("LOCATION$i","NULL")!!)
                statusCheck.add(data.getBoolean("STATUS_CHECK$i",false)!!)
                timeLimit.add(data.getString("TIME_LIMIT$i", "NULL")!!)
            }
        }
    }

    fun addData(name : String, broj : String, lokacija : String, time : String, check : Boolean) {
        shopName.add(name)
        number.add(broj)
        location.add(lokacija)
        statusCheck.add(check)
        timeLimit.add(time)

        editor.putString("NAME$counter",shopName[counter])
        editor.putString("NUMBER$counter",number[counter])
        editor.putString("LOCATION$counter",location[counter])
        editor.putBoolean("STATUS_CHECK$counter",statusCheck[counter])
        editor.putString("TIME_LIMIT$counter", timeLimit[counter])


        counter += 1
        editor.putInt("COUNTER", counter)
        editor.commit()

        // check if everything is correctly written
        message[0] = refData.getString("NAME${counter-1}", "NULL")!!
        message[1] = refData.getString("NUMBER${counter-1}", "NULL")!!
        message[2] = refData.getString("LOCATION${counter-1}", "NULL")!!
        message[3] = refData.getString("TIME_LIMIT${counter-1}", "NULL")!!

    }

    fun kill() {
        editor.clear()
        editor.apply()
        counter = 0
        shopName.clear()
        number.clear()
        location.clear()
        statusCheck.clear()
        editor.putInt("COUNTER", 0)
        editor.commit()
    }





}