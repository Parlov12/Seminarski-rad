package com.example.deliverymanager

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast

// pohrana podataka
class DataManager (var data : SharedPreferences){
    // liste u koje se spremaju podaci
    var shopName : MutableList<String> = mutableListOf()
    var number : MutableList<String> = mutableListOf()
    var location : MutableList<String> = mutableListOf()
    var statusCheck : MutableList<Boolean> = mutableListOf()
    var refData : SharedPreferences = data

    // sluzi za pracenje koji element se sprema
    var counter : Int = 0
    var editor: SharedPreferences.Editor = data.edit()
    var i = 0

    var message : Array<String> = arrayOf("", "", "")


    // konstruktor
    // prilikom stvaranja objekta
    init {
        // u SharedPreference file-u pod kljucem COUNTER je pohranjena vrijednost brojaca
        // ako nista nije spremljeno pod imenom "COUNTER", stvara se novi kljuc COUNTER s vrijednosti 0
        counter = data.getInt("COUNTER", 0)

        if(counter == 0){
            // do nothing
        }
        else{
            for (i in 0..counter-1){
                // od 0 do counter, u navedene liste na pocetku se dodaju podaci iz SharedPreferencu file-a
                // koji se pohranjeni na nacin da je kljuc prvog elementa IME0, drugi IME1, ...
                shopName.add(data.getString("NAME$i", "NULL")!!)
                number.add(data.getString("NUMBER$i","NULL")!!)
                location.add(data.getString("LOCATION$i","NULL")!!)
                statusCheck.add(data.getBoolean("STATUS_CHECK$i",false)!!)
            }
        }
    }

    // dodavanje n-tog elementa u listu 
    fun addData(name : String, broj : String, lokacija : String, check : Boolean) {
        shopName.add(name)
        number.add(broj)
        location.add(lokacija)
        statusCheck.add(check)

        // elementi se pohranjuju na nacin IME + VRIJEDNOST COUNTERA U TOM TRENUTKU
        editor.putString("NAME$counter",shopName[counter])
        editor.putString("NUMBER$counter",number[counter])
        editor.putString("LOCATION$counter",location[counter])
        editor.putBoolean("STATUS_CHECK$counter",statusCheck[counter])

        // nakon spremanja svih elemenata pod kljucem IME + VRIJEDNOST COUNTER
        // counter se povecava za +1
        counter += 1
        editor.putInt("COUNTER", counter)

        // editor koji sluzi za uredivanje SharedPreference file-a, commit-om potvrduje zapisane podatake
        editor.commit()

        // check if everything is correctly written
        message[0] = refData.getString("NAME${counter-1}", "NULL")!!
        message[1] = refData.getString("NUMBER${counter-1}", "NULL")!!
        message[2] = refData.getString("LOCATION${counter-1}", "NULL")!!

    }

    // brise cijeli SharedPreference file i sve liste
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

    // uklanjanje jednog elementa
    fun removeAnElement(index : Int) {
        shopName.removeAt(index)
        number.removeAt(index)
        location.removeAt(index)
        statusCheck.removeAt(index)

        counter = counter - 1

        editor.clear()
        editor.commit()

        editor.putInt("COUNTER", counter)
        editor.commit()

        // update SharedPreference file-a
        for (i in 0..counter-1){
            editor.putString("NAME$i",shopName[i])
            editor.putString("NUMBER$i",number[i])
            editor.putString("LOCATION$i",location[i])
            editor.putBoolean("STATUS_CHECK$i",statusCheck[i])
            editor.commit()
        }
    }

    // funkcija koja sluzi da se lista refresha u trenutku kad se korisnik klikne da je task gotov
    fun refreshTempList() {
        shopName.clear()
        number.clear()
        location.clear()
        statusCheck.clear()

        for (i in 0..counter-1){
            // od 0 do counter, u navedene liste na pocetku se dodaju podaci iz SharedPreferencu file-a
            // koji se pohranjeni na nacin da je kljuc prvog elementa IME0, drugi IME1, ...
            shopName.add(data.getString("NAME$i", "NULL")!!)
            number.add(data.getString("NUMBER$i","NULL")!!)
            location.add(data.getString("LOCATION$i","NULL")!!)
            statusCheck.add(data.getBoolean("STATUS_CHECK$i",false)!!)
        }
    }





}