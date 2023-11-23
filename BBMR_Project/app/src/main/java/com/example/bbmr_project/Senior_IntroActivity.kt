package com.example.bbmr_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bbmr_project.databinding.ActivitySeniorIntroBinding

class Senior_IntroActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySeniorIntroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_senior_intro)
        binding = ActivitySeniorIntroBinding.inflate(layoutInflater)


        // ------ 포장하기 매장하기 시작 ------
        binding.btnOrderInASI.setOnClickListener {

        }

        binding.btnOrderOutASI.setOnClickListener {

        }
        // ------ 포장하기 매장하기 시작 ------




    }
}