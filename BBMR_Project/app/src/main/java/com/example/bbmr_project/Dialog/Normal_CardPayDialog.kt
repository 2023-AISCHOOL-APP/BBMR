package com.example.bbmr_project.Dialog

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
            val intent = Intent(view.context, Normal_PaySuccessActivity::class.java)
            startActivity(intent)
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
        dialog.show()

        myLayout.findViewById<Button>(R.id.btnRetryPayDNCF).setOnClickListener {
            dialog.dismiss()
        }
    }
}