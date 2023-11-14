package com.example.bbmr_project


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.bbmr_project.databinding.ActivitySTakeOutBinding
import com.example.bbmr_project.fragments1.Fragment1_1
import com.example.bbmr_project.fragments1.Fragment1_2
import com.example.bbmr_project.fragments1.Fragment1_3
import com.example.bbmr_project.fragments1.Fragment1_4


class TakeOut1Activity : AppCompatActivity() {



    // viewBinding 엑티비디 id에 맞는 변수를 자동으로 적용해줌.
    private lateinit var binding : ActivitySTakeOutBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_s_take_out)

        //viewBinding 추가 코드
        binding = ActivitySTakeOutBinding.inflate(layoutInflater)
        setContentView(binding.root)






        // Fragment 관리하는 함수
        fun loadFragment(fragment : androidx.fragment.app.Fragment){
            supportFragmentManager.beginTransaction()
                .replace(R.id.fl1, fragment)
                .commit()
        }
        // 상단-버튼 클릭시 Fragment 화면 전환
        binding.rbtnBest.setOnClickListener { loadFragment(Fragment1_1()) }
        binding.rbtnCoffee.setOnClickListener { loadFragment(Fragment1_2()) }
        binding.rbtnBeverage.setOnClickListener { loadFragment(Fragment1_3()) }
        binding.rbtnDessert.setOnClickListener { loadFragment(Fragment1_4()) }

        // 다음 버튼 클릭시 기존의 리스트 리셋후 새로운 리스트 추가
        binding.btnPre.setOnClickListener { (supportFragmentManager.findFragmentById(R.id.fl1) as? Fragment1_1)?.switchToMenuList1() }
        // 다음 버튼 클릭시 기존의 리스트 리셋후 새로운 리스트 추가
        binding.btnNext.setOnClickListener { (supportFragmentManager.findFragmentById(R.id.fl1)  as? Fragment1_1)?.switchToMenuList2() }


        //결제화면 이동
        binding.btnPay.setOnClickListener {
            val intent = Intent(this@TakeOut1Activity, Pay1Activity::class.java)
            startActivity(intent)
        }





        // 장바구니 버튼 기능
        binding.btnBasket.setOnClickListener{
//            val intent = Intent(this, BasketActivity::class.java)
//            startActivity(intent)


            val price = binding.tvPrice.text
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