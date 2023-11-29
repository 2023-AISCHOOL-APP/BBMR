package com.example.bbmr_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout

class LegalityActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_legality)

        val cl = findViewById<ConstraintLayout>(R.id.clLegality)

        cl.setOnClickListener {
            val intent = Intent(this, StartActivity::class.java)
            startActivity(intent)
            // ------ 모든 stack의 창들을 종료하는 코드 -----
            // 시니어는 DialogFragment이기 때문에 stack 초기화가 불가능해서 여기서 전환 될 때 창들을 종료 시키는 구조
            finishAffinity()
        }


    }
}