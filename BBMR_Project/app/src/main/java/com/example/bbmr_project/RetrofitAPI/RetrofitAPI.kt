package com.example.bbmr_project.RetrofitAPI

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import okhttp3.FormBody
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

data class Coupon(
    @SerializedName("coupon") val coupon: String
)
data class OrderResponse(
    @SerializedName("orderNumber")
    val orderNumber: Int
)

data class OrderData(
    val total_amount: Int,
    val menu_ids: List<Unit>,
    val coupon: String?,
    val discount: Int?
)

data class MenuData(
    val menu_id: String,
    val quantity: Int
)

data class RfAPI(val result: String)

interface RetrofitAPI {
    @GET("/todos/")
    fun getTodoList() : Call<JsonObject>
    @POST("/checkcoupon/")
    fun sendCoupon(@Body CouponNum: FormBody): Call<JsonObject>
    @POST("/saveorder/")
    fun sendOrder(@Body orderData: OrderData): Call<JsonObject>
}
interface ApiService {
    @Multipart
    @POST("/face/")
    fun uploadImage(@Part image: MultipartBody.Part): Call<RfAPI>
}