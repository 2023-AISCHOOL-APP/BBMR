package com.example.bbmr_project.Menu
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bbmr_project.R
import com.example.bbmr_project.VO.TakeOut1VO


class MenuListViewModel : ViewModel() {

    // 첫 번째 메뉴리스트 LiveData 및 getter 설정
    private val sMenuList1 = MutableLiveData<List<TakeOut1VO>>()
    val menuList1: LiveData<List<TakeOut1VO>> get() = sMenuList1


    // 두 번째 메뉴리스트 LiveData 및 getter 설정
    private val sMenuList2 = MutableLiveData<List<TakeOut1VO>>()
    val menuList2: LiveData<List<TakeOut1VO>> get() = sMenuList2

    // 초기화 블록에서 초기 데이터 설정
    init {
        // 초기 데이터 설정
        sMenuList1.value = createMenuList1()
        sMenuList2.value = createMenuList2()
    }

    //첫 번째 메뉴 리스트 생성 메서드
    private fun createMenuList1(): List<TakeOut1VO> {
        return listOf(
            TakeOut1VO(R.drawable.coffee, "아메리카노", "2000원"),
            TakeOut1VO(R.drawable.coffee, "아메리카노", "2000원"),
            TakeOut1VO(R.drawable.coffee, "아메리카노", "2000원"),
            TakeOut1VO(R.drawable.coffee, "아메리카노", "2000원"),
            TakeOut1VO(R.drawable.coffee, "아메리카노", "2000원"),
            TakeOut1VO(R.drawable.coffee, "아메리카노", "2000원"),
        )
    }

    // 두 번째 메뉴 리스트 생성 메서드
    private fun createMenuList2(): List<TakeOut1VO> {
        return listOf(
            TakeOut1VO(R.drawable.coffee, "러시아노", "2000원"),
            TakeOut1VO(R.drawable.coffee, "러시아노", "2000원"),
            TakeOut1VO(R.drawable.coffee, "러시아노", "2000원"),
            TakeOut1VO(R.drawable.coffee, "러시아노", "2000원"),
            TakeOut1VO(R.drawable.coffee, "러시아노", "2000원"),
            TakeOut1VO(R.drawable.coffee, "러시아노", "2000원"),
        )
    }
}
