package com.example.bbmr_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

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
            CancelDialog(viewBinding.root)
            }
//            val intent = Intent(this, BasketActivity::class.java)
//            startActivity(intent)
//            finish()

        viewBinding.btnCoupOkN.setOnClickListener {
            val CouponNum = viewBinding.tvCpnNumN.text.toString()
            Log.d("CouponNum", "CouponNum value:$CouponNum")
            Log.d("CouponType", "CouponType:${CouponNum::class?.simpleName}")
            if (CouponNum == "111") {
                ChangedSuccessDialog(viewBinding.root)
            } else if (CouponNum == "332") {
                CardSuccessDialog(viewBinding.root)
            } else {
                CouponFailDialog(viewBinding.root)
            }

        }

    }
    // 취소하시겠습니까? 란 dialog 띄우기 해당 layout.custom_dialog.xml에 있음
    fun CancelDialog(view:View) {
        val myLayout = layoutInflater.inflate(R.layout.dialog_coupon_backspace, null)
        val build = AlertDialog.Builder(view.context).apply {
            setView(myLayout)
        }
        val dialog = build.create()
        dialog.show()
        myLayout.findViewById<Button>(R.id.btnYesDCB).setOnClickListener {
            dialog.dismiss()
            val intent = Intent(view.context, Senior_BasketDialog::class.java)
            startActivity(intent)
        }
        myLayout.findViewById<Button>(R.id.btnNoDCB).setOnClickListener {
            dialog.dismiss()
        }
    }
    fun SelectedGift(ProductName: TextView, ProductPrice: TextView, name:String, price:String) {
        ProductName.text = name
        ProductPrice.text = price
    }
    fun CouponFailDialog(view:View) {
        val myLayout = layoutInflater.inflate(R.layout.dialog_coupon_fail, null)
        val build = AlertDialog.Builder(view.context).apply {
            setView(myLayout)
        }
        val dialog = build.create()
        dialog.show()
        myLayout.findViewById<Button>(R.id.btnCpnReinputDCF).setOnClickListener {
            viewBinding.tvCpnNumN.text = ""
            dialog.dismiss()
        }
    }

    fun ChangedSuccessDialog(view: View) {

        val myLayout = layoutInflater.inflate(R.layout.dialog_coupon_success, null)
        val build = AlertDialog.Builder(view.context).apply{
            setView(myLayout)
        }
        val productName = myLayout.findViewById<TextView>(R.id.tvPdName)
        val productPrice = myLayout.findViewById<TextView>(R.id.tvPdPrice)
        SelectedGift(productName, productPrice, "콘치즈달걀빵", "2,900")
        myLayout.findViewById<ImageView>(R.id.ivCoupon).setImageResource(R.drawable.corncheezeeggbread)
        val dialog = build.create()
        dialog.show()

        // 쿠폰 번호 맞는데 사용 버튼 누를 경우
        myLayout.findViewById<Button>(R.id.btnCpnUseDCS).setOnClickListener {
            // couponPrice에다가 담아서 보내기
            val CouponPrice = myLayout.findViewById<TextView>(R.id.tvPdPrice).text.toString().replace(",", "").toIntOrNull()?: 0
            // 번들에 담아서 다이얼로그프래그먼트로 보내기

            val dialogFragment = Senior_BasketDialog()
            val bundle = Bundle()
            bundle.putString("discount_price", "2,900")
            dialogFragment.arguments = bundle
            dialogFragment.show(supportFragmentManager, "Senior_BasketDialog")

            // 다이얼로그창 끄기
            dialog.dismiss()
        }
        // 쿠펀 번호 맞는데 취소 버튼 누를 경우
        myLayout.findViewById<Button>(R.id.btnCpnCnclDCS).setOnClickListener {
            dialog.dismiss()
        }
    }

    fun CardSuccessDialog(view: View) {
        val myLayout = layoutInflater.inflate(R.layout.dialog_coupon_success, null)
        val build = AlertDialog.Builder(view.context).apply{
            setView(myLayout)
        }
        val couponNum = viewBinding.tvCpnNumN.text
        val productName = myLayout.findViewById<TextView>(R.id.tvPdName)
        val productPrice = myLayout.findViewById<TextView>(R.id.tvPdPrice)
        SelectedGift(productName, productPrice, "이디야 상품권", "20,000")
        myLayout.findViewById<ImageView>(R.id.ivCoupon).setImageResource(R.drawable.giftcard)
        val dialog = build.create()
        dialog.show()
        // 쿠폰 번호 맞는데 사용 버튼 누를 경우
        myLayout.findViewById<Button>(R.id.btnCpnUseDCS).setOnClickListener {
            // couponPrice에다가 담아서 보내기
            val CouponPrice = myLayout.findViewById<TextView>(R.id.tvPdPrice).text.toString().replace(",", "").toIntOrNull()?: 0
            val dialogFragment = Senior_BasketDialog()
            val bundle = Bundle()
            bundle.putString("discount_price", "20,000")
            dialogFragment.arguments = bundle
            dialogFragment.show(supportFragmentManager, "Senior_BasketDialog")
            // 다이얼로그창 끄기
            dialog.dismiss()
        }
        // 쿠펀 번호 맞는데 취소 버튼 누를 경우
        myLayout.findViewById<Button>(R.id.btnCpnCnclDCS).setOnClickListener {
            dialog.dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        var dialog: AlertDialog? = null
        dialog?.dismiss()
    }
}