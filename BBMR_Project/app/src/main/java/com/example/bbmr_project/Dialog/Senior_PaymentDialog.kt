package com.example.bbmr_project.Dialog

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.bbmr_project.Normal_PaySuccessActivity
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
        isCancelable = false
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
        val myLayout = layoutInflater.inflate(R.layout.dialog_senior_payment_fail, null)
        val build = AlertDialog.Builder(view.context).apply {
            setView(myLayout)
        }
        val dialog = build.create()
        // 화면 밖 터치 잠금
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
        myLayout.findViewById<Button>(R.id.btnPymtRetry).setOnClickListener {
            dialog.dismiss()
        }

    }

    fun paymentSuccess(view: View) {
        val myLayout = layoutInflater.inflate(R.layout.dialog_senior_payment_billquery, null)
        val build = AlertDialog.Builder(view.context).apply {
            setView(myLayout)
            // 화면밖 터치 했을 때 안 됨
            setCancelable(false)
        }
        val cancelButton : Button = myLayout.findViewById(R.id.btnNoBillDSPB)
        val fulltext = "아니오\n(주문번호 미발행)"

        val spannableStringBuilder = SpannableStringBuilder(fulltext)
        spannableStringBuilder.setSpan(RelativeSizeSpan(2.3f), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableStringBuilder.setSpan(RelativeSizeSpan(0.8f),4, fulltext.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        cancelButton.text = spannableStringBuilder


        val dialog = build.create()
        // 화면 밖 터치 잠금
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()

        // ------ 프로그레스 바 코드 시작 -------
        val handler = Handler()
        val progressBar: ProgressBar = myLayout.findViewById(R.id.progressBar3)
        progressBar.max = 100
        progressBar.progress = 0
        val progressIncreaseAmount = 20
        val runnable = object : Runnable {
            var progressCount = 0
            override fun run() {
                if (progressCount < 5) {
                    progressBar.incrementProgressBy(progressIncreaseAmount)
                    progressCount++
                    handler.postDelayed(this, 1000)
                } else {
                    val dialogFragment = Senior_PaySuccessDialog()
                    dialogFragment.show(requireActivity().supportFragmentManager, "PaymentSuccessDialog")
                    dialog.dismiss()
                    dismiss()
                }
            }
        }
        handler.post(runnable)
        // ------ 프로그레스 바 코드 끝 -------

        // 영수증 출력
        myLayout.findViewById<Button>(R.id.btnYesBillDSPB).setOnClickListener {
            val dialogFragment = Senior_PaySuccessDialog()
            dialogFragment.show(requireActivity().supportFragmentManager, "PaymentSuccessDialog")
            dialog.dismiss()
        }
        // 주문번호 발행
        myLayout.findViewById<Button>(R.id.btnNoBillDSPB).setOnClickListener {
            val dialogFragment = Senior_PaySuccessDialog()
            dialogFragment.show(requireActivity().supportFragmentManager, "PaymentSuccessDialog")
            dialog.dismiss()
        }
    }
}