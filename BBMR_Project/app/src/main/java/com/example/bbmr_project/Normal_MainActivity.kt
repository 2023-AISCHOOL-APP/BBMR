package com.example.bbmr_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Normal_MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        // 주문하기 버튼 클릭 시 Intro2로 이동
        val btnOrder: Button = findViewById(R.id.btnOrder)
        btnOrder.setOnClickListener {
            val intent = Intent(this@Normal_MainActivity, LoadingSplashActivity::class.java)
            startActivity(intent)
        }
    }
}