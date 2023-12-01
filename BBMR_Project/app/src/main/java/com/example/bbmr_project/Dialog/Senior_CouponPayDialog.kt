package com.example.bbmr_project.Dialog

import android.content.ContentValues
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
import com.example.bbmr_project.CartStorage
import com.example.bbmr_project.Product
import com.example.bbmr_project.R
import com.example.bbmr_project.RetrofitAPI.RetrofitAPI
import com.example.bbmr_project.databinding.DialogSeniorCouponpayBinding
import com.google.gson.JsonObject
import okhttp3.FormBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Senior_CouponPayDialog : DialogFragment() {
    private lateinit var binding: DialogSeniorCouponpayBinding
    lateinit var mRetrofitAPI: RetrofitAPI
    lateinit var mRetrofit: Retrofit
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
        setRetrofit()
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
        binding.btn9.setOnClickListener { binding.tvCpnNumN.append("9") }
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

    // 쿠폰번호 서버로 보내고 응답값 받기
        private fun sendCouponToServer(CouponNum: String) {
        val requestBody = FormBody.Builder()
            .add("coupon", CouponNum)
            .build()
        val callSendCoupon = mRetrofitAPI.sendCoupon(requestBody)
        callSendCoupon.enqueue(object : retrofit2.Callback<JsonObject> {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                t.printStackTrace()
                Log.d(ContentValues.TAG, "쿠폰 전송 에러입니다. => ${t.message.toString()}")
            }
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    val result = response.body()?.getAsJsonObject("result") // 서버 응답 결과
                    Log.d(ContentValues.TAG, "쿠폰 전송 결과 => $result")

                } else {
                    Log.d(ContentValues.TAG, "쿠폰 전송 실패. HTTP 응답코드: ${response.code()}")
                }
            }
        })
    }
    private fun setRetrofit() {
        mRetrofit = Retrofit
            .Builder()
            .baseUrl(getString(R.string.baseUrl))   // baseUrl은 strings.xml에서 플라스크서버 IP 확인 후 수정
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        mRetrofitAPI = mRetrofit.create(RetrofitAPI::class.java)
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

            val bread = Product(
                name = "콘치즈 계란빵",
                price = 0,
                count = 1,
                image = "https://shop-phinf.pstatic.net/20231102_156/1698909003769FXQYp_JPEG/25827574172676864_100459445.jpg?type=m510"
            )
            CartStorage.productList.add(bread)
            // 변한 값을 UI에 바꿔주는 코드
            CartStorage.notifyProductListChanged()

            val dialogFragment = SeniorBasketDialog()
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
