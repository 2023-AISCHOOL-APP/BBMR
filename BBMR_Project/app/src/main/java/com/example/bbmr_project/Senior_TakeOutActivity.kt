package com.example.bbmr_project


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.bbmr_project.Dialog.KeyProductBundleKey
import com.example.bbmr_project.Dialog.Senior_BasketDialog
import com.example.bbmr_project.databinding.ActivitySeniorTakeoutBinding
import com.example.bbmr_project.Senior_Fragment.Senior_Fragment_Tab_Recommend
import com.example.bbmr_project.Senior_Fragment.Senior_Fragment_Tab_Coffee
import com.example.bbmr_project.Senior_Fragment.Senior_Fragment_Tab_Beverage
import com.example.bbmr_project.Senior_Fragment.Senior_Fragment_Tab_Dessert


class Senior_TakeOutActivity : AppCompatActivity() {

    var buttonDoubleDefend = false


    // viewBinding 엑티비디 id에 맞는 변수를 자동으로 적용해줌.
    private lateinit var binding: ActivitySeniorTakeoutBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_s_take_out)

        //viewBinding 추가 코드
        binding = ActivitySeniorTakeoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 일반 키오스크로 이동
        binding.btnToOrigin.setOnClickListener {
            val intent = Intent(this@Senior_TakeOutActivity, Normal_TakeOutActivity::class.java)
            startActivity(intent)
        }

        // 초기 Fragment지정
        supportFragmentManager.beginTransaction().replace(R.id.fl1, Senior_Fragment_Tab_Recommend() ).commit()

        // 상단-버튼 클릭시 Fragment 화면 전환
        binding.rgSenior.setOnCheckedChangeListener { _, checkedId ->
            // 체크된 버튼에 따라 해당하는 Fragment로 교체
            when (checkedId) {
                R.id.rbtnBest -> replaceFragment(Senior_Fragment_Tab_Recommend())
                R.id.rbtnCoffee -> replaceFragment(Senior_Fragment_Tab_Coffee())
                R.id.rbtnBeverage -> replaceFragment(Senior_Fragment_Tab_Beverage())
                R.id.rbtnDessert -> replaceFragment(Senior_Fragment_Tab_Dessert())
            }
        }



        // 다음 버튼 클릭시 기존의 리스트 리셋후 새로운 리스트 추가
        binding.btnPre.setOnClickListener { (supportFragmentManager.findFragmentById(R.id.fl1) as? Senior_Fragment_Tab_Recommend)?.switchToMenuList1() }
        // 다음 버튼 클릭시 기존의 리스트 리셋후 새로운 리스트 추가
        binding.btnNext.setOnClickListener { (supportFragmentManager.findFragmentById(R.id.fl1) as? Senior_Fragment_Tab_Recommend)?.switchToMenuList2() }


        //결제화면 이동
        binding.btnPay.setOnClickListener {
        }

        // 장바구니 버튼 기능
        binding.btnBasket.setOnClickListener {
            if (!buttonDoubleDefend){
                buttonDoubleDefend = true

                val fragment = Senior_BasketDialog()

                val args = Bundle().apply {

//                putParcelable(KeyProductBundleKey, Product("아메리카노", 1000,10))
                    putParcelableArrayList(KeyProductBundleKey, ArrayList(CartStorage.productList))
            }

                fragment.arguments = args
                fragment.show(supportFragmentManager, "Senior.BasketDialog")

                fragment.setOn


            }

        }

        // 가격측정
        binding.tvTotalSeniorPrice.text

    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fl1, fragment).addToBackStack(null).commit()
    }

}