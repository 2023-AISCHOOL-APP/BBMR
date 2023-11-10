package com.example.bbmr_project

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.example.bbmr_project.fragments.Fragment2_1
import com.example.bbmr_project.fragments.Fragment2_2
import com.example.bbmr_project.fragments.Fragment2_3
import com.example.bbmr_project.fragments.adapters.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout

class TakeOut2Activity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take_out2)
        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        val tabs: TabLayout = findViewById(R.id.tabs)
        setUpTabs(viewPager)

        val fl: FrameLayout = findViewById(R.id.fl)

        // ViewPager와 TabLayout 연결
        tabs.setupWithViewPager(viewPager)

        val customTab1 = layoutInflater.inflate(R.layout.custom_origin_tab, null)
        customTab1.findViewById<TextView>(R.id.textView6).text = "음료"
        tabs.getTabAt(0)?.customView = customTab1
        val customTab2 = layoutInflater.inflate(R.layout.custom_origin_tab, null)
        customTab2.findViewById<TextView>(R.id.textView6).text = "디저트"
        tabs.getTabAt(1)?.customView = customTab2
        val customTab3 = layoutInflater.inflate(R.layout.custom_origin_tab, null)
        customTab3.findViewById<TextView>(R.id.textView6).text = "MD"
        tabs.getTabAt(2)?.customView = customTab3


    }


    private fun setUpTabs(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Fragment2_1(), "음료")
        adapter.addFragment(Fragment2_2(), "디저트")
        adapter.addFragment(Fragment2_3(), "MD")
        viewPager.adapter = adapter

        val tabs = findViewById<TabLayout>(R.id.tabs)
        tabs.setupWithViewPager(viewPager)


        supportFragmentManager.beginTransaction().replace(R.id.fl, Fragment2_1()).commit()
        tabs.getTabAt(0)?.view?.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.fl, Fragment2_1()).commit()
        }
        tabs.getTabAt(1)?.view?.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.fl, Fragment2_2()).commit()
        }
        tabs.getTabAt(2)?.view?.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.fl, Fragment2_3()).commit()
        }
    }
}