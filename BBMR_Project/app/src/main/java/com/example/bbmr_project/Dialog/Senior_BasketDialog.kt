package com.example.bbmr_project.Dialog

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.bbmr_project.CartStorage
import com.example.bbmr_project.Product
import com.example.bbmr_project.Senior_TakeOutActivity
import com.example.bbmr_project.databinding.DialogSeniorBasketBinding
import com.example.bbmr_project.databinding.DialogSeniorMenuBinding

const val KeyProductBundleKey = "Product"

class Senior_BasketDialog() : DialogFragment() {

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

        isCancelable = false
//        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogSeniorBasketBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val discountPrice = arguments?.getString("discount_price").toString().toIntOrNull()
            ?: 0 // 여기서 각 상품에 맞는 게 String으로 가져와 짐
        // 총 합계
        var amountPrice = binding.tvAmount.text.toString().toIntOrNull() ?: 0

        // 남은 금액
        var extraPrice = 0

        if (amountPrice >= discountPrice) {
            amountPrice = amountPrice - discountPrice
            binding.tvAmount.text = amountPrice.toString()
        } else if (amountPrice < discountPrice) {
            amountPrice = amountPrice - discountPrice
            // 이 부분에서 남은 금액을 교환권에 되돌려 주기
            extraPrice = discountPrice - amountPrice
        } else {

        }

//        val product = requireArguments().getParcelable<Product>(KeyProductBundleKey)
//        binding.tvSeniorPayPrice.text= product?.price?.toString() ?: "0"

        // val product = parentFragmentManager.fragments

        val product = arguments?.getParcelable<Product>(KeyProductBundleKey)
        Log.d("Senior_BasketDialog", "Received Product: $product")



        if (product != null) {
            // 인자가 있을 경우
            binding.tvAmount.text = product.price.toString()
            Log.d("장바구니", binding.tvAmount.text as String)

        } else {
            // 인자가 없을 경우
            binding.tvAmount.text = ""

        }
        CartStorage.productList

        // 쿠폰은 바로 보내버리기

        // 쿠폰 클릭 시, 쿠폰 창으로 넘어가기

        binding.btnCpnDSB.setOnClickListener {
            val dialogFragment = Senior_CouponPayDialog()
            dialogFragment.show(requireActivity().supportFragmentManager, "Senior_CouponPayDialog")
        }

        // 결제창 클릭했을 때, 결제로 넘어가기
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