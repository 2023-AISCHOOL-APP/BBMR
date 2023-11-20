package com.example.camerax_mlkit

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


data class ImageUploadResponse(val result: String)


interface RetrofitAPI {

    @Multipart
    @POST("upload")
    fun uploadImage(
        @Part image: MultipartBody.Part
    ): Call<ImageUploadResponse>
}