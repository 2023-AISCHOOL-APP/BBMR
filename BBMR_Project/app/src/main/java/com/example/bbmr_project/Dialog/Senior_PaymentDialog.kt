package com.example.bbmr_project.Dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.RelativeSizeSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.bbmr_project.CartStorage.productList
import com.example.bbmr_project.Product
import com.example.bbmr_project.R
import com.example.bbmr_project.RetrofitAPI.FlaskSendRes
import com.example.bbmr_project.RetrofitAPI.MenuData
import com.example.bbmr_project.RetrofitAPI.OrderResponseCallBack
import com.example.bbmr_project.databinding.DialogSeniorPaymentBinding

class Senior_PaymentDialog: DialogFragment(), OrderResponseCallBack {
    private lateinit var binding : DialogSeniorPaymentBinding
    private var orderNumber: Int = 0

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


    // SendOrderTask 인스턴스 생성
////            val sendOrderTask = FlaskSendRes(this, getString(R.string.baseUrl))
////            // ------ 결제 완료 후 영수증 여부 Dialog 출력하면서 하단 코드들과
////            //
////            // 주문 정보 전송 (예시)
////            val menu_ids = listOf(MenuData(121, 2), MenuData(162, 1))
////            val total_amount = 18000 // 쿠폰 값 제외 하고 값
////            val coupon = "qwerasdfzxcv" // 쿠폰번호
////            val discount = 2500 // 쿠폰했을 때 값
////            sendOrderTask.sendOrder(menu_ids, total_amount, coupon, discount) // sendOrderTak
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
        val amount = arguments?.getString("amount").toString().toIntOrNull() ?: 0

        // ------ 결제 정보 전송 코드 시작 ------
        val sendOrderTask = FlaskSendRes(view.context, getString(R.string.baseUrl), this)
            // ------ 결제 완료 후 영수증 여부 Dialog 출력하면서 하단 코드들과

            // 주문 정보 전송 (예시) // MenuData(121, 2), MenuData(162, 1)
            val menu_ids = listOf(
                productList.forEach { p: Product ->
                    MenuData(p.id, p.count)
                }
            )
            Log.d("결제 메뉴 리스트 ","'$menu_ids")
            val total_amount = amount // 쿠폰 값 제외 하고 값
            val coupon = null // 쿠폰번호
            val discount = null // 쿠폰했을 때 값
            sendOrderTask.sendOrder(menu_ids, total_amount, coupon, discount) // sendOrderTak
        // ------ 결제 정보 전송 코드 끝 ------

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
                } else if (progressCount == 5) {
                    if (dialog.isShowing) {
                        val dialogFragment = SeniorPaySuccessDialog()
                        val bundle = Bundle()
                        bundle.putInt("주문번호", orderNumber)
                        dialogFragment.arguments = bundle
                        dialogFragment.show(requireActivity().supportFragmentManager, "PaymentSuccessDialog")
                        dialog.dismiss()
                    }
                }
            }
        }
        handler.post(runnable)
        // ------ 프로그레스 바 코드 끝 -------

        // 영수증 출력
        myLayout.findViewById<Button>(R.id.btnYesBillDSPB).setOnClickListener {
            val dialogFragment = SeniorPaySuccessDialog()
            val bundle = Bundle()
            bundle.putInt("주문번호", orderNumber)
            dialogFragment.arguments = bundle
            dialogFragment.show(requireActivity().supportFragmentManager, "PaymentSuccessDialog")
            dialog.dismiss()
            dismiss()
        }
        // 주문번호 발행
        myLayout.findViewById<Button>(R.id.btnNoBillDSPB).setOnClickListener {
            val dialogFragment = SeniorPaySuccessDialog()
            val bundle = Bundle()
            bundle.putInt("주문번호", orderNumber)
            dialogFragment.arguments = bundle
            dialogFragment.show(requireActivity().supportFragmentManager, "PaymentSuccessDialog")
            dialog.dismiss()
            dismiss()
        }
    }

    override fun onOrderResponse(orderNumber: Int) {
        this.orderNumber = orderNumber
    }
}