package com.example.bbmr_project.Dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.bbmr_project.R
import com.example.bbmr_project.databinding.DialogSeniorPaymentBinding

class Senior_PaymentDialog: DialogFragment() {
    private lateinit var binding : DialogSeniorPaymentBinding

    override fun onStart() {
        super.onStart()
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
        binding = DialogSeniorPaymentBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        binding.btnCnclDSP.setOnClickListener {
            val dialogFragment = Senior_BasketDialog()
            dialogFragment.show(requireActivity().supportFragmentManager, "Senior_BasketDialog")

            dismiss()
        }
        binding.clPayFailDSP.setOnClickListener{
            paymentFail(view.rootView)
            // true를 반환하여 이벤트가 더 이상 상위 요소로 전달 X
            true
        }
        binding.clPaySuccessDSP.setOnClickListener{
            paymentSuccess(view.rootView)
        }
    }
    fun paymentFail(view: View) {
        val myLayout = layoutInflater.inflate(R.layout.dialog_payment_fail, null)
        val build = AlertDialog.Builder(view.context).apply {
            setView(myLayout)
        }
        val dialog = build.create()
        dialog.show()
        myLayout.findViewById<Button>(R.id.btnPymtRetry).setOnClickListener {
            dialog.dismiss()
        }

    }

    fun paymentSuccess(view: View) {
        val myLayout = layoutInflater.inflate(R.layout.dialog_payment_billquery, null)
        val build = AlertDialog.Builder(view.context).apply {
            setView(myLayout)
            // 화면밖 터치 했을 때 안 됨
            setCancelable(false)
        }
        val cancelButton : Button = myLayout.findViewById(R.id.btnNoBill)
        val fulltext = "아니오\n(주문번호 미발행)"

        val spannableStringBuilder = SpannableStringBuilder(fulltext)
        spannableStringBuilder.setSpan(RelativeSizeSpan(2f), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableStringBuilder.setSpan(RelativeSizeSpan(0.9f),4, fulltext.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        cancelButton.text = spannableStringBuilder


        val dialog = build.create()
        dialog.show()

        // 영수증 출력
        myLayout.findViewById<Button>(R.id.btnYesBill).setOnClickListener {
            val dialogFragment = Senior_PaySuccessDialog()
            dialogFragment.show(requireActivity().supportFragmentManager, "PaymentSuccessDialog")
            dialog.dismiss()
        }
        // 주문번호 발행
        myLayout.findViewById<Button>(R.id.btnNoBill).setOnClickListener {
            val dialogFragment = Senior_PaySuccessDialog()
            dialogFragment.show(requireActivity().supportFragmentManager, "PaymentSuccessDialog")
            dialog.dismiss()
        }
    }
}