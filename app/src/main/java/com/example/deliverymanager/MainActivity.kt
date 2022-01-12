package com.example.deliverymanager

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    private var shopName = mutableListOf<String>()
    private var number = mutableListOf<String>()
    private var location = mutableListOf<String>()
    private var statusCheck = mutableListOf<Boolean>()
    private var timeLimit = mutableListOf<String>()

    private var insertIndex = 0

    // included FrameLayout
    lateinit var newTaskInput : FrameLayout
    lateinit var inputName : EditText
    lateinit var inputNumber : EditText
    lateinit var inputCityName : EditText
    lateinit var inputStreetName : EditText
    lateinit var inputStreetNumber : EditText
    lateinit var inputTime : EditText
    lateinit var confirmButton : Button
    lateinit var cancelButton : Button

    // other elements
    private lateinit var addPrikup : Button
    private lateinit var addDostava : Button
    private lateinit var clearBtn : Button

    lateinit var file : SharedPreferences
    lateinit var data : DataManager



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        newTaskInput = findViewById(R.id.include_layout)
        inputName = newTaskInput.findViewById(R.id.name)
        inputNumber = newTaskInput.findViewById(R.id.number)
        inputCityName = newTaskInput.findViewById(R.id.city_name)
        inputStreetName = newTaskInput.findViewById(R.id.street_name)
        inputStreetNumber = newTaskInput.findViewById(R.id.street_number)
        inputTime = newTaskInput.findViewById(R.id.time_limit)
        confirmButton = newTaskInput.findViewById(R.id.confirm_button)
        cancelButton = newTaskInput.findViewById(R.id.cancel_button)




        clearBtn = findViewById(R.id.clear_button)


        addPrikup = findViewById(R.id.add_prikup)
        //addDostava = findViewById(R.id.add_dostava)
        newTaskInput.visibility = View.INVISIBLE


        var rv_recyclerView : RecyclerView = findViewById(R.id.rv_recyclerView)
        //var rv_recyclerView2 : RecyclerView = findViewById(R.id.rv_recyclerView2)



        // data file
        file = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        data = DataManager(file)
        // loading previous data
        rv_recyclerView.layoutManager = LinearLayoutManager(this)
        rv_recyclerView.adapter = RecyclerAdapter(data.shopName, data.number, data.location, data.timeLimit, data.statusCheck, baseContext)


        cancelButton.setOnClickListener {
            newTaskInput.visibility = View.INVISIBLE
            clearBtn.visibility = View.VISIBLE
            inputName.setText("")
            inputNumber.setText("")
            inputStreetNumber.setText("")
            inputStreetName.setText("")
            inputCityName.setText("")
            inputTime.setText("")
        }

        confirmButton.setOnClickListener {
            addToList(inputName.text.toString(), inputNumber.text.toString(), "${inputStreetName.text} ${inputStreetNumber.text}, ${inputCityName.text}", "${inputTime.text}", false)
            data.addData(inputName.text.toString(), inputNumber.text.toString(), "${inputStreetName.text} ${inputStreetNumber.text}, ${inputCityName.text}", "${inputTime.text}",false)
            // check if data is added
            Toast.makeText(this,"Data inside ${data.counter}th element:\n${data.message[0]}\n${data.message[1]}\n${data.message[2]}\n${data.message[3]}\n", Toast.LENGTH_LONG).show()

            newTaskInput.visibility = View.INVISIBLE
            rv_recyclerView.layoutManager = LinearLayoutManager(this)
            rv_recyclerView.adapter = RecyclerAdapter(data.shopName, data.number, data.location, data.timeLimit, data.statusCheck, baseContext)
            clearBtn.visibility = View.VISIBLE
            inputName.setText("")
            inputNumber.setText("")
            inputStreetNumber.setText("")
            inputStreetName.setText("")
            inputCityName.setText("")
            inputTime.setText("")
        }


        addPrikup.setOnClickListener {
            newTaskInput.visibility = View.VISIBLE
            clearBtn.visibility = View.INVISIBLE
        }

        clearBtn.setOnClickListener {
            shopName.clear()
            number.clear()
            location.clear()
            statusCheck.clear()

            data.kill()

            rv_recyclerView.layoutManager = LinearLayoutManager(this)
            rv_recyclerView.adapter = RecyclerAdapter(shopName, number, location, timeLimit, statusCheck, baseContext)
        }




    }

    private fun addToList(shop : String, broj : String, lokacija : String, time : String, status_check : Boolean){
        shopName.add(shop)
        number.add(broj)
        location.add(lokacija)
        statusCheck.add(status_check)
        timeLimit.add(time)
    }


}