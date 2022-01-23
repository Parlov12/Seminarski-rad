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


    private var insertIndex = 0

    // included FrameLayout
    // objekti kojima ce unutar funkcije onCreate pridruzit odgovarajuci element s layout-a input_layout.xml
    lateinit var newTaskInput : FrameLayout
    lateinit var inputName : EditText
    lateinit var inputNumber : EditText
    lateinit var inputCityName : EditText
    lateinit var inputStreetName : EditText
    lateinit var inputStreetNumber : EditText
    lateinit var confirmButton : Button
    lateinit var cancelButton : Button

    // other elements
    // button-i koji se nalaze na activity_main.xml
    private lateinit var addPrikup : Button
    private lateinit var addDostava : Button
    private lateinit var clearBtn : Button

    lateinit var file : SharedPreferences
    lateinit var data : DataManager


    // funkcija koja se poziva svaki put kada se stvara activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // pridruzivanje svakog UI elementa odredenoj varijabli
        newTaskInput = findViewById(R.id.include_layout)
        inputName = newTaskInput.findViewById(R.id.name)
        inputNumber = newTaskInput.findViewById(R.id.number)
        inputCityName = newTaskInput.findViewById(R.id.city_name)
        inputStreetName = newTaskInput.findViewById(R.id.street_name)
        inputStreetNumber = newTaskInput.findViewById(R.id.street_number)
        confirmButton = newTaskInput.findViewById(R.id.confirm_button)
        cancelButton = newTaskInput.findViewById(R.id.cancel_button)




        clearBtn = findViewById(R.id.clear_button)


        addPrikup = findViewById(R.id.add_prikup)
        //addDostava = findViewById(R.id.add_dostava)
        newTaskInput.visibility = View.INVISIBLE


        // RecyclerView u koji ce RecyclerAdapter ubacivati elemente
        var rv_recyclerView : RecyclerView = findViewById(R.id.rv_recyclerView)



        // data file
        file = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        data = DataManager(file)
        // loading previous data
        rv_recyclerView.layoutManager = LinearLayoutManager(this)
        rv_recyclerView.adapter = RecyclerAdapter(data.shopName, data.number, data.location, data.statusCheck, baseContext, file, data)


        cancelButton.setOnClickListener {
            newTaskInput.visibility = View.INVISIBLE
            clearBtn.visibility = View.VISIBLE
            inputName.setText("")
            inputNumber.setText("")
            inputStreetNumber.setText("")
            inputStreetName.setText("")
            inputCityName.setText("")
        }

        confirmButton.setOnClickListener {
            data.addData(inputName.text.toString(), inputNumber.text.toString(), "${inputStreetName.text} ${inputStreetNumber.text}, ${inputCityName.text}",false)
            // check if data is added
            Toast.makeText(this,"Data inside ${data.counter}th element:\n${data.message[0]}\n${data.message[1]}\n${data.message[2]}\n", Toast.LENGTH_LONG).show()

            newTaskInput.visibility = View.INVISIBLE
            rv_recyclerView.layoutManager = LinearLayoutManager(this)
            rv_recyclerView.adapter = RecyclerAdapter(data.shopName, data.number, data.location, data.statusCheck, baseContext, file, data)
            clearBtn.visibility = View.VISIBLE
            inputName.setText("")
            inputNumber.setText("")
            inputStreetNumber.setText("")
            inputStreetName.setText("")
            inputCityName.setText("")
        }


        addPrikup.setOnClickListener {
            newTaskInput.visibility = View.VISIBLE
            clearBtn.visibility = View.INVISIBLE
        }

        clearBtn.setOnClickListener {
            data.kill()

            rv_recyclerView.layoutManager = LinearLayoutManager(this)
            rv_recyclerView.adapter = RecyclerAdapter(data.shopName, data.number, data.location, data.statusCheck, baseContext, file, data)
        }




    }



}