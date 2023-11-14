package com.example.bbmr_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bbmr_project.databinding.ActivityPay1Binding

class Pay1Activity : AppCompatActivity() {

    private lateinit var binding : ActivityPay1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay1)

        binding = ActivityPay1Binding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnBack.setOnClickListener {
            finish()
        }


    }
}