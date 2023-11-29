package com.example.bbmr_project

import NormalSelectedMenuInfo
import Normal_Fragment_Tab1
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bbmr_project.Dialog.ConfirmBasketCancelListener
import com.example.bbmr_project.Dialog.NormalSelectPayDialogListener
import com.example.bbmr_project.Dialog.Normal_ConfirmBasketCancelDialog
import com.example.bbmr_project.Dialog.Normal_MenuDessertDialogListener
import com.example.bbmr_project.Dialog.Normal_MenuDialogListener
import com.example.bbmr_project.Dialog.Normal_MenuMDDialogListener
import com.example.bbmr_project.Dialog.Normal_SelectPayDialog
import com.example.bbmr_project.Dialog.TotalCostListener
import com.example.bbmr_project.Normal_Fragment.Normal_Fragment_Tab2
import com.example.bbmr_project.Normal_Fragment.Normal_Fragment_Tab3
import com.example.bbmr_project.databinding.ActivityNormalTakeoutBinding
import com.example.bbmr_project.Normal_Fragment.adapters.NormalSelectBasketAdapter
import com.example.bbmr_project.Normal_Fragment.adapters.NormalViewPagerAdapter
import java.text.NumberFormat
import java.util.Locale


class Normal_TakeOutActivity : AppCompatActivity(), Normal_MenuDialogListener,
    NormalSelectPayDialogListener, TotalCostListener, ConfirmBasketCancelListener,
    Normal_MenuDessertDialogListener, Normal_MenuMDDialogListener {

    private lateinit var binding: ActivityNormalTakeoutBinding
    private lateinit var normalSelectBasketAdapter: NormalSelectBasketAdapter

    private var totalCost: Int = 0  // 누적 총 비용

    // 외부 클래스에서 어댑터에 접근할 수 있는 메소드 추가
    fun getNormalSelectBasketAdapter(): NormalSelectBasketAdapter {
        return normalSelectBasketAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNormalTakeoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpTabs()
        initializeRecyclerView()

        binding.btnNormalPay.setOnClickListener {
            showSelectPayDialog()
        }
        binding.btnToSenior.setOnClickListener {
            val intent = Intent(this@Normal_TakeOutActivity, SeniorTakeOutActivity::class.java)
            startActivity(intent)
        }
        binding.btnNormalCancel.setOnClickListener {
            val confirmBasketCancelDialog = Normal_ConfirmBasketCancelDialog()
            confirmBasketCancelDialog.setConfirmBasketCancelListener(this)
            confirmBasketCancelDialog.show(
                supportFragmentManager,
                "Normal_ConfirmBasketCancelDialog"
            )
        }
        binding.btnToHome.setOnClickListener {
            val intent = Intent(this@Normal_TakeOutActivity, Normal_IntroActivity::class.java)
            startActivity(intent)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onConfirmBasketCancel() {
        Log.d("ConfirmBasketCancel", "onConfirmBasketCancel called")
        // "Yes" 버튼이 클릭되었을 때의 동작 수행
        normalSelectBasketAdapter.clearItems()
        normalSelectBasketAdapter.notifyDataSetChanged()
        // 총 비용을 0으로 초기화
        totalCost = 0
        // UI 상의 총 비용을 업데이트
        updateTotalCostUI(totalCost)
    }


    override fun onTotalCostUpdated(totalCost: Int) {
        // 활동에서 총 비용 UI 요소(tvNormalTotalMoney)를 업데이트
        updateTotalCostUI(totalCost)

        // 아이템 비용을 빼고 업데이트된 총 비용으로 totalCost 변수를 업데이트
        this.totalCost = totalCost
    }

    override fun onMenuSelectedForPayment(selectedMenuInfoList: List<NormalSelectedMenuInfo>) {
        Log.d("Normal_TakeOutActivity", "Received selected menu list: $selectedMenuInfoList")

        // Normal_SelectPayDialog에서 선택한 항목으로 업데이트
        val selectPayDialog =
            supportFragmentManager.findFragmentByTag("Normal_SelectPayDialog") as? Normal_SelectPayDialog
        selectPayDialog?.updateSelectedMenuList(selectedMenuInfoList)
    }


    private fun updateTotalCostUI(currentTotalCost: Int) {
        // 숫자를 한국 통화 단위로 포맷
        val formattedTotalCost =
            NumberFormat.getNumberInstance(Locale.KOREA).format(currentTotalCost)

        // 활동에서 총 비용 UI 요소(tvNormalTotalMoney)를 업데이트
        binding.tvNormalTotalMoney.text = String.format("%s 원", formattedTotalCost)
        // 로그로 현재 tvNormalTotalMoney의 값 출력
        Log.d("TotalCostUpdated", "Current tvNormalTotalMoney Value: $formattedTotalCost")
    }

    private fun setUpTabs() {
        val adapter = NormalViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Normal_Fragment_Tab1(), "음료")
        adapter.addFragment(Normal_Fragment_Tab2(), "디저트")
        adapter.addFragment(Normal_Fragment_Tab3(), "MD")
        binding.viewPager.adapter = adapter
        binding.tabs.setupWithViewPager(binding.viewPager)
        setCustomTabTitles()

        supportFragmentManager.beginTransaction().replace(R.id.flTakeOut, Normal_Fragment_Tab1())
            .commit()
        binding.tabs.getTabAt(0)?.view?.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.flTakeOut, Normal_Fragment_Tab1())
                .commit()
        }
        binding.tabs.getTabAt(1)?.view?.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.flTakeOut, Normal_Fragment_Tab2())
                .commit()
        }
        binding.tabs.getTabAt(2)?.view?.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.flTakeOut, Normal_Fragment_Tab3())
                .commit()
        }
    }

    // 탭의 설정
    private fun setCustomTabTitles() {
        // ViewPager와 TabLayout 연결
        binding.tabs.setupWithViewPager(binding.viewPager)

        val customTab1 = layoutInflater.inflate(R.layout.custom_origin_tab, null)
        customTab1.findViewById<TextView>(R.id.textView6).text = "음료"
        binding.tabs.getTabAt(0)?.customView = customTab1
        val customTab2 = layoutInflater.inflate(R.layout.custom_origin_tab, null)
        customTab2.findViewById<TextView>(R.id.textView6).text = "디저트"
        binding.tabs.getTabAt(1)?.customView = customTab2
        val customTab3 = layoutInflater.inflate(R.layout.custom_origin_tab, null)
        customTab3.findViewById<TextView>(R.id.textView6).text = "MD"
        binding.tabs.getTabAt(2)?.customView = customTab3
    }

    private fun initializeRecyclerView() {
        // NormalSelectBasketAdapter 생성 시 NormalSelectPayAdapter를 전달
        normalSelectBasketAdapter =
            NormalSelectBasketAdapter(
                this,
                R.layout.normal_basketlist,
                this,
            )
        binding.rvNormalBasket.apply {
            adapter = normalSelectBasketAdapter
            layoutManager = LinearLayoutManager(
                this@Normal_TakeOutActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        }
    }

    private fun showSelectPayDialog() {
        val currentList = normalSelectBasketAdapter.getCurrentList()
        // 결제 창 띄우기
        val dialog = Normal_SelectPayDialog.newInstance(currentList, "0")
        dialog.show(supportFragmentManager, "Normal_SelectPayDialog")
    }

    // Normal_MenuDialog 메서드
    override fun onMenuAdded(
        normalSelectedMenuInfo: NormalSelectedMenuInfo,
        tvCount: Int, // 수량
        menuCost: Int, // 아이템 하나의 값
        options: List<String>, // 옵션들
        optionTvCount: Int // 옵션 수량
    ) {
        // 추가된 메뉴의 tvCount를 사용자가 선택한 값으로 설정
        normalSelectedMenuInfo.tvCount = tvCount
        normalSelectedMenuInfo.options = options

        normalSelectBasketAdapter.addItem(normalSelectedMenuInfo)

        // 총 비용 업데이트
        totalCost += menuCost

        // TotalCostListener에 알림
        onTotalCostUpdated(totalCost)
    }

    // Normal_MenuDessertDialog 메서드
    override fun onDessertMenuAdded(normalSelectedMenuInfo: NormalSelectedMenuInfo) {
        // 추가된 메뉴의 tvCount를 사용자가 선택한 값으로 설정
        normalSelectedMenuInfo.tvCount = normalSelectedMenuInfo.tvCount

        normalSelectBasketAdapter.addItem(normalSelectedMenuInfo)

        // 총 비용 업데이트
        totalCost += normalSelectedMenuInfo.menuPrice

        // TotalCostListener에 알림
        onTotalCostUpdated(totalCost)
    }

    // Normal_MenuMDDialog 메서드
    override fun onMDMenuAdded(normalSelectedMenuInfo: NormalSelectedMenuInfo) {
        // 추가된 메뉴의 tvCount를 사용자가 선택한 값으로 설정
        normalSelectedMenuInfo.tvCount = normalSelectedMenuInfo.tvCount

        normalSelectBasketAdapter.addItem(normalSelectedMenuInfo)

        // 총 비용 업데이트
        totalCost += normalSelectedMenuInfo.menuPrice

        // TotalCostListener에 알림
        onTotalCostUpdated(totalCost)
    }

}
