package com.example.bbmr_project.Menu
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bbmr_project.R
import com.example.bbmr_project.VO.Senior_TakeOutVO


class MenuListViewModel : ViewModel() {

    // 첫 번째 메뉴리스트 LiveData 및 getter 설정
    private val sMenuList1 = MutableLiveData<List<Senior_TakeOutVO>>()
    val menuList1: LiveData<List<Senior_TakeOutVO>> get() = sMenuList1


    // 두 번째 메뉴리스트 LiveData 및 getter 설정
    private val sMenuList2 = MutableLiveData<List<Senior_TakeOutVO>>()
    val menuList2: LiveData<List<Senior_TakeOutVO>> get() = sMenuList2

    // 초기화 블록에서 초기 데이터 설정
    init {
        // 초기 데이터 설정
        sMenuList1.value = createMenuList1()
        sMenuList2.value = createMenuList2()
    }

    //첫 번째 메뉴 리스트 생성 메서드
    private fun createMenuList1(): List<Senior_TakeOutVO> {
        return listOf(
            Senior_TakeOutVO(R.drawable.coffee, "아메리카노", "2000원"),
            Senior_TakeOutVO(R.drawable.coffee, "아메리카노", "2000원"),
            Senior_TakeOutVO(R.drawable.coffee, "아메리카노", "2000원"),
            Senior_TakeOutVO(R.drawable.coffee, "아메리카노", "2000원"),
            Senior_TakeOutVO(R.drawable.coffee, "아메리카노", "2000원"),
            Senior_TakeOutVO(R.drawable.coffee, "아메리카노", "2000원"),
        )
    }

    // 두 번째 메뉴 리스트 생성 메서드
    private fun createMenuList2(): List<Senior_TakeOutVO> {
        return listOf(
            Senior_TakeOutVO(R.drawable.coffee, "러시아노", "2000원"),
            Senior_TakeOutVO(R.drawable.coffee, "러시아노", "2000원"),
            Senior_TakeOutVO(R.drawable.coffee, "러시아노", "2000원"),
            Senior_TakeOutVO(R.drawable.coffee, "러시아노", "2000원"),
            Senior_TakeOutVO(R.drawable.coffee, "러시아노", "2000원"),
            Senior_TakeOutVO(R.drawable.coffee, "러시아노", "2000원"),
        )
    }
}
