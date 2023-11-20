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
data class ImageUploadResponse(val result: String)

interface RetrofitAPI {
    @GET("/todos/")
    fun getTodoList() : Call<JsonObject>
    @POST("/checkcoupon/")
    fun sendCoupon(@Body coupon: FormBody): Call<JsonObject>
    @Multipart
    @POST("/model/")
    fun uploadImage(@Part image: MultipartBody.Part): Call<ImageUploadResponse> // 231120  flask에 데이터 타입을 Multipartfile 형태로 변경
}