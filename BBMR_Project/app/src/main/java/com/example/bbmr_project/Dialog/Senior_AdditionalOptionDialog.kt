package com.example.bbmr_project.Dialog

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.bbmr_project.CartStorage
import com.example.bbmr_project.Product
import com.example.bbmr_project.R
import com.example.bbmr_project.Senior_TakeOutActivity
import com.example.bbmr_project.databinding.DialogSeniorAdditionalOptionBinding

class Senior_AdditionalOptionDialog : DialogFragment() {
    private lateinit var binding : DialogSeniorAdditionalOptionBinding

    override fun onStart() {
        super.onStart()
        val darkTransparentBlack = Color.argb((255 * 0.6).toInt(), 0, 0, 0)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(darkTransparentBlack))
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog?.window?.setDimAmount(0.4f)
//        isCancelable = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogSeniorAdditionalOptionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // ------ 추가 옵션 코드 시작 ------
        var size : Boolean = false
        var sugar: Boolean = false
        var cream: Boolean = false
        binding.cbSizeDSAO.setOnCheckedChangeListener { _, isChecked ->
            size = isChecked
        }
        binding.cbSugarDSAO.setOnCheckedChangeListener { _, isChecked ->
            sugar = isChecked

        }
        binding.cbCreamDSAO.setOnCheckedChangeListener { _, isChecked ->
            cream = isChecked
        }
        // ------ 추가 옵션 코드 끝 ------

        // ------ 추천 메뉴 코드 시작 ------
        binding.btnRecommend1.setOnClickListener {
            val bread = Product(
                name = "데블스 초코케이크",
                price = 4800,
                count = 1,
                temperature = false,
                size = false,
                sugar = false,
                cream = false,
            )
            CartStorage.productList.add(bread)
            SuggestionProductAddDialog(view.rootView)
        }
        binding.btnRecommend2.setOnClickListener {
            val bread = Product(
                name = "콘치즈 계란빵",
                price = 2900,
                count = 1,
                temperature = false,
                size = false,
                sugar = false,
                cream = false,
            )
            CartStorage.productList.add(bread)
            SuggestionProductAddDialog(view.rootView)
        }
        binding.btnRecommend3.setOnClickListener {
            val bread = Product(
                name = "허니 카라멜 브레드",
                price = 4800,
                count = 1,
                temperature = false,
                size = false,
                sugar = false,
                cream = false,
            )
            CartStorage.productList.add(bread)
            SuggestionProductAddDialog(view.rootView)
        }
        // ------ 추천 메뉴 코드 끝 ------

        // ------ 이전, 선택 완료 코드 시작 ------
        binding.btnCnclAddiOpDSAO.setOnClickListener {
            val bundle = arguments
            if (bundle != null) {
                val customOption = bundle.getSerializable("product_option") as Product
                Log.d("Product라는 data List", "${customOption}")
                val bundle2 = Bundle()
                val dialogFragment = Senior_MenuDialog()
                bundle2.putSerializable("final_product", customOption)
                dialogFragment.arguments = bundle2
                dialogFragment.show(childFragmentManager, "Senior_MenuDialog")
            }
            dismiss()
        }

        binding.btnOkAddiOpDSAO.setOnClickListener {
            // ------ Product라는 Class 담아오기 코드 시작 ------
            val bundle = arguments
            if (bundle != null) {
                val customOption = bundle.getSerializable("product_option") as Product

                // ------ 옵션 선택 시 금액 추가(합산) 코드 시작 ------
                var addprice : Int = customOption.price
                if (size == true) {
                    addprice = addprice + 500
                }
                if (sugar == true) {
                    addprice = addprice + 300
                }
                if (cream == true) {
                    addprice = addprice + 300
                }
                // ------ 옵션 선택 시 금액 추가(합산) 코드 끝   ------

                // ------ 객체 Product 값 수정하기 시작 ------
                customOption?.let { product ->
                    val finalproduct = product.copy(
                        price = addprice,
                        size = size,
                        sugar = sugar,
                        cream = cream
                    )
                    Log.d("Product라는 data List", "${finalproduct}")
                    CartStorage.productList.add(finalproduct)
                // ------ 객체 Product 값 수정하기 끝 ------
                }
//                val SeniorMenuDialogFragment = Senior_MenuDialog()
//                SeniorMenuDialogFragment.dismiss()
                val intent = Intent(view.context, Senior_TakeOutActivity::class.java)
                startActivity(intent)

            }
            // ------ Product라는 Class 담아오기 코드 끝 ------
            dismiss()
        }
        // ------ 이전, 선택 완료 코드 끝 ------

    }
    interface ProductDataListener{
        fun onProductDataReceived(product: Product)
    }
    fun SuggestionProductAddDialog(view:View) {
        val myLayout = layoutInflater.inflate(R.layout.dialog_senior_menu_add, null)
        val build = AlertDialog.Builder(view.context).apply {
            setView(myLayout)
            // 화면밖 터치 했을 때 안 됨
            setCancelable(false)
        }

        val dialog = build.create()
        // 화면 밖 터치 잠금
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()



        myLayout.findViewById<Button>(R.id.btnCnclDSMA).setOnClickListener {
            dialog.dismiss()
        }
        myLayout.findViewById<Button>(R.id.btnOkDSMA).setOnClickListener {
            dialog.dismiss()
            val dialogFragment = Senior_BasketDialog()
            dialogFragment.show(childFragmentManager, "Senior_BasketDialog")
        }
    }
}