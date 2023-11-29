package com.example.bbmr_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.bbmr_project.databinding.ActivityNormalPaySuccessBinding

class Normal_PaySuccessActivity : AppCompatActivity() {

    lateinit var binding: ActivityNormalPaySuccessBinding
    private var count = 127
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_normal_pay_success)

        binding = ActivityNormalPaySuccessBinding.inflate(layoutInflater)
        val Handler = Handler(Looper.getMainLooper())
        Handler.postDelayed({
            finishAffinity()
            val intent = Intent(this, LegalityActivity::class.java)
            startActivity(intent)
        }, 3500)




    }
    override fun onResume() {
        super.onResume()
        count++
        binding.tvWaitNum.text = count.toString()
    }
}