package com.example.bbmr_project.Menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bbmr_project.CartStorage
import com.example.bbmr_project.Product
import com.example.bbmr_project.Senior_Fragment.Category
import com.example.bbmr_project.VO.Senior_TakeOutVO


class MenuListViewModel(
    private val category: Category
) : ViewModel() {

    class MenuListViewModelFactory(private val category: Category) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MenuListViewModel::class.java)) {
                return MenuListViewModel(category) as T
            }
            throw IllegalAccessException("Unknow ViewModel Class")
        }
    }

    // 메뉴리스트 LiveData 및 getter 설정
    private val _seniorMenuList = MutableLiveData<List<Senior_TakeOutVO>>()
    val seniorMenuList: LiveData<List<Senior_TakeOutVO>> get() = _seniorMenuList


    // 초기화 블록에서 초기 데이터 설정
    init {
        // 초기 데이터 설정
        val menuList = when (category) {
            Category.RECOMMEND -> createMenuList1()
            Category.COFFEE -> createMenuList2()
            Category.BEVERAGE -> createMenuList3()
            Category.DESSERT -> createMenuList4()
        }
        _seniorMenuList.value = menuList
    }

    //첫 번째 메뉴 리스트 생성 메서드
    private fun createMenuList1(): List<Senior_TakeOutVO> {
        val menuList: ArrayList<Product> = CartStorage.menuList
        val recommendList: List<Senior_TakeOutVO> = menuList.filter { product ->
            product.id == "2" || product.id == "5" || product.id == "31" || product.id == "10" || product.id == "85" || product.id == "82" || product.id == "98" || product.id == "71" || product.id == "47" || product.id == "43"
        }.map {product ->
            Senior_TakeOutVO(
                sname = product.name,
                sprice = product.price,
                simg = product.image
            )
        }
        return recommendList
    }

    // 두 번째 메뉴 리스트 생성 메서드
    private fun createMenuList2(): List<Senior_TakeOutVO> {
        val menuList: ArrayList<Product> = CartStorage.menuList
        val coffeeList: List<Senior_TakeOutVO> = menuList.filter { product ->
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

    private fun createMenuList3(): List<Senior_TakeOutVO> {
        val menuList: ArrayList<Product> = CartStorage.menuList
        val teaList: List<Senior_TakeOutVO> = menuList.filter { product ->
            product.cate == "tea" && product.size == 1 && product.temperature == "HOT"
        }.map { product ->
            Senior_TakeOutVO(
                sname = product.name,
                sprice = product.price,
                simg = product.image
            )
        }
        val beverageList: List<Senior_TakeOutVO> = menuList.filter { product ->
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
        val mdList: List<Senior_TakeOutVO> = menuList.filter { product ->
            product.cate == "md"
        }.map { product ->
            Senior_TakeOutVO(
                sname = product.name,
                sprice = product.price,
                simg = product.image
            )
        }
        val dessertList: List<Senior_TakeOutVO> = menuList.filter { product ->
            product.cate == "dessert"
        }.map { product ->
            Senior_TakeOutVO(
                sname = product.name,
                sprice = product.price,
                simg = product.image
            )
        }
        val mdDessertList = mutableListOf<Senior_TakeOutVO>()
        mdDessertList.addAll(dessertList)
        mdDessertList.addAll(mdList)
        return mdDessertList
    }


    private fun createMenuList5(): List<Senior_TakeOutVO> {
        val menuList: ArrayList<Product> = CartStorage.menuList
        val recommendList: List<Senior_TakeOutVO> = menuList.filter { product ->
            product.id == "35" || product.id == "121" || product.id == "122"
        }.map {product ->
            Senior_TakeOutVO(
                sname = product.name,
                sprice = product.price,
                simg = product.image
            )
        }
        return recommendList
    }
}

