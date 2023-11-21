package com.example.bbmr_project

import android.os.Parcelable
import android.util.Log
import kotlinx.parcelize.Parcelize


@Parcelize
data class Product( val name: String,
                    val price: Int = 0,
                    val count: Int,
                    val temperature: Boolean = false,
                    val size: Boolean = false,
                    val sugar: Boolean = false,
                    val cream:Boolean = false ) : Parcelable

interface OnCartChangeListener {
    fun onChange(productList: List<Product>)
}
object CartStorage {
    private val productList: ArrayList<Product> = ArrayList()

    private var onCartChangeListener: OnCartChangeListener? = null

    fun setListener(onCartChangeListener: OnCartChangeListener) {
        this.onCartChangeListener = onCartChangeListener
    }

    fun release() {
        this.onCartChangeListener = null
    }

    /**
     * 장바구니 구성이 변경되었음을 알림.
     */
    private fun notifyProductListChanged() {
        onCartChangeListener?.onChange(productList)
        Log.d("CartStore", "productList=$productList")

    }

    fun getProductList(): List<Product> {
        Log.d("CartStore", "getProductList()=$productList")
        return productList

    }

    fun addProduct(product: Product) {
        productList.add(product)
        notifyProductListChanged()
    }

    fun removeProduct(product: Product) {
        productList.remove(product)
        notifyProductListChanged()
    }


    fun clearProduct() {
        productList.clear()
        notifyProductListChanged()
    }
}
