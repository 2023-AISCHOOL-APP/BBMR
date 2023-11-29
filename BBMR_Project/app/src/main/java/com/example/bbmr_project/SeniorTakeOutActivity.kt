package com.example.bbmr_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.RelativeSizeSpan
import androidx.fragment.app.Fragment
import com.example.bbmr_project.Dialog.SeniorBasketDialog
import com.example.bbmr_project.Senior_Fragment.Category
import com.example.bbmr_project.databinding.ActivitySeniorTakeoutBinding
import com.example.bbmr_project.Senior_Fragment.SeniorTabFragment


class SeniorTakeOutActivity() : AppCompatActivity(), OnCartChangeListener {

    // 버튼 연속클릭 방지
    var buttonDoubleDefend = false
    // CarStorage에서 상품정보를 받아옴
//    val productList = CartStorage.productList
//    val adapter = SeniorGetCartStorageAdapter(productList)

    // viewBinding 엑티비디 id에 맞는 변수를 자동으로 적용해줌.
    private lateinit var binding: ActivitySeniorTakeoutBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //viewBinding 추가 코드
        binding = ActivitySeniorTakeoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        CartStorage.setListener(this)

        // ------ 장바구니 버튼 크기 변경 코드 시작 ------//
        val _text = "장바구니\n&\n결제하기"
        val spannableStringBuilder = SpannableStringBuilder(_text)
        spannableStringBuilder.setSpan(
            RelativeSizeSpan(1.4f),
            0,
            4,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableStringBuilder.setSpan(
            RelativeSizeSpan(0.5f),
            4,
            6,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableStringBuilder.setSpan(
            RelativeSizeSpan(1.4f),
            7,
            _text.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.btnBasket.text = spannableStringBuilder
        binding.btnBasket.setLineSpacing(0.4f, 0.7f)
        // ----------- 장바구니 버튼 크기 변경 코드 끝 -----------//


        // -------------일반 키오스크로 이동---------------//
        binding.btnToOrigin.setOnClickListener {
            val intent = Intent(this@SeniorTakeOutActivity, Normal_TakeOutActivity::class.java)
            startActivity(intent)
            CartStorage.clearProduct()
        }
        //-------------일반 키오스크로 이동 끝---------------//

        //초기 Fragment지정
        supportFragmentManager.beginTransaction().replace(R.id.fl1, SeniorTabFragment(Category.RECOMMEND))
            .commit()


        // 상단->메뉴 카테고리 Fragment 화면 전환
        binding.rgSenior.setOnCheckedChangeListener { _, checkedId ->
            // 체크된 버튼에 따라 해당하는 Fragment로 교체
            when (checkedId) {
                R.id.rbtnBest -> replaceFragment(SeniorTabFragment(Category.RECOMMEND))
                R.id.rbtnCoffee -> replaceFragment(SeniorTabFragment(Category.COFFEE))
                R.id.rbtnBeverage -> replaceFragment(SeniorTabFragment(Category.BEVERAGE))
                R.id.rbtnDessert -> replaceFragment(SeniorTabFragment(Category.DESSERT))
            }
        }


        binding.btnNext.setOnClickListener {
            val fragment =
                supportFragmentManager.findFragmentById(R.id.fl1) as? SeniorTabFragment

            fragment?.let {
                if (it.menuIndex <= it.getMenuListSize()){
                it.menuIndex += 5
                it.scrollToPosition(it.menuIndex)
                }
            }
        }
        binding.btnPre.setOnClickListener {
            val fragment =
                supportFragmentManager.findFragmentById(R.id.fl1) as? SeniorTabFragment
            fragment?.let {
                if (it.menuIndex != 0 && it.menuIndex >= 6) {
                    it.menuIndex -= 5
                    it.scrollToPosition(it.menuIndex)
                }
//                else if (it.menuIndex == it.getMenuListSize())
            }
        }



        // ------------장바구니 버튼 기능 & 연속클릭 방지-----------//
        binding.btnBasket.setOnClickListener {
            if (!buttonDoubleDefend) {
                buttonDoubleDefend = true
                val fragment = SeniorBasketDialog()
                val args = Bundle().apply {
//                    putParcelableArrayList(KeyProductBundleKey, ArrayList(CartStorage.productList))
                }
                fragment.arguments = args
                fragment.show(supportFragmentManager, "Senior.BasketDialog")
                Handler().postDelayed({
                    buttonDoubleDefend = false
                }, 2000)
            }
        }
        //-----------장바구니 버튼 기능 & 연속클릭 방지 끝------------//

    }


    //------프레그먼트 화면이동 함수------//
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fl1, fragment).addToBackStack(null)
            .commit()
    }

    //------화면 변화를 적용해주는 함수-----//
    override fun onChange(productList: List<Product>) {
        // 값의 총합을 구하는 코드
        val addPrice = productList.sumOf { it.price }
        // 값을 1000단위마다 , 넣어주는 코드
        val TotalPrice =
            String.format("%,d원", addPrice) // String.format("%,d", 값) -> 1000 단위마다 , 표시
        binding.tvTotalSeniorPrice.text = TotalPrice
    }

    override fun onDestroy() {
        super.onDestroy()
        CartStorage.release()
    }
}