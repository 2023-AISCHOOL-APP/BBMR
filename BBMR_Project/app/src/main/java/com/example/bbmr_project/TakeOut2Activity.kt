package com.example.bbmr_project

import Fragment2_1
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.bbmr_project.VO.BasketVO
import com.example.bbmr_project.databinding.ActivityNTakeOutBinding
//import com.example.bbmr_project.fragments2.Fragment2_1
import com.example.bbmr_project.fragments2.Fragment2_1_Coffee
import com.example.bbmr_project.fragments2.Fragment2_2
import com.example.bbmr_project.fragments2.Fragment2_3
import com.example.bbmr_project.fragments2.adapters.BasketAdapter
import com.example.bbmr_project.fragments2.adapters.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout

interface NMenuDialogListener {
    fun onMenuAdded(basketVO: BasketVO)
}
class TakeOut2Activity : AppCompatActivity() {

    private lateinit var binding: ActivityNTakeOutBinding
    private lateinit var basketAdapter: BasketAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_n_take_out)

        binding = ActivityNTakeOutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpTabs(binding.viewPager)

        val fl: FrameLayout = findViewById(R.id.flTakeOut)

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

        // TakeOut2Activity에서 BasketAdapter 객체를 생성하고 초기화하는 부분
        val basketAdapter = BasketAdapter(this, R.layout.basket_list, mutableListOf())
        binding.rvBasket.adapter = basketAdapter

        // TakeOut2Activity에서 메뉴 정보를 받는 부분
        val intent = intent
        val basketImg = intent.getIntExtra("basketImg", 0)
        val tvBasketCount = intent.getStringExtra("tvBasketCount")

        // Basket에 추가
        val basketVO = BasketVO(basketImg, tvBasketCount ?: "")
        basketAdapter.addItem(basketVO)

        val gridLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvBasket.layoutManager = gridLayoutManager
    }


    private fun setUpTabs(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Fragment2_1(), "음료")
        adapter.addFragment(Fragment2_2(), "디저트")
        adapter.addFragment(Fragment2_3(), "MD")
        viewPager.adapter = adapter

        val tabs = findViewById<TabLayout>(R.id.tabs)
        tabs.setupWithViewPager(viewPager)


        supportFragmentManager.beginTransaction().replace(R.id.flTakeOut, Fragment2_1()).commit()
        tabs.getTabAt(0)?.view?.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.flTakeOut, Fragment2_1())
                .commit()
        }
        tabs.getTabAt(1)?.view?.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.flTakeOut, Fragment2_2())
                .commit()
        }
        tabs.getTabAt(2)?.view?.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.flTakeOut, Fragment2_3())
                .commit()
        }

    }
}