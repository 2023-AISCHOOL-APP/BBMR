package com.example.bbmr_project


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog


class TakeOutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take_out)

        // 상단 카테고리 변수선언
        val rbtnBest : Button = findViewById(R.id.rbtnBest)
        val rbtnCoffee : Button = findViewById(R.id.rbtnCoffee)
        val rbtnBeverage : Button = findViewById(R.id.rbtnBeverage)
        val rbtnDessert : Button = findViewById(R.id.rbtnDessert)

        // 하단에 들어가는 값 변수선언
        val tvPrice : TextView = findViewById(R.id.tvPrice)
        val btnBasket : Button = findViewById(R.id.btnBasket)


        // 상단 메뉴 버튼 기능
        rbtnBest.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fl1, Fragment1_1())
                .commit()
        }

        rbtnCoffee.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fl1, Fragment1_2())
                .commit()
        }

        rbtnBeverage.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fl1, Fragment1_3())
                .commit()
        }

        rbtnDessert.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fl1, Fragment1_4())
                .commit()
        }





        // 장바구니 버튼 기능
        btnBasket.setOnClickListener{
//            val intent = Intent(this, BasketActivity::class.java)
//            startActivity(intent)


            val price = tvPrice.text
            AlertDialog.Builder(this).run{
                setTitle("test dialog")
                setIcon(android.R.drawable.ic_delete)
                setPositiveButton("이전으로", null)
                setMessage("결제금액 :  ${price}")
                show()
            }

            
            }


    }
}