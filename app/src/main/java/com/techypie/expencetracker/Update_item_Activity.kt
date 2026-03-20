package com.techypie.expencetracker

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.techypie.expencetracker.databinding.ActivityMainBinding
import com.techypie.expencetracker.databinding.ActivityUpdateItemBinding

class Update_item_Activity : AppCompatActivity() {
    // Global Variable
    lateinit var binding: ActivityUpdateItemBinding
    lateinit var expences: Expences
    lateinit var databaseHelper : DataBaseHelper
    var itemQuantity = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUpdateItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intilizeEveryThing()

    }

    fun intilizeEveryThing()
    {

        databaseHelper = DataBaseHelper(this@Update_item_Activity)
         expences = intent.getSerializableExtra("item") as Expences

        itemQuantity = binding.noOfItem.text.toString().toInt()

        binding.increment.setOnClickListener {
            itemQuantity++
            binding.noOfItem.text = "$itemQuantity"
        }
        binding.decrement.setOnClickListener {
            if (itemQuantity>1)
            {
                itemQuantity--
                binding.noOfItem.text = "$itemQuantity"
            }
        }

        binding.deleteButton.setOnClickListener {
            var recordDelete = databaseHelper.deleteExpence(expences.id)
            Toast.makeText(this@Update_item_Activity, "$recordDelete", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.updateButton.setOnClickListener {
            var itemName = binding.itemName.text.toString()
            var itemPrice = binding.itemPrice.text.toString()
            var d = binding.date.dayOfMonth
            var m = binding.date.month+1
            var y = binding.date.year
            var date = "$d/$m/$y"

            if (itemName.isEmpty() || itemPrice.isEmpty())
            {
                Toast.makeText(this@Update_item_Activity, "Please fill all Field", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            var totalItemPrice = itemPrice.toInt()*itemQuantity

            Toast.makeText(this@Update_item_Activity, "$date", Toast.LENGTH_SHORT).show()
           var dataUpdated =  databaseHelper.UpdateExpence(expences.id,date,itemName,itemPrice,itemQuantity.toString(), totalItemPrice.toString())
            Toast.makeText(this@Update_item_Activity, "$dataUpdated", Toast.LENGTH_SHORT).show()
            finish()
        }

        // for show old data
        binding.itemName.setText(expences.itemName)
        binding.itemPrice.setText(expences.itemPrice)
        itemQuantity = expences.itemQuantity.toInt()
        binding.noOfItem.text = expences.itemQuantity

        // for Date

        var parts = expences.date.split("/").toMutableList()
        var day = parts[0].toInt()
        var month = parts[1].toInt() - 1
        var year = parts[2].toInt()

        binding.date.updateDate(year,month,day)

    }
}