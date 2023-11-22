package com.example.bbmr_project

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable


@Parcelize
data class Product(val name: String,
                   var price: Int,
                   var count: Int,
                   var temperature: Boolean = false,
                   var size: Boolean = false,
                   var sugar: Boolean = false,
                   var cream:Boolean = false,
                   val id:String = "001" ) : Parcelable, Serializable

object CartStorage {
    val productList: ArrayList<Product> = ArrayList()
}