package com.example.bbmr_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bbmr_project.databinding.ActivitySPayBinding

class Pay1Activity : AppCompatActivity() {

    private lateinit var binding : ActivitySPayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_s_pay)



        binding = ActivitySPayBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnBack.width = 1000

        binding.btnBack.setOnClickListener {
            finish()
        }


    }
}