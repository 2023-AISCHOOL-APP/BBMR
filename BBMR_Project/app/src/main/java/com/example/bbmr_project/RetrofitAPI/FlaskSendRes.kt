package com.example.bbmr_project.RetrofitAPI

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FlaskSendRes (private val context: Context, private val baseUrl: String) {
    fun sendOrder(menu_ids: List<MenuData>, total_amount: Int, coupon: String?, discount :Int?) {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(RetrofitAPI::class.java)

        // 주문 정보를 담은 데이터 모델 생성
        val orderData = OrderData(total_amount, menu_ids, coupon, discount)
        Log.d("orderdata", orderData.toString())

        // Retrofit2를 사용하여 서버에 주문 정보 전송
        val call: Call<JsonObject> = apiService.sendOrder(orderData)

        call.enqueue(object : retrofit2.Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    val orderResponse = response.body()
                    if (orderResponse != null) {
                        val orderNumber = orderResponse.get("response")?.asInt
                        Log.d(ContentValues.TAG, "주문이 성공적으로 전송됨. 주문번호: $orderNumber")
                    } else {
                        Log.d(ContentValues.TAG, "주문 응답이 null입니다.")
                    }
                } else {
                    // 주문 전송 실패
                    Log.d(ContentValues.TAG, "주문 전송 실패. HTTP 응답코드: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                // 통신 실패
                Log.d(ContentValues.TAG, "통신 실패: ${t.message}")
            }
        })
    }



}