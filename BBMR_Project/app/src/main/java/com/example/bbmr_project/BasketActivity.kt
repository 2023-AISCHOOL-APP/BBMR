package com.example.bbmr_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class BasketActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basket)

        val btnTurn : Button = findViewById(R.id.btnTurn)

        btnTurn.setOnClickListener{
            val intent = Intent(this, TakeOutActivity::class.java)
            startActivity(intent)
        }
    }
}