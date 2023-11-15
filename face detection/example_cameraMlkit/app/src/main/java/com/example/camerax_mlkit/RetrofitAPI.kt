package com.example.camerax_mlkit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


data class ImageUploadResponse(val result: String)


interface RetrofitAPI {
    @POST("/model/")
    fun uploadImage(@Body byteArray: ByteArray): Call<ImageUploadResponse>

}