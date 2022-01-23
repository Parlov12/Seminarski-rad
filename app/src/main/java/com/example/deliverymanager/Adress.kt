package com.example.deliverymanager

 // klasa koja sluzi za preglednost podataka pojedine adrese
class Adress {
    private var streetName : String
    private var streetNumber: Int
    private var cityName : String
    private var zipCode : Int

    init {
        streetName = "NULL"
        streetNumber = 0
        cityName = "NULL"
        zipCode = 0
    }
}