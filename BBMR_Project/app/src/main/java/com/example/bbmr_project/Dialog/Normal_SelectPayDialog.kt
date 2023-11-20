package com.example.bbmr_project.Dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.bbmr_project.R
import com.example.bbmr_project.databinding.DialogNormalSelectPayBinding

class Normal_SelectPayDialog : DialogFragment() {
    private lateinit var binding: DialogNormalSelectPayBinding

    override fun onStart() {
        super.onStart()
        // 검은색에 80% 투명도를 적용한 Color
        val darkTransparentBlack = Color.argb((255 * 0.6).toInt(), 0, 0, 0)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(darkTransparentBlack))
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog?.window?.setDimAmount(0.4f)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogNormalSelectPayBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 이전으로 클릭 시 NormalTakeOut으로 이동
        binding.btnNormalSelectPayBack.setOnClickListener {
            dismiss()
        }

        // btnSelectPayNext 클릭 시 선택된 라디오 버튼에 따라 다이얼로그로 이동
        binding.btnSelectPayNext.setOnClickListener {
            when (binding.btnNormalSelectPay.checkedRadioButtonId) {
                R.id.btnNormalCardPay -> showNormalCardPayDialog()
                R.id.btnNormalCouponPay -> showNormalCouponPayDialog()
                else -> {
                    // 선택된 라디오 버튼이 없는 경우
                    showNormalNoSelectPayDialog()
                }
            }
        }
    }
    private fun showNormalCardPayDialog() {
        val normalCardPayDialog = Normal_CardPayDialog()
        normalCardPayDialog.show(requireActivity().supportFragmentManager, "Normal_CardPayDialog")
    }

    private fun showNormalCouponPayDialog() {
        val normalCouponPayDialog = Normal_CouponPayDialog()
        normalCouponPayDialog.show(requireActivity().supportFragmentManager, "Normal_CouponPayDialog")
    }
    private fun showNormalNoSelectPayDialog() {
        val normalNoSelectPayDialog = Normal_NoSelectPayDialog()
        normalNoSelectPayDialog.show(requireActivity().supportFragmentManager, "Normal_NoSelectPayDialog")
    }
}
