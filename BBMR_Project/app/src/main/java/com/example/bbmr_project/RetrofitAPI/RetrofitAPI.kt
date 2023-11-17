package com.example.bbmr_project.RetrofitAPI

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import okhttp3.FormBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

data class Coupon(
    @SerializedName("coupon") val coupon: String
)

interface RetrofitAPI {
    @GET("/todos/")
    fun getTodoList() : Call<JsonObject>
    @POST("/checkcoupon/")
    fun sendCoupon(@Body coupon: FormBody): Call<JsonObject>
}