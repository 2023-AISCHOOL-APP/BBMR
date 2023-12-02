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
import com.bumptech.glide.Glide
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
            sendCouponToServer(CouponNum)

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
                    result?.let { resultObj ->
                        for (key in resultObj.keySet()) {
                            val resultKey = key.toInt()

                            // 추출한 key 값을 저장할 변수 (resultKey)를 사용하여 값을 추출
                            val menuObject = resultObj.getAsJsonObject(key)
                            val menu_id = menuObject.getAsJsonPrimitive("menu_id")?.asString ?: ""
                            val name = menuObject.getAsJsonPrimitive("name")?.asString ?: ""
                            val coupon_price = menuObject.getAsJsonPrimitive("price")?.asInt ?: 0
                            val menu_con = menuObject.getAsJsonPrimitive("menu_con")?.asString ?: ""
                            val size = menuObject.getAsJsonPrimitive("size")?.asInt ?: 0
                            val imageUrl = menuObject.getAsJsonPrimitive("imageUrl")?.asString ?: ""
                            val coupon_amount = menuObject.getAsJsonPrimitive("amount")?.asInt ?: 0
                            val Sub = menuObject.getAsJsonPrimitive("sub")?.asString ?: ""

                            // 이제 필요한 작업 수행 (예: UI 업데이트, 다이얼로그 표시 등)
                            // 여기서는 예시로 ChangedSuccessDialog를 호출하고 결과값을 전달
                            handleCouponResponse(resultKey, menu_id, name, coupon_price, menu_con, size, imageUrl,coupon_amount, Sub)
                        }
                    }


                } else {
                    Log.d(ContentValues.TAG, "쿠폰 전송 실패. HTTP 응답코드: ${response.code()}")
                }
            }
        })
    }

    // 이 함수에서 쿠폰 응답을 처리하고 원하는 작업을 수행
    private fun handleCouponResponse(
        resultKey: Int,
        menu_id: String,
        name: String,
        coupon_price: Int,
        menu_con: String,
        size: Int,
        imageUrl: String,
        coupon_amount: Int,
        Sub: String
    ) {
        // 여기에서 resultKey와 다른 필드 값을 사용하여 필요한 작업을 수행
        Log.d("쿠폰키", "resultKey: $resultKey")
        Log.d("메뉴 정보", "menu_id: $menu_id, name: $name, price: $coupon_price, menu_con: $menu_con, size: $size, imageUrl: $imageUrl,coupon_amount : $coupon_amount, Sub: $Sub")

        // 예시로 ChangedSuccessDialog를 호출하고 결과값을 전달
        if (resultKey==2){
            ChangedSuccessDialog(binding.root, name, coupon_price,imageUrl)
            Log.d("메뉴 id ", "$menu_id")

        }else if (resultKey==0){
            CardSuccessDialog(binding.root, coupon_amount)
        }else{
            CouponFailDialog(binding.root,Sub)
        }
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
    fun SelectedGift(ProductName: TextView, ProductPrice: TextView, name: String, coupon_price: Int) {
        ProductName.text = name
        ProductPrice.text = coupon_price.toString()
    }

    // 쿠폰 번호 불일치 다이얼로그
    fun CouponFailDialog(view: View,Sub: String) {
        val myLayout = layoutInflater.inflate(R.layout.dialog_senior_coupon_fail, null)
        val build = AlertDialog.Builder(view.context).apply {
            setView(myLayout)
        }
        val tvCpnQuery = myLayout.findViewById<TextView>(R.id.tvCpnQuery)
        tvCpnQuery.text = Sub

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
    fun ChangedSuccessDialog(view: View, name: String, coupon_price: Int, imageUrl: String) {

        val myLayout = layoutInflater.inflate(R.layout.dialog_senior_coupon_success, null)
        val build = AlertDialog.Builder(view.context).apply {
            setView(myLayout)
        }
        val productName = myLayout.findViewById<TextView>(R.id.tvPdName)
        val productPrice = myLayout.findViewById<TextView>(R.id.tvPdPrice)
        SelectedGift(productName, productPrice, name, coupon_price)
        Glide.with(this)
            .load(imageUrl)
            .into(myLayout.findViewById<ImageView>(R.id.ivCoupon))
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
            val changeProduct = Product(
                name = name,
                price = 0,
                count = 1,
                image = imageUrl
            )
            CartStorage.productList.add(changeProduct)
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
    fun CardSuccessDialog(view: View, coupon_amount : Int) {
        val myLayout = layoutInflater.inflate(R.layout.dialog_senior_coupon_success, null)
        val build = AlertDialog.Builder(view.context).apply {
            setView(myLayout)
        }
        val couponNum = binding.tvCpnNumN.text
        val productName = myLayout.findViewById<TextView>(R.id.tvPdName)
        val productPrice = myLayout.findViewById<TextView>(R.id.tvPdPrice)
        val productAmount = myLayout.findViewById<TextView>(R.id.tvPdPrice)
        SelectedGift(productName, productAmount, "이디야 상품권", coupon_amount)
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