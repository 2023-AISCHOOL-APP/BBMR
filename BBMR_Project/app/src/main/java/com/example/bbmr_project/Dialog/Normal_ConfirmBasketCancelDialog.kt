package com.example.bbmr_project.Dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.bbmr_project.R
import com.example.bbmr_project.databinding.DialogNormalConfirmBasketCancelBinding

// Normal_ConfirmBasketCancelDialog에서 사용할 인터페이스 정의
interface ConfirmBasketCancelListener {
    fun onConfirmBasketCancel()
}

class Normal_ConfirmBasketCancelDialog : DialogFragment() {

    // 전체취소 리스너
    private var listener: ConfirmBasketCancelListener? = null
    fun setConfirmBasketCancelListener(listener: ConfirmBasketCancelListener) {
        this.listener = listener
    }

    private lateinit var binding: DialogNormalConfirmBasketCancelBinding

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
        isCancelable = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogNormalConfirmBasketCancelBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnNormalNo.setOnClickListener {
            // 다이얼로그를 닫기 전에 onConfirmBasketCancel을 호출
            listener?.onConfirmBasketCancel()
            dismiss()
        }
        binding.btnNormalYes.setOnClickListener {
            dismiss()
        }
    }
}