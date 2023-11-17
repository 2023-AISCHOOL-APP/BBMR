package com.example.bbmr_project

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.bbmr_project.RetrofitAPI.RetrofitAPI
import com.example.bbmr_project.VO.MenuVO
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    lateinit var mRetrofit : Retrofit
    lateinit var mRetrofitAPI: RetrofitAPI
    lateinit var mCallTodoList : retrofit2.Call<JsonObject>
    val cafeList : ArrayList<MenuVO> = ArrayList()
    val dessertList : ArrayList<MenuVO> = ArrayList()
    val teaList : ArrayList<MenuVO> = ArrayList()
    val mdList : ArrayList<MenuVO> = ArrayList()
    val flatccinoList : ArrayList<MenuVO> = ArrayList()
    val beverageList : ArrayList<MenuVO> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setRetrofit()   // 설정 초기화
        callTodoList()

    }

    private fun callTodoList() {
        mCallTodoList = mRetrofitAPI.getTodoList()
        mCallTodoList.enqueue(mRetrofitCallback)    // 응답을 큐 대기열에 넣는다.
    }


    //http요청을 보냈고 이건 응답을 받을 콜벡메서드
    private val mRetrofitCallback  = (object : retrofit2.Callback<JsonObject>{
        override fun onFailure(call: Call<JsonObject>, t: Throwable) {
            t.printStackTrace()
            Log.d(ContentValues.TAG, "에러입니다. => ${t.message.toString()}")
        }
        override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
            val coffeeResult = response.body()?.getAsJsonObject("coffee")
            val dessertResult = response.body()?.getAsJsonObject("dessert")
            val teaResult = response.body()?.getAsJsonObject("tea")
            val mdResult = response.body()?.getAsJsonObject("md")
            val flatccinoResult = response.body()?.getAsJsonObject("flatccino")
            val beverageResult = response.body()?.getAsJsonObject("beverage")
            val etcResult = response.body()?.getAsJsonObject("etc")

            // JsonObject를 MenuVO로 변환하여 각각 List에 추가
            if (response.isSuccessful && coffeeResult != null && dessertResult != null &&
                teaResult != null && mdResult != null && flatccinoResult != null &&
                beverageResult != null) {
                for ((name, price) in coffeeResult.entrySet()) {
                    cafeList.add(MenuVO(name, price.asInt))
                }
                for ((name, price) in dessertResult.entrySet()) {
                    dessertList.add(MenuVO(name, price.asInt))
                }
                for ((name, price) in teaResult.entrySet()) {
                    teaList.add(MenuVO(name, price.asInt))
                }

                for ((name, price) in mdResult.entrySet()) {
                    mdList.add(MenuVO(name, price.asInt))
                }

                for ((name, price) in flatccinoResult.entrySet()) {
                    flatccinoList.add(MenuVO(name, price.asInt))
                }

                for ((name, price) in beverageResult.entrySet()) {
                    beverageList.add(MenuVO(name, price.asInt))
                }
            }
        }
    })


    // Retrofit 연결 설정
    private fun setRetrofit() {
        mRetrofit = Retrofit
            .Builder()
            .baseUrl(getString(R.string.baseUrl))   // baseUrl은 strings.xml에서 플라스크서버 IP 확인 후 수정
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        mRetrofitAPI = mRetrofit.create(RetrofitAPI::class.java)
    }
}