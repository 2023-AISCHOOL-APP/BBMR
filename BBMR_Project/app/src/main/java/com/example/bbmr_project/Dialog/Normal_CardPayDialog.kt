package com.example.bbmr_project.Dialog

import android.app.AlertDialog
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
import androidx.fragment.app.DialogFragment
import com.example.bbmr_project.Normal_PaySuccessActivity
import com.example.bbmr_project.R
import com.example.bbmr_project.databinding.DialogNormalCardpayBinding

class Normal_CardPayDialog : DialogFragment() {

    private lateinit var binding: DialogNormalCardpayBinding

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
        binding = DialogNormalCardpayBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.clPayFailDNC.setOnClickListener{
            PayFailDialog(view.rootView)
        }
        binding.clPaySuccessDNC.setOnClickListener {
            paymentSuccess(view.rootView)
        }




        binding.btnCardPayBack.setOnClickListener {
            dismiss()
        }
    }
    fun PayFailDialog(view:View) {
        val myLayout = layoutInflater.inflate(R.layout.dialog_normal_card_fail, null)
        val build = AlertDialog.Builder(view.context).apply {
            setView(myLayout)
        }
        val dialog = build.create()
        // 화면 밖 터치 잠금
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()

        myLayout.findViewById<Button>(R.id.btnYesBillDNPB).setOnClickListener {
            dialog.dismiss()
        }
    }


    fun paymentSuccess(view: View) {
        val myLayout = layoutInflater.inflate(R.layout.dialog_normal_payment_billquery, null)
        val build = androidx.appcompat.app.AlertDialog.Builder(view.context).apply {
            setView(myLayout)
        }
        val dialog = build.create()
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
        // 프로그래스 바 5초
        val handler = Handler()
        val progressBar: ProgressBar = myLayout.findViewById(R.id.progressBar2)
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
                } else if (progressCount == 5) {
                    if (dialog.isShowing) {
                        val intent = Intent(requireActivity(), Normal_PaySuccessActivity::class.java)
                        requireActivity().startActivity(intent)
                        dialog.dismiss()
                    }
                }
            }
        }
        // 영수증 출력
        myLayout.findViewById<Button>(R.id.btnYesBillDNPB).setOnClickListener {
            val intent = Intent(requireActivity(), Normal_PaySuccessActivity::class.java)
            requireActivity().startActivity(intent)
            dialog.dismiss()
            dismiss()
        }
        // 영수증 미출력 주문번호 발행
        myLayout.findViewById<Button>(R.id.btnNoBillDNPB).setOnClickListener {
            val intent = Intent(requireActivity(), Normal_PaySuccessActivity::class.java)
            requireActivity().startActivity(intent)
            dialog.dismiss()
            dismiss()
        }
        handler.post(runnable)
    }
}