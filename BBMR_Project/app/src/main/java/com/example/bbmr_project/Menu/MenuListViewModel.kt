package com.example.bbmr_project.Menu
import android.content.ContentValues
import android.provider.Settings.Global.getString
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bbmr_project.CartStorage
import com.example.bbmr_project.Product
import com.example.bbmr_project.R
import com.example.bbmr_project.RetrofitAPI.RetrofitAPI
import com.example.bbmr_project.VO.MenuVO
import com.example.bbmr_project.VO.Senior_TakeOutVO
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MenuListViewModel() : ViewModel() {

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
    val menuList4: LiveData<List<Senior_TakeOutVO>> get() = snior_MenuList4

    // 초기화 블록에서 초기 데이터 설정
    init {
        // 초기 데이터 설정
        snior_MenuList1.value = createMenuList1()
        snior_MenuList2.value = createMenuList2()
        snior_MenuList3.value = createMenuList3()
        snior_MenuList4.value = createMenuList4()
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
        val menuList: ArrayList<Product> = CartStorage.menuList
        val coffeeList : List<Senior_TakeOutVO> = menuList.filter { product ->
            product.cate == "coffee" && product.size == 1 && product.temperature == "ICED"
        }.map { product ->
            Senior_TakeOutVO(
                sname = product.name,
                sprice = product.price,
                simg = product.image
            )
        }
        return coffeeList
    }

    //
    private fun createMenuList3(): List<Senior_TakeOutVO> {
        val menuList: ArrayList<Product> = CartStorage.menuList
        val teaList : List<Senior_TakeOutVO> = menuList.filter { product ->
            product.cate == "tea" && product.size == 1 && product.temperature == "HOT"
        }.map { product ->
            Senior_TakeOutVO(
                sname = product.name,
                sprice = product.price,
                simg = product.image
            )
        }
        val beverageList : List<Senior_TakeOutVO> = menuList.filter { product ->
            product.cate == "beverage" && product.size == 1 && product.temperature == "ICED"
        }.map { product ->
            Senior_TakeOutVO(
                sname = product.name,
                sprice = product.price,
                simg = product.image
            )
        }
        val teaBeverageList = mutableListOf<Senior_TakeOutVO>()
        teaBeverageList.addAll(teaList)
        teaBeverageList.addAll(beverageList)
        return teaBeverageList
    }
    private fun createMenuList4(): List<Senior_TakeOutVO> {
        val menuList: ArrayList<Product> = CartStorage.menuList
        val mdList : List<Senior_TakeOutVO> = menuList.filter { product ->
            product.cate == "md"
        }.map { product ->
            Senior_TakeOutVO(
                sname = product.name,
                sprice = product.price,
                simg = product.image
            )
        }
        val dessertList : List<Senior_TakeOutVO> = menuList.filter { product ->
            product.cate == "dessert"
        }.map { product ->
            Senior_TakeOutVO(
                sname = product.name,
                sprice = product.price,
                simg = product.image
            )
        }
        val mdDessertList = mutableListOf<Senior_TakeOutVO>()
        mdDessertList.addAll(mdList)
        mdDessertList.addAll(dessertList)
        return mdDessertList
    }
}

