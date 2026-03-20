package com.techypie.expencetracker

import java.io.Serializable

data class Expences(
    var id : String = "",
    var date : String = "",
    var itemName : String = "",
    var itemPrice : String = "",
    var itemQuantity : String = "",
    var totalPriceItem : String = ""
) : Serializable
