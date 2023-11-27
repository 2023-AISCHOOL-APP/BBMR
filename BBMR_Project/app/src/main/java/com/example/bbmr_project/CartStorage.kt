package com.example.bbmr_project

import android.os.Parcelable
import android.util.Log
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class Product(
    val name: String = "",
    var price: Int = 0,
    var count: Int = 0,
    var temperature: String = "ICED",
    var size: Int = 1,
    var sugar: Boolean = false,
    var cream: Boolean = false,
    val id: String = "001",
    var image: String = "",
    val cate : String = ""
) : Parcelable, Serializable




interface OnCartChangeListener {
    fun onChange(productList: List<Product>)
}

object CartStorage {

    // private val 에서 private로 변경 -> 이유 : MenuDialog에서 86번줄 추가옵션 productList가 오류가 듬
    val productList: ArrayList<Product> = ArrayList()
    val menuList: ArrayList<Product> = ArrayList()
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
    fun notifyProductListChanged() {
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
