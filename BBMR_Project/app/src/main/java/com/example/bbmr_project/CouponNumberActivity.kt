package com.example.bbmr_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

import androidx.appcompat.app.AlertDialog
import com.example.bbmr_project.Dialog.Senior_BasketDialog
import com.example.bbmr_project.databinding.ActivityCouponNumberBinding

class CouponNumberActivity : AppCompatActivity() {
    lateinit var viewBinding : ActivityCouponNumberBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coupon_number)
        viewBinding = ActivityCouponNumberBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.btn0.setOnClickListener{ viewBinding.tvCpnNumN.append("0") }
        viewBinding.btn1.setOnClickListener { viewBinding.tvCpnNumN.append("1") }
        viewBinding.btn2.setOnClickListener { viewBinding.tvCpnNumN.append("2") }
        viewBinding.btn3.setOnClickListener { viewBinding.tvCpnNumN.append("3") }
        viewBinding.btn4.setOnClickListener { viewBinding.tvCpnNumN.append("4") }
        viewBinding.btn5.setOnClickListener { viewBinding.tvCpnNumN.append("5") }
        viewBinding.btn6.setOnClickListener { viewBinding.tvCpnNumN.append("6") }
        viewBinding.btn7.setOnClickListener { viewBinding.tvCpnNumN.append("7") }
        viewBinding.btn8.setOnClickListener { viewBinding.tvCpnNumN.append("8") }
        viewBinding.btn9.setOnClickListener { viewBinding.tvCpnNumN.append("9") }
        viewBinding.btnBckSpce.setOnClickListener {
            if (viewBinding.tvCpnNumN.text.isNotEmpty()) {
                val newText = viewBinding.tvCpnNumN.text.substring(0, viewBinding.tvCpnNumN.text.length -1)
                viewBinding.tvCpnNumN.text = newText
            }
        }

        viewBinding.btnCoupCnclN.setOnClickListener {
            cuDialog(viewBinding.root)
            }
//            val intent = Intent(this, BasketActivity::class.java)
//            startActivity(intent)
//            finish()

        viewBinding.btnCoupOkN.setOnClickListener {
            val couponNum = viewBinding.tvCpnNumN.text
            // couponNum을 intent를 통해 다음 activity로 전송하면됨
            //val intent = Intent(this, )
        }

    }
    // 취소하시겠습니까? 란 dialog 띄우기 해당 layout.custom_dialog.xml에 있음
    fun cuDialog(view:View) {
        val myLayout = layoutInflater.inflate(R.layout.activity_alert_dialog, null)
        val build = AlertDialog.Builder(view.context).apply {
            setView(myLayout)
        }
        val dialog = build.create()
        dialog.show()
        myLayout.findViewById<Button>(R.id.btnYesAD).setOnClickListener {
            dialog.dismiss()
            val intent = Intent(view.context, Senior_BasketDialog::class.java)
            startActivity(intent)
        }
        myLayout.findViewById<Button>(R.id.btnNoAD).setOnClickListener {
            dialog.dismiss()
        }
    }

}