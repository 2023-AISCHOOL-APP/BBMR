package com.example.bbmr_project

import Normal_Fragment_Tab1
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bbmr_project.Dialog.Normal_MenuDialog
import com.example.bbmr_project.Dialog.Normal_MenuDialogListener
import com.example.bbmr_project.Dialog.Normal_SelectPayDialog
import com.example.bbmr_project.Menu.NormalSelectedMenuInfo
import com.example.bbmr_project.Normal_Fragment.Normal_Fragment_Tab2
import com.example.bbmr_project.Normal_Fragment.Normal_Fragment_Tab3
import com.example.bbmr_project.VO.NormalSelectBasketVO
import com.example.bbmr_project.databinding.ActivityNormalTakeoutBinding
import com.example.bbmr_project.Normal_Fragment.adapters.NormalSelectBasketAdapter
import com.example.bbmr_project.Normal_Fragment.adapters.NormalTakeOutAdapter
import com.example.bbmr_project.Normal_Fragment.adapters.NormalViewPagerAdapter
//import com.example.bbmr_project.VO.NMenuDialogListener
import com.example.bbmr_project.VO.NormalTakeOutVO
import com.google.android.material.tabs.TabLayout

class Normal_TakeOutActivity : AppCompatActivity(), Normal_MenuDialogListener {

    private lateinit var binding: ActivityNormalTakeoutBinding
    private lateinit var normalSelectBasketAdapter: NormalSelectBasketAdapter

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
            val intent = Intent(this@Normal_TakeOutActivity, Senior_TakeOutActivity::class.java)
            startActivity(intent)
        }
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
        // 장바구니(rvNormalBasket)에 정보 담기
        normalSelectBasketAdapter =
            NormalSelectBasketAdapter(this, R.layout.normal_basketlist, mutableListOf())
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
        // 결제 창 띄우기
        val normalSelectpaydialog = Normal_SelectPayDialog()
        normalSelectpaydialog.show(supportFragmentManager, "Normal_SelectPayDialog")
    }

    override fun onMenuAdded(normalSelectedMenuInfo: NormalSelectedMenuInfo) {
        // 메뉴가 추가되었을 때 호출되는 콜백 함수 - MenuDialog
        Log.d("MenuAdded", "Menu added: $normalSelectedMenuInfo")
        normalSelectBasketAdapter.addItem(normalSelectedMenuInfo)
        normalSelectBasketAdapter.notifyItemInserted(normalSelectBasketAdapter.itemCount - 1)
    }

}
