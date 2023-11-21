package com.example.bbmr_project

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Product( val name: String,
                    val price: Int = 0,
                    val count: Int,
                    val temperature: Boolean = false,
                    val size: Int = 0,
                    val sugar: Boolean = false,
                    val cream:Boolean = false ) : Parcelable
object CartStorage {
    val productList: ArrayList<Product> = ArrayList()
}
