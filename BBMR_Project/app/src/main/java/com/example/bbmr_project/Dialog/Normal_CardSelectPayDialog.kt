package com.example.bbmr_project.Dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.bbmr_project.R
import com.example.bbmr_project.databinding.DialogNormalCardSelectPayBinding
import com.example.bbmr_project.databinding.DialogNormalCardpayBinding
import com.example.bbmr_project.databinding.DialogNormalNoSelectPayBinding

class Normal_CardSelectPayDialog : DialogFragment() {
    private lateinit var binding: DialogNormalCardSelectPayBinding

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
        binding = DialogNormalCardSelectPayBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        binding.btnCnclDNCSP.setOnClickListener {
            dismiss()
        }

        binding.btn1DNCSP.setOnClickListener {
            val DialogFragment = Normal_CardPayDialog()
            DialogFragment.show(requireActivity().supportFragmentManager, "Normal_CardPayDialog")
//            dismiss()
        }
        binding.btn2DNCSP.setOnClickListener {
            val DialogFragment = Normal_CardPayDialog()
            DialogFragment.show(requireActivity().supportFragmentManager, "Normal_CardPayDialog")
//            dismiss()
        }
        binding.btn3DNCSP.setOnClickListener {
            val DialogFragment = Normal_CardPayDialog()
            DialogFragment.show(requireActivity().supportFragmentManager, "Normal_CardPayDialog")
//            dismiss()
        }
        binding.btn4DNCSP.setOnClickListener {
            val DialogFragment = Normal_CardPayDialog()
            DialogFragment.show(requireActivity().supportFragmentManager, "Normal_CardPayDialog")
//            dismiss()
        }
        binding.btn5DNCSP.setOnClickListener {
            val DialogFragment = Normal_CardPayDialog()
            DialogFragment.show(requireActivity().supportFragmentManager, "Normal_CardPayDialog")
//            dismiss()
        }
        binding.btn6DNCSP.setOnClickListener {
            val DialogFragment = Normal_CardPayDialog()
            DialogFragment.show(requireActivity().supportFragmentManager, "Normal_CardPayDialog")
//            dismiss()
        }
        binding.btn7DNCSP.setOnClickListener {
            val DialogFragment = Normal_CardPayDialog()
            DialogFragment.show(requireActivity().supportFragmentManager, "Normal_CardPayDialog")
//            dismiss()
        }
        binding.btn8DNCSP.setOnClickListener {
            val DialogFragment = Normal_CardPayDialog()
            DialogFragment.show(requireActivity().supportFragmentManager, "Normal_CardPayDialog")
//            dismiss()
        }
        binding.btn9DNCSP.setOnClickListener {
            val DialogFragment = Normal_CouponPayDialog()
            DialogFragment.show(requireActivity().supportFragmentManager, "Normal_CouponPayDialog")
        }


    }
}