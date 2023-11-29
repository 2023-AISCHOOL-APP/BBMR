package com.example.bbmr_project.Dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.bbmr_project.R
import com.example.bbmr_project.databinding.DialogSeniorCouponpayBinding

class Senior_CouponPayDialog : DialogFragment() {
    private lateinit var binding: DialogSeniorCouponpayBinding
    override fun onStart() {
        super.onStart()
        val darkTransparentBlack = Color.argb((255 * 0).toInt(), 0, 0, 0)
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
        binding = DialogSeniorCouponpayBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btn0.setOnClickListener { binding.tvCpnNumN.append("0") }
        binding.btn1.setOnClickListener { binding.tvCpnNumN.append("1") }
        binding.btn2.setOnClickListener { binding.tvCpnNumN.append("2") }
        binding.btn3.setOnClickListener { binding.tvCpnNumN.append("3") }
        binding.btn4.setOnClickListener { binding.tvCpnNumN.append("4") }
        binding.btn5.setOnClickListener { binding.tvCpnNumN.append("5") }
        binding.btn6.setOnClickListener { binding.tvCpnNumN.append("6") }
        binding.btn7.setOnClickListener { binding.tvCpnNumN.append("7") }
        binding.btn8.setOnClickListener { binding.tvCpnNumN.append("8") }
        binding.btn8.setOnClickListener { binding.tvCpnNumN.append("9") }
        binding.btnBckSpce.setOnClickListener {
            if (binding.tvCpnNumN.text.isNotEmpty()) {
                val newText = binding.tvCpnNumN.text.substring(0, binding.tvCpnNumN.text.length - 1)
                binding.tvCpnNumN.text = newText
            }
        }

        binding.btnCpnOkN.setOnClickListener {
            val CouponNum = binding.tvCpnNumN.text.toString()
            Log.d("CouponNum", "CouponNum value:$CouponNum")
            Log.d("CouponType", "CouponType:${CouponNum::class?.simpleName}")
            if (CouponNum == "331210188802") {
                ChangedSuccessDialog(binding.root)
            } else if (CouponNum == "502341578874") {
                CardSuccessDialog(binding.root)
            } else {
                CouponFailDialog(binding.root)
            }

        }
        binding.btnCpnCnclN.setOnClickListener {
            CancelDialog(view.rootView)

        }


    }

    // 쿠폰창에서 취소 다이얼로그
    fun CancelDialog(view: View) {
        val myLayout = layoutInflater.inflate(R.layout.dialog_senior_coupon_backspace, null)
        val build = AlertDialog.Builder(view.context).apply {
            setView(myLayout)
        }
        val dialog = build.create()
        // 화면 밖 터치 잠금
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
        myLayout.findViewById<Button>(R.id.btnYesDSCB).setOnClickListener {
            dialog.dismiss()
            val dialogFragment = SeniorBasketDialog()
            dialogFragment.show(childFragmentManager, "Senior_BasketDialog")
            dismiss()

        }
        myLayout.findViewById<Button>(R.id.btnNoDSCB).setOnClickListener {
            dialog.dismiss()
        }
    }

    // 각 이름과 가격을 가져오는 함수
    fun SelectedGift(ProductName: TextView, ProductPrice: TextView, name: String, price: String) {
        ProductName.text = name
        ProductPrice.text = price
    }

    // 쿠폰 번호 불일치 다이얼로그
    fun CouponFailDialog(view: View) {
        val myLayout = layoutInflater.inflate(R.layout.dialog_senior_coupon_fail, null)
        val build = AlertDialog.Builder(view.context).apply {
            setView(myLayout)
        }
        val dialog = build.create()
        // 화면 밖 터치 잠금
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
        myLayout.findViewById<Button>(R.id.btnRetryCpnDSCF).setOnClickListener {
            binding.tvCpnNumN.text = ""
            dialog.dismiss()
        }
    }

    // 쿠폰 번호 맞고, 쓸 건지 다이얼로그
    fun ChangedSuccessDialog(view: View) {

        val myLayout = layoutInflater.inflate(R.layout.dialog_senior_coupon_success, null)
        val build = AlertDialog.Builder(view.context).apply {
            setView(myLayout)
        }
        val productName = myLayout.findViewById<TextView>(R.id.tvPdName)
        val productPrice = myLayout.findViewById<TextView>(R.id.tvPdPrice)
        SelectedGift(productName, productPrice, "콘치즈달걀빵", "2,900")
        myLayout.findViewById<ImageView>(R.id.ivCoupon)
            .setImageResource(R.drawable.corncheezeeggbread)
        val dialog = build.create()
        // 화면 밖 터치 잠금
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()

        // 교환권 성공 및 사용 버튼 누를 경우
        myLayout.findViewById<Button>(R.id.btnCpnUseDSCS).setOnClickListener {
            // couponPrice에다가 담아서 보내기
            val CouponPrice =
                myLayout.findViewById<TextView>(R.id.tvPdPrice).text.toString().replace(",", "")
                    .toIntOrNull() ?: 0
            // 번들에 담아서 다이얼로그프래그먼트로 보내기

            val dialogFragment = SeniorBasketDialog()
            val bundle = Bundle()
            bundle.putString("discount_price", "2,900")
            dialogFragment.arguments = bundle
            dialogFragment.show(childFragmentManager, "Senior_BasketDialog")

            // 다이얼로그창 끄기
            dialog.dismiss()
        }
        //  취소 버튼
        myLayout.findViewById<Button>(R.id.btnCpnCnclDSCS).setOnClickListener {
            dialog.dismiss()
        }
    }

    // 금액권 성공 및 사용 버튼 누를 경우
    fun CardSuccessDialog(view: View) {
        val myLayout = layoutInflater.inflate(R.layout.dialog_senior_coupon_success, null)
        val build = AlertDialog.Builder(view.context).apply {
            setView(myLayout)
        }
        val couponNum = binding.tvCpnNumN.text
        val productName = myLayout.findViewById<TextView>(R.id.tvPdName)
        val productPrice = myLayout.findViewById<TextView>(R.id.tvPdPrice)
        SelectedGift(productName, productPrice, "이디야 상품권", "20,000")
        myLayout.findViewById<ImageView>(R.id.ivCoupon).setImageResource(R.drawable.giftcard)
        val dialog = build.create()
        // 화면 밖 터치 잠금
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
        // 쿠폰 번호 맞는데 사용 버튼 누를 경우
        myLayout.findViewById<Button>(R.id.btnCpnUseDSCS).setOnClickListener {
            // couponPrice에다가 담아서 보내기
            val CouponPrice =
                myLayout.findViewById<TextView>(R.id.tvPdPrice).text.toString().replace(",", "")
                    .toIntOrNull() ?: 0
            val dialogFragment = SeniorBasketDialog()
            val bundle = Bundle()
            bundle.putString("discount_price", "20,000")
            dialogFragment.arguments = bundle
            dialogFragment.show(childFragmentManager, "Senior_BasketDialog")
            // 다이얼로그창 끄기
            dialog.dismiss()
            // 쿠폰 화면 끄기
            dismiss()
        }
        // 쿠펀 번호 맞는데 취소 버튼 누를 경우
        myLayout.findViewById<Button>(R.id.btnCpnCnclDSCS).setOnClickListener {
            dialog.dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        var dialog: AlertDialog? = null
        dialog?.dismiss()
    }
}
