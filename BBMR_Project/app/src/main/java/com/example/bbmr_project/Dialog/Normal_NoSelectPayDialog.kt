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
import com.example.bbmr_project.databinding.DialogNormalNoSelectPayBinding
import com.example.bbmr_project.databinding.DialogNormalSelectPayBinding

class Normal_NoSelectPayDialog : DialogFragment() {

    private lateinit var binding: DialogNormalNoSelectPayBinding

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
        binding = DialogNormalNoSelectPayBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnNormalClose.setOnClickListener {
            dismiss()
        }
    }
}