package com.example.bbmr_project

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.bbmr_project.CartStorage.productList
import com.example.bbmr_project.RetrofitAPI.RetrofitAPI
import com.google.gson.JsonObject
import okhttp3.FormBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    lateinit var mRetrofit: Retrofit
    lateinit var mRetrofitAPI: RetrofitAPI
    lateinit var mCallTodoList: retrofit2.Call<JsonObject>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//
//        setRetrofit()   // 설정 초기화
//        callTodoList()
//
//        // 이곳에 쿠폰입력후 버튼 클릭 하는 함수 작성
//        // btnCoupon.setOnClickListener{
//        //      val coupon = etCoupon.text.toString()
//        //      sendCouponToServer(coupon)
//        // }
//        // 위와 같은 형태로 입력받는 쿠폰번호를 coupon 이란 변수에 담아주고
//        // sendCouponToServer(coupon) 함수를 실행 시켜줘야 서버에서 처리하고 결과값을 받아올 수 있음
//
//
//        // 이곳에 결제완료 후 DB 저장하는 코드 작성
//        // 아래는 예시 코드 - btnOrder를 눌렀을때 주문 정보들을 보내고 서버에서 저장하고 주문번호를 받아옴
//        // menu_ids는 (메뉴id, 수량), (메뉴id,수량) .... 의 리스트
//        // total_amount는 총 금액(쿠폰으로 할인된 금액은 제외)
//        // coupon 은 입력한 쿠폰코드
//        // discount 는 쿠폰으로 할인된 금액이 있으면 해당 금액
//        // (교환권일때는 해당되는 제품의 가격, 금액권 일 경우 사용한 금액권 액수만큼)
//
////        btnOrder.setOnClickListener{
////            // SendOrderTask 인스턴스 생성
////            val sendOrderTask = FlaskSendRes(this, getString(R.string.baseUrl))
////            // ------ 결제 완료 후 영수증 여부 Dialog 출력하면서 하단 코드들과
////            //
////            // 주문 정보 전송 (예시)
////            val menu_ids = listOf(MenuData(121, 2), MenuData(162, 1))
////            val total_amount = 18000 // 쿠폰 값 제외 하고 값
////            val coupon = "qwerasdfzxcv" // 쿠폰번호
////            val discount = 2500 // 쿠폰했을 때 값
////            sendOrderTask.sendOrder(menu_ids, total_amount, coupon, discount) // sendOrderTak
////        }
//
//
//
    }
//
//
//
//
//    // 쿠폰번호를 입력해서 확인 누르면 서버로 쿠폰번호를 보내고 결과값을 다시 받는 함수
//    // 서버로 보내는 쿠폰번호는 12자리 숫자,영문 ( coupon 이라는 변수에 담겨서 보내짐)
//    // result값으로 아래와 같이 서버에서 받아옴
//    // 쿠폰번호가 없거나 잘못 입력 했을경우 -> "잘못 입력 또는 없는 쿠폰입니다."
//    // 사용이 기한이 지났을 떄 -> "사용기한이 지났습니다."
//    // 교환권의 경우
//    // - 사용한 쿠폰 -> "이미 사용한 쿠폰입니다."
//    // - 미사용 쿠폰 -> [메뉴id, 메뉴명, 가격, 음료의 온도(HOT / ICED),사이즈(0,1,2)]
//    // 금액권인 경우
//    // - 잔액이 있는 경우 -> 해당 금액권의 금액 ( ex) 50000 )
//    // - 잔액이 없는 경우 -> "잔액이 없습니다"
//    private fun sendCouponToServer(coupon: String) {
//        val requestBody = FormBody.Builder()
//            .add("coupon", coupon)
//            .build()
//        val callSendCoupon = mRetrofitAPI.sendCoupon(requestBody)
//        callSendCoupon.enqueue(object : retrofit2.Callback<JsonObject> {
//            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
//                t.printStackTrace()
//                Log.d(ContentValues.TAG, "쿠폰 전송 에러입니다. => ${t.message.toString()}")
//            }
//            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
//                if (response.isSuccessful) {
//                    val result = response.body() // 서버 응답 결과
//                    Log.d(ContentValues.TAG, "쿠폰 전송 결과 => $result")
//                } else {
//                    Log.d(ContentValues.TAG, "쿠폰 전송 실패. HTTP 응답코드: ${response.code()}")
//                }
//            }
//        })
//    }
//
//
//
//
//
//    private fun callTodoList() {
//        mCallTodoList = mRetrofitAPI.getTodoList()
//        mCallTodoList.enqueue(mRetrofitCallback)    // 응답을 큐 대기열에 넣는다.
//    }
//

        // DB에서 메뉴리스트 가져오는 함수
        private val mRetrofitCallback = (object : retrofit2.Callback<JsonObject> {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                t.printStackTrace()
                Log.d(ContentValues.TAG, "에러입니다. => ${t.message.toString()}")
            }

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                val menu = response.body()?.getAsJsonObject("menu")
                Log.d("메뉴", "$menu")

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
                        productList.add(
                            Product(
                                name = name,
                                price = price,
                                temperature = temperature,
                                size = size,
                                id = id,
                                image = image,
                                cate = cate
                            )
                        )
                    }
                    Log.d("메뉴리스트", "$productList")
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