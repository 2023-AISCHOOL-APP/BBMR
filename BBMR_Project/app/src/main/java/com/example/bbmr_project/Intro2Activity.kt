package com.example.bbmr_project

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Intro2Activity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro2)

        val btnTakeOut: Button = findViewById(R.id.btnTakeOut)
        val btnStay: Button = findViewById(R.id.btnStay)

        btnTakeOut.setOnClickListener {
            val intent = Intent(this@Intro2Activity, TakeOut2Activity::class.java)
            startActivity(intent)
        }

        btnStay.setOnClickListener {
            val intent = Intent(this@Intro2Activity, TakeOut2Activity::class.java)
            startActivity(intent)
        }
    }
}