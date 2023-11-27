package com.example.bbmr_project.Menu
import android.content.ContentValues
import android.provider.Settings.Global.getString
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bbmr_project.R
import com.example.bbmr_project.RetrofitAPI.RetrofitAPI
import com.example.bbmr_project.VO.MenuVO
import com.example.bbmr_project.VO.Senior_TakeOutVO
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MenuListViewModel(private val retrofitService: RetrofitService) : ViewModel() {
    private val retrofitAPI = retrofitService.retrofitAPI
//    lateinit var mRetrofitAPI: RetrofitAPI
//    lateinit var mRetrofit : Retrofit
    lateinit var mCallTodoList : retrofit2.Call<JsonObject>
    val coffeeList : ArrayList<MenuVO> = ArrayList()
    val dessertList : ArrayList<MenuVO> = ArrayList()
    val teaList : ArrayList<MenuVO> = ArrayList()
    val mdList : ArrayList<MenuVO> = ArrayList()
    val flatccinoList : ArrayList<MenuVO> = ArrayList()
    val beverageList : ArrayList<MenuVO> = ArrayList()


    // 첫 번째 메뉴리스트 LiveData 및 getter 설정
    private val snior_MenuList1 = MutableLiveData<List<Senior_TakeOutVO>>()
    val menuList1: LiveData<List<Senior_TakeOutVO>> get() = snior_MenuList1


    // 두 번째 메뉴리스트 LiveData 및 getter 설정
    private val snior_MenuList2 = MutableLiveData<List<Senior_TakeOutVO>>()
    val menuList2: LiveData<List<Senior_TakeOutVO>> get() = snior_MenuList2


    //
    private val snior_MenuList3 = MutableLiveData<List<Senior_TakeOutVO>>()
    val menuList3: LiveData<List<Senior_TakeOutVO>> get() = snior_MenuList3

    private val snior_MenuList4 = MutableLiveData<List<Senior_TakeOutVO>>()
    val menuList4: LiveData<List<Senior_TakeOutVO>> get() = snior_MenuList3

    // 초기화 블록에서 초기 데이터 설정
    init {
        // 초기 데이터 설정
        snior_MenuList1.value = createMenuList1()

    }

    //첫 번째 메뉴 리스트 생성 메서드
    private fun createMenuList1(): List<Senior_TakeOutVO> {
        return listOf(
            Senior_TakeOutVO("", "아메리카노", 2000),
            Senior_TakeOutVO("", "메리카노아", 3000),
            Senior_TakeOutVO("", "리카노아메", 4000),
            Senior_TakeOutVO("", "카노아메리", 5000),
            Senior_TakeOutVO("", "아메리카노", 2000),
            Senior_TakeOutVO("", "메리카노아", 3000),
            Senior_TakeOutVO("", "리카노아메", 4000),
            Senior_TakeOutVO("", "카노아메리", 5000),
            Senior_TakeOutVO("", "아메리카노", 2000),
            Senior_TakeOutVO("", "메리카노아", 3000),
            Senior_TakeOutVO("", "리카노아메", 4000),
            Senior_TakeOutVO("", "카노아메리", 5000),
            Senior_TakeOutVO("", "아메리카노", 2000),
            Senior_TakeOutVO("", "메리카노아", 3000),
            Senior_TakeOutVO("", "리카노아메", 4000),
            Senior_TakeOutVO("", "카노아메리", 5000),
        )
    }

    // 두 번째 메뉴 리스트 생성 메서드
    private fun createMenuList2(): List<Senior_TakeOutVO> {
        return listOf(
        )
    }

    //
    private fun createMenuList3(): List<Senior_TakeOutVO> {
        return listOf(
        )
    }
    private fun createMenuList4(): List<Senior_TakeOutVO> {
        return listOf(
        )
    }

    private fun callTodoList() {
        mCallTodoList = retrofitAPI.getTodoList()
        mCallTodoList.enqueue(mRetrofitCallback)    // 응답을 큐 대기열에 넣는다.
    }
    private val mRetrofitCallback = (object : retrofit2.Callback<JsonObject> {
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




            snior_MenuList2.value = processMenuData(coffeeResult)
            snior_MenuList3.value = processMenuData(beverageResult)
            snior_MenuList4.value = processMenuData(dessertResult)
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
                        val name = data.asJsonArray[0].asString // 메뉴명
                        val price = data.asJsonArray[1].asInt   // 메뉴 가격
                        val menu_con = data.asJsonArray[2].asString // 음료 온도 (HOT,ICED,N_drink)
                        val size = data.asJsonArray[3].asInt    // 음료 사이즈 (0, 1, 2)
                        val imageUrl = data.asJsonArray[4].asString // 메뉴 이미지 url 추가
                        categoryList.add(MenuVO(menu_id, name, price, menu_con, size, imageUrl))
                    }
                }
            }
        }
    })
    private fun processMenuData(jsonObject: JsonObject?) : List<Senior_TakeOutVO> {
        val menuList = mutableListOf<Senior_TakeOutVO>()
        jsonObject?.entrySet()?.forEach { (menu_id, data) ->
            val name = data.asJsonArray[0].asString // 메뉴명
            val price = data.asJsonArray[1].asInt   // 메뉴 가격
            val imageUrl = data.asJsonArray[4].asString // 메뉴 이미지 URL
//
//            // 임시로 이미지 리소스 ID를 설정 (실제 사용 시에는 이미지 로딩 라이브러리 사용 권장)
//            val imageResId = R.drawable.default_image // 기본 이미지 리소스 ID

            menuList.add(Senior_TakeOutVO(imageUrl, name, price))
        }
        return menuList
    }

    class RetrofitService(private val baseUrl: String) {
        val retrofitAPI: RetrofitAPI by lazy {
            Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RetrofitAPI::class.java)
        }
    }
    class MenuListViewModelFactory(private val baseUrl: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MenuListViewModel::class.java)) {
                val retrofitService = RetrofitService(baseUrl)
                @Suppress("UNCHECKED_CAST")
                return MenuListViewModel(retrofitService) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}
