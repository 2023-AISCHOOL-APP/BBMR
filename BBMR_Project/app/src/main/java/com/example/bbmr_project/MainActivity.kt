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
    val coffeeList : ArrayList<MenuVO> = ArrayList()
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

                // 카테고리 별로 리스트 초기화
                val categories = listOf(
                    Pair(coffeeResult, coffeeList),
                    Pair(dessertResult, dessertList),
                    Pair(teaResult, teaList),
                    Pair(mdResult, mdList),
                    Pair(flatccinoResult, flatccinoList),
                    Pair(beverageResult, beverageList)
                )

                // 각 카테고리에 대해 처리
                for ((categoryResult, categoryList) in categories) {
                    for ((menu_id, data) in categoryResult.entrySet()) {
                        val name = data.asJsonArray[1].asString // 변경된 부분
                        val price = data.asJsonArray[0].asInt // 변경된 부분
                        categoryList.add(MenuVO(menu_id, name, price))
                    }
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