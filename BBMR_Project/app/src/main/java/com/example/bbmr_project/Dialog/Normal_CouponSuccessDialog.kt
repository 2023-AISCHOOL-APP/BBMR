package com.example.bbmr_project.Dialog

import NormalSelectedMenuInfo
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.bbmr_project.Normal_Fragment.adapters.NormalSelectBasketAdapter
import com.example.bbmr_project.Normal_PaySuccessActivity
import com.example.bbmr_project.Normal_TakeOutActivity
import com.example.bbmr_project.databinding.DialogNormalCouponSuccessBinding

class Normal_CouponSuccessDialog : DialogFragment() {
    private lateinit var binding: DialogNormalCouponSuccessBinding
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

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogNormalCouponSuccessBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // couponPay 프래그먼트에서 담아놓은 데이터 불러오기



        val receivedCouponNum = arguments?.getString("CouponNum")

        if (receivedCouponNum == "3333 5582 4458") {

            binding.tvCpnPriceDNCS.text = "2,000"
            // 교환권을 추가할 공간
        } else if (receivedCouponNum == "1") {
            // 금액권을 추가할 공간
        } else {

        }

        // "이전으로" 버튼 로직
        binding.btnCpnCnclDNCS.setOnClickListener {
            dismiss()
        }
        // 쿠폰 사용 --> 장바구니로 돌아가기
        binding.btnCpnUseDNCS.setOnClickListener {
            showSelectPayDialog()
        }
    }

    private fun showSelectPayDialog() {
        // 현재 선택된 항목 리스트 가져오기
        val currentList = (activity as? Normal_TakeOutActivity)?.getNormalSelectBasketAdapter()?.getCurrentList()
        val bundle = Bundle()
        val selectPay = Normal_SelectPayDialog()
        // 형변환해서 가져오기
        if (currentList != null) {
            bundle.putString("coupon", binding.tvCpnPriceDNCS.text.toString())
            selectPay.arguments = bundle
            val dialog = Normal_SelectPayDialog.newInstance(currentList, binding.tvCpnPriceDNCS.text.toString())
            dialog.show(requireActivity().supportFragmentManager, "Normal_SelectPayDialog")
        }
//        binding.tvCpnPriceDNCS.text = "2000"
    }
}