package com.example.bbmr_project.Dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.bbmr_project.R
import com.example.bbmr_project.databinding.DialogNormalCardpayBinding
import com.example.bbmr_project.databinding.DialogNormalCouponSuccessBinding
import com.example.bbmr_project.databinding.DialogNormalCouponpayBinding

class Normal_CouponPayDialog : DialogFragment() {

    private lateinit var binding: DialogNormalCouponpayBinding

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
        binding = DialogNormalCouponpayBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btn0N.setOnClickListener { binding.tvCpnNumDNC.append("0") }
        binding.btn1N.setOnClickListener { binding.tvCpnNumDNC.append("1") }
        binding.btn2N.setOnClickListener { binding.tvCpnNumDNC.append("2") }
        binding.btn3N.setOnClickListener { binding.tvCpnNumDNC.append("3") }
        binding.btn4N.setOnClickListener { binding.tvCpnNumDNC.append("4") }
        binding.btn5N.setOnClickListener { binding.tvCpnNumDNC.append("5") }
        binding.btn6N.setOnClickListener { binding.tvCpnNumDNC.append("6") }
        binding.btn7N.setOnClickListener { binding.tvCpnNumDNC.append("7") }
        binding.btn8N.setOnClickListener { binding.tvCpnNumDNC.append("8") }
        binding.btn9N.setOnClickListener { binding.tvCpnNumDNC.append("9") }
        binding.btnBckSpceDNC.setOnClickListener {
            if (binding.tvCpnNumDNC.text.isNotEmpty()) {
                val newText = binding.tvCpnNumDNC.text.substring(0, binding.tvCpnNumDNC.text.length -1)
                binding.tvCpnNumDNC.text = newText
            }
        } // 쿠폰 번호 키패드
        binding.btnClearDNC.setOnClickListener {
            binding.tvCpnNumDNC.text = ""
        }

        // 버튼 클릭 리스너
        binding.btnCpnCnclDNC.setOnClickListener {
            dismiss()
        }
        // 성공 시에 프래그먼트 이동
        binding.btnCpnOkDNC.setOnClickListener {
            val CouponNum = binding.tvCpnNumDNC.text.toString()
            if (CouponNum == "333355824458") {
                // 쿠폰 num 1 일경우 다이얼로그프래그먼트 이동
                // argument에 데이터를 담음
                val dialogFragment = Normal_CouponSuccessDialog()
                val bundle = Bundle()
                // 입력한 쿠폰 번호만 가져가기,
                bundle.putString("CouponNum", binding.tvCpnNumDNC.text.toString().chunked(4).joinToString(" "))
                dialogFragment.arguments = bundle
                dialogFragment.show(requireActivity().supportFragmentManager, "Normal_CouponSuccessDialog")

                dismiss()
            }  else {
                CouponFailDialog(binding.root)
            }
        }

    }

    fun CouponFailDialog(view:View) {
        val myLayout = layoutInflater.inflate(R.layout.dialog_normal_coupon_fail, null)
        val build = AlertDialog.Builder(view.context).apply {
            setView(myLayout)
        }
        val dialog = build.create()
        // 화면 밖 터치 잠금
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
        myLayout.findViewById<Button>(R.id.btnRetryCpnDNCF).setOnClickListener {
            binding.tvCpnNumDNC.text = ""
            dialog.dismiss()
        }
    }


}