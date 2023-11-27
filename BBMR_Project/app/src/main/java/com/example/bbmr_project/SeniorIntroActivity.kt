package com.example.bbmr_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bbmr_project.databinding.ActivitySeniorIntroBinding

class SeniorIntroActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySeniorIntroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeniorIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ------ 포장하기 매장하기 시작 ------
        binding.btnOrderInASI.setOnClickListener {
            val intent = Intent(this@SeniorIntroActivity, SeniorTakeOutActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnOrderOutASI.setOnClickListener {
            val intent = Intent(this@SeniorIntroActivity, SeniorTakeOutActivity::class.java)
            startActivity(intent)
            finish()
        }
        // ------ 포장하기 매장하기 끝 ------




    }
}