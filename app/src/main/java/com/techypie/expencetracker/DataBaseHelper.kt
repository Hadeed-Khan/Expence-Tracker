package com.techypie.expencetracker

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBaseHelper(context: Context) : SQLiteOpenHelper(context, "Expence.dp", null, 1) {

    init {
        writableDatabase.execSQL("CREATE TABLE IF NOT EXISTS EXPENCE_TABLE(ID INTEGER PRIMARY KEY AUTOINCREMENT , DATE TEXT , ITEM_NAME TEXT, ITEM_PRICE INTEGER , ITEM_QUANTITY INTEGER , ITEM_TOTAL_PRICE INTEGER)")
    }

    // add data into DataBase
    fun addExpence(
        date: String,
        itemName: String,
        itemQuantity: Int,
        itemPrice: String,
        totalItemPrice: Int
    ): Boolean {
        var values = ContentValues()
        values.put("DATE", date)
        values.put("ITEM_NAME", itemName)
        values.put("ITEM_PRICE", itemPrice)
        values.put("ITEM_QUANTITY", itemQuantity)
        values.put("ITEM_TOTAL_PRICE", totalItemPrice)
        var isDataInsert = writableDatabase.insert("EXPENCE_TABLE", null, values)
        if (isDataInsert == -1L)
            return false
        else
            return true
    }

    // read the Data
    fun getExpence(): MutableList<Expences> {
        var cursor = writableDatabase.rawQuery("SELECT * FROM EXPENCE_TABLE", null)
        var expenceLsit = mutableListOf<Expences>()
        while (cursor.moveToNext()) {
            var id = cursor.getString(0)
            var date = cursor.getString(1)
            var itemName = cursor.getString(2)
            var itemPrice = cursor.getString(3)
            var itemQuantity = cursor.getString(4)
            var totalItemPrice = cursor.getString(5)

            var data = Expences(id, date, itemName, itemPrice, itemQuantity, totalItemPrice)
            expenceLsit.add(data)
        }
        return expenceLsit
    }


    // for Delete Record

    fun deleteExpence(id : String) : Boolean
    {
       var deleteRecord =  writableDatabase.delete("EXPENCE_TABLE","ID=?", arrayOf(id))
        return deleteRecord > 0
    }

    // for Update Data

    fun UpdateExpence(id : String ,date : String, itemName : String, itemPrice: String, itemQuantity : String, totalItemPrice: String) : Boolean
    {
        var values = ContentValues()
        values.put("DATE", date)
        values.put("ITEM_NAME", itemName)
        values.put("ITEM_PRICE", itemPrice)
        values.put("ITEM_QUANTITY", itemQuantity)
        values.put("ITEM_TOTAL_PRICE", totalItemPrice)
       var numberOfUpdatedRecord =  writableDatabase.update("EXPENCE_TABLE",values,"ID=?", arrayOf(id))
        return numberOfUpdatedRecord > 0
    }



    override fun onCreate(p0: SQLiteDatabase?) {

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }
}