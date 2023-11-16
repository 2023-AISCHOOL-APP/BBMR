package com.example.bbmr_project

import Normal_Fragment_Tab1
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.example.bbmr_project.Dialog.Normal_SelectPayDialog
import com.example.bbmr_project.VO.NormalSelectBasketVO
import com.example.bbmr_project.databinding.ActivityNormalTakeoutBinding
import com.example.bbmr_project.Normal_Fragment.Normal_Fragment_Tab2
import com.example.bbmr_project.Normal_Fragment.Normal_Fragment_Tab3
import com.example.bbmr_project.Normal_Fragment.adapters.NormalSelectBasketAdapter
import com.example.bbmr_project.Normal_Fragment.adapters.NormalViewPagerAdapter
import com.google.android.material.tabs.TabLayout

interface NMenuDialogListener {
    fun onMenuAdded(normalSelectBasketVO: NormalSelectBasketVO) {
        Log.d("Normal_TakeOutActivity", "Menu added: ${normalSelectBasketVO.basketImg}, ${normalSelectBasketVO.tvBasketCount}")
    }
}
class Normal_TakeOutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNormalTakeoutBinding
    private lateinit var normalSelectBasketAdapter: NormalSelectBasketAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_normal_takeout)

        binding = ActivityNormalTakeoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpTabs(binding.viewPager)

        val fl: FrameLayout = findViewById(R.id.flTakeOut)

        // 시니어 키오스크로 이동
        binding.btnToSenior.setOnClickListener {
            val intent = Intent(this@Normal_TakeOutActivity, Senior_TakeOutActivity::class.java)
            startActivity(intent)
        }

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

        // NormalTakeOut에서 NormalSelectBasketAdapter 객체를 생성하고 초기화하는 부분
        normalSelectBasketAdapter = NormalSelectBasketAdapter(this, R.layout.normal_basketlist, mutableListOf())
        binding.rvNormalBasket.adapter = normalSelectBasketAdapter

        // NormalTakeOut에서 메뉴 정보를 받는 부분
        val intent = intent
        val basketImg = intent.getIntExtra("basketImg", 0)
        val tvBasketCount = intent.getStringExtra("tvBasketCount")

        // Basket에 추가
        val normalSelectBasketVO = NormalSelectBasketVO(basketImg, tvBasketCount ?: "")
        normalSelectBasketAdapter.addItem(normalSelectBasketVO)

        val gridLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvNormalBasket.layoutManager = gridLayoutManager

        // 결제하기 클릭 시 결제 창 normalSelectpaydialog 띄우기
        binding.btnNormalPay.setOnClickListener {
            val normalSelectpaydialog = Normal_SelectPayDialog()
            normalSelectpaydialog.show(supportFragmentManager, "Normal_SelectPayDialog")
        }
    }


    private fun setUpTabs(viewPager: ViewPager) {
        val adapter = NormalViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Normal_Fragment_Tab1(), "음료")
        adapter.addFragment(Normal_Fragment_Tab2(), "디저트")
        adapter.addFragment(Normal_Fragment_Tab3(), "MD")
        viewPager.adapter = adapter

        val tabs = findViewById<TabLayout>(R.id.tabs)
        tabs.setupWithViewPager(viewPager)


        supportFragmentManager.beginTransaction().replace(R.id.flTakeOut, Normal_Fragment_Tab1()).commit()
        tabs.getTabAt(0)?.view?.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.flTakeOut, Normal_Fragment_Tab1())
                .commit()
        }
        tabs.getTabAt(1)?.view?.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.flTakeOut, Normal_Fragment_Tab2())
                .commit()
        }
        tabs.getTabAt(2)?.view?.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.flTakeOut, Normal_Fragment_Tab3())
                .commit()
        }

    }
}