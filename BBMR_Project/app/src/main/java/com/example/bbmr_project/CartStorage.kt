package com.example.bbmr_project

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Product(val name: String, val price: Int, val count: Int) : Parcelable
object CartStorage {
    val productList: ArrayList<Product> = ArrayList()
}