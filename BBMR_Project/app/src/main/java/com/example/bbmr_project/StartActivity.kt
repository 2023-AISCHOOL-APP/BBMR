package com.example.bbmr_project

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.bbmr_project.CartStorage.menuList
import com.example.bbmr_project.CartStorage.productList
import com.example.bbmr_project.RetrofitAPI.RetrofitAPI
import com.example.bbmr_project.databinding.ActivityStartBinding
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class StartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding
    lateinit var mRetrofit : Retrofit
    lateinit var mRetrofitAPI: RetrofitAPI
    lateinit var mCallTodoList : retrofit2.Call<JsonObject>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        // 앱 최초 실행 때만 코드가 실행되고 activity변경에는 안되는
        setRetrofit()   // 설정 초기화
        callTodoList()

        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnOrder.setOnClickListener {
            val intent = Intent(this, LoadingSplashActivity::class.java)
            startActivity(intent)
            finish()
        }

    }



    private fun callTodoList() {
        mCallTodoList = mRetrofitAPI.getTodoList()
        mCallTodoList.enqueue(mRetrofitCallback)    // 응답을 큐 대기열에 넣는다.
    }


    // DB에서 메뉴리스트 가져오는 함수
    private val mRetrofitCallback  = (object : retrofit2.Callback<JsonObject>{
        override fun onFailure(call: Call<JsonObject>, t: Throwable) {
            t.printStackTrace()
            Log.d(ContentValues.TAG, "에러입니다. => ${t.message.toString()}")
        }
        override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
            val menu = response.body()?.getAsJsonObject("menu")
            Log.d("메뉴","$menu")

            // JsonObject를 MenuVO로 변환하여 각각 List에 추가
            if (response.isSuccessful && menu != null) {


                // 각 카테고리에 대해 처리
                for ((id, data) in menu.entrySet()) {
                    val name = data.asJsonObject["name"].asString
                    val price = data.asJsonObject["price"].asInt
                    val temperature = data.asJsonObject["menu_con"].asString
                    val size = data.asJsonObject["size"].asInt
                    val image = data.asJsonObject["imageUrl"].asString
                    val cate = data.asJsonObject["category"].asString
                    menuList.add(Product(name=name, price=price,  temperature = temperature, size = size ,id= id, image = image,cate=cate))
                }
                // adapter 초기화 해줘야 함
                // adapter.notifyDataSetChanged()
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