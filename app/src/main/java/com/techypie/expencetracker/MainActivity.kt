package com.techypie.expencetracker

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.techypie.expencetracker.databinding.ActivityMainBinding
import kotlin.time.times

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var searchExpenceList: MutableList<Expences>
    lateinit var dataBaseHelper: DataBaseHelper
    lateinit var expenceListget: MutableList<Expences>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
    fun intilizeEveryThing() {

        dataBaseHelper = DataBaseHelper(this)
        expenceListget = dataBaseHelper.getExpence()
        // Start
        // for Dialog Box
        var builder = AlertDialog.Builder(this)
        var layoutAlartbox = LayoutInflater.from(this).inflate(R.layout.dialog_box, null)
        builder.setView(layoutAlartbox)
        var alertDialog = builder.create()

        binding.showDialog.setOnClickListener {

            alertDialog.setCancelable(false)
            alertDialog.show()
        }


        var itemNamefind = layoutAlartbox.findViewById<EditText>(R.id.itemName)
        var itemPricefind = layoutAlartbox.findViewById<EditText>(R.id.itemPrice)
        var noOfItem = layoutAlartbox.findViewById<TextView>(R.id.noOfItem)
        var incNoOfitem = layoutAlartbox.findViewById<ImageView>(R.id.increment)
        var decNoOfItem = layoutAlartbox.findViewById<ImageView>(R.id.decrement)
        var datefind = layoutAlartbox.findViewById<DatePicker>(R.id.date)
        var addData = layoutAlartbox.findViewById<Button>(R.id.addDataButton)
        var cancelButton = layoutAlartbox.findViewById<Button>(R.id.cancel_button)
        var totalNoOfItem = 1

        cancelButton.setOnClickListener {
            alertDialog.dismiss()
        }
        incNoOfitem.setOnClickListener {
            totalNoOfItem++
            noOfItem.text = "$totalNoOfItem"
        }
        decNoOfItem.setOnClickListener {
            if(totalNoOfItem>1)
            {
                totalNoOfItem--
                noOfItem.text = "$totalNoOfItem"
            }
        }


        addData.setOnClickListener {
            if (itemNamefind.text.isEmpty() || itemPricefind.text.isEmpty()) {
                Toast.makeText(this, "Please fill the all Data", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            var itemName = itemNamefind.text.toString()
            var itemPrice = itemPricefind.text.toString().toInt()
            var day = datefind.dayOfMonth
            var month = datefind.month + 1
            var year = datefind.year
            var date = "$day/$month/$year"

            var totalPriceofItem = (totalNoOfItem * itemPrice.toInt())
            var expenceList = dataBaseHelper.addExpence(date, itemName, totalNoOfItem, itemPrice.toString(), totalPriceofItem)
            Toast.makeText(this, "$expenceList", Toast.LENGTH_SHORT).show()
            alertDialog.dismiss()
            expenceListget = dataBaseHelper.getExpence()
            binding.recyclerView.adapter = null

            var adapter = Adapter_Expences(this, expenceListget)
            binding.recyclerView.adapter = adapter
            totalExpence(expenceListget)

            itemNamefind.text.clear()
            itemPricefind.text.clear()
            totalNoOfItem = 1
            noOfItem.text = "$totalNoOfItem"
            binding.noDataFound.visibility = View.GONE
        }

        // recycler View
        var adapter = Adapter_Expences(this, expenceListget)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        searchExpenceList = mutableListOf()

        binding.searchbutton.setOnClickListener {
            var searchInput = binding.searchInput.text.toString().trim()
            var expenceFound = false
            searchExpenceList = mutableListOf()

            for (expence in expenceListget) {
                if (expence.itemName.contains(searchInput, true)) {
                    expenceFound = true
                    searchExpenceList.add(expence)
                }
            }
            if (!expenceFound) {
                binding.recyclerView.adapter = null
                binding.noDataFound.visibility = View.VISIBLE
            } else {
                binding.recyclerView.adapter = null
                var adapter2 = Adapter_Expences(this@MainActivity, searchExpenceList)
                binding.recyclerView.adapter = adapter2
                binding.recyclerView.layoutManager = LinearLayoutManager(this)
                binding.noDataFound.visibility = View.GONE

            }
            var totalExpenceofSearchList = 0
            for (expence in searchExpenceList) {
                totalExpenceofSearchList += expence.totalPriceItem.toInt()
            }
            binding.expence.text = "$totalExpenceofSearchList"
            if (!searchInput.isEmpty()) {
                binding.totalExpenceText.text = "$searchInput Expence"
            } else {
                binding.totalExpenceText.text = "Total Expence"
            }
            checkSearchItemMaxMinPrice(totalExpenceofSearchList)
        }
        totalExpence(expenceListget)
    }
    // for find Total Expence

    fun totalExpence(expenceList: MutableList<Expences>) {
        var totalExpence = 0
        for (expence in expenceList) {
            var totalItemPrice = expence.totalPriceItem.toInt()
            totalExpence += totalItemPrice

        }
        binding.expence.text = "$totalExpence"
        findMaximumMinumum(expenceList, totalExpence)
    }


    // for find Maximum Minimum Price

    fun findMaximumMinumum(expenceList: MutableList<Expences>, totalExpence: Int) {
        var max = 0
        var min = totalExpence
        for (oneExpence in expenceList) {
            if (oneExpence.totalPriceItem.toInt() > max) {
                max = oneExpence.totalPriceItem.toInt()
            }
            if (oneExpence.totalPriceItem.toInt() < min) {
                min = oneExpence.totalPriceItem.toInt()
            }
        }
        binding.maxPriceItem.text = "MAX : $max"
        binding.minPriceItem.text = "MIN : $min"
    }

    fun checkSearchItemMaxMinPrice(totalExpenceofSearchList: Int) {
        var searchitemMaxPrice = 0
        var searchItemMinPrice = totalExpenceofSearchList
        for (expence in searchExpenceList) {
            if (expence.totalPriceItem.toInt() > searchitemMaxPrice) {
                searchitemMaxPrice = expence.totalPriceItem.toInt()
            }
            if (expence.totalPriceItem.toInt() < searchItemMinPrice) {
                searchItemMinPrice = expence.totalPriceItem.toInt()
            }
        }
        binding.maxPriceItem.text = "MAX : $searchitemMaxPrice"
        binding.minPriceItem.text = "MIN : $searchItemMinPrice"
    }

    override fun onResume() {

        super.onResume()

        intilizeEveryThing()
    }

}