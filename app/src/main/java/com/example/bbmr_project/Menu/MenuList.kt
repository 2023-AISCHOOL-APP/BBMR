package com.example.bbmr_project.Menu
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bbmr_project.R
import com.example.bbmr_project.VO.Senior_TakeOutVO


class MenuListViewModel : ViewModel() {

    // 첫 번째 메뉴리스트 LiveData 및 getter 설정
    private val snior_MenuList1 = MutableLiveData<List<Senior_TakeOutVO>>()
    val menuList1: LiveData<List<Senior_TakeOutVO>> get() = snior_MenuList1


    // 두 번째 메뉴리스트 LiveData 및 getter 설정
    private val snior_MenuList2 = MutableLiveData<List<Senior_TakeOutVO>>()
    val menuList2: LiveData<List<Senior_TakeOutVO>> get() = snior_MenuList2

    // 초기화 블록에서 초기 데이터 설정
    init {
        // 초기 데이터 설정
        snior_MenuList1.value = createMenuList1()
        snior_MenuList2.value = createMenuList2()
    }

    //첫 번째 메뉴 리스트 생성 메서드
    private fun createMenuList1(): List<Senior_TakeOutVO> {
        return listOf(
            Senior_TakeOutVO(R.drawable.coffee, "아메리카노", "2000원"),
            Senior_TakeOutVO(R.drawable.coffee, "메리카노아", "3000원"),
            Senior_TakeOutVO(R.drawable.coffee, "리카노아메", "4000원"),
            Senior_TakeOutVO(R.drawable.coffee, "카노아메리", "5000원"),
            Senior_TakeOutVO(R.drawable.coffee, "노아메리카", "6000원"),
            Senior_TakeOutVO(R.drawable.coffee, "아메리카노", "2000원"),
        )
    }

    // 두 번째 메뉴 리스트 생성 메서드
    private fun createMenuList2(): List<Senior_TakeOutVO> {
        return listOf(
            Senior_TakeOutVO(R.drawable.coffee, "에스프레소", "2000원"),
            Senior_TakeOutVO(R.drawable.coffee, "스프레소에", "3000원"),
            Senior_TakeOutVO(R.drawable.coffee, "프레소에스", "4000원"),
            Senior_TakeOutVO(R.drawable.coffee, "레소에스프", "5000원"),
            Senior_TakeOutVO(R.drawable.coffee, "소에스프레", "6000원"),
            Senior_TakeOutVO(R.drawable.coffee, "에스프레소", "7000원"),
        )
    }
}
