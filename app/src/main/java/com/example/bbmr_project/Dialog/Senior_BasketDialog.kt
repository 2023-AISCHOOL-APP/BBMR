package com.example.bbmr_project.Dialog

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.bbmr_project.CouponNumberActivity
import com.example.bbmr_project.databinding.DialogSeniorBasketBinding
import com.example.bbmr_project.databinding.DialogSeniorMenuBinding

class Senior_BasketDialog : DialogFragment() {

    private lateinit var binding: DialogSeniorBasketBinding


    override fun onStart() {
        super.onStart()
        val darkTransparentBlack = Color.argb((255 * 0.6).toInt(), 0, 0, 0)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(darkTransparentBlack))
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog?.window?.setDimAmount(0.4f)
//        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 쿠폰 값을 받아오는 곳

        binding = DialogSeniorBasketBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 할인 쿠폰 금액 가져오기
        val discountPrice = arguments?.getString("discount_price").toString().toIntOrNull()?: 0 // 여기서 각 상품에 맞는 게 String으로 가져와 짐
        // 총 합계
        var amountPrice = binding.tvAmount.text.toString().toIntOrNull()?: 0

        // 남은 금액
        var extraPrice = 0

        if (amountPrice >= discountPrice) {
            amountPrice = amountPrice-discountPrice
            binding.tvAmount.text = amountPrice.toString()
        } else if (amountPrice < discountPrice) {
            amountPrice = amountPrice - discountPrice
            // 이 부분에서 남은 금액을 교환권에 되돌려 주기
            extraPrice = discountPrice-amountPrice
        } else {

        }

        // 쿠폰은 바로 보내버리기
        binding.btnCpnDSB.setOnClickListener {
            val intent = Intent(view.context, CouponNumberActivity::class.java)
            startActivity(intent)
        }

        // 결제창 클릭했을 때, 결제 로 넘어가기
        binding.btnCardDSB.setOnClickListener {
            val dialogFragment = Senior_PaymentDialog()
            val bundle = Bundle()
            // 결제 창으로 넘기기 amount라는 걸 받아오면 됨 받아오는 방법은  discountPrice변수 초기화에 적혀있음
            bundle.putString("amount", "${amountPrice}")
            dialogFragment.arguments = bundle

            dialogFragment.show(requireActivity().supportFragmentManager, "Senior_PaymentDialog")
        }


        binding.btnTurnDSB.setOnClickListener {
            dismiss()
        }
    }
}