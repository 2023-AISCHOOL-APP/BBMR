package com.example.bbmr_project.Dialog

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
import androidx.fragment.app.createViewModelLazy
import com.example.bbmr_project.CartStorage
import com.example.bbmr_project.Menu.MenuListViewModel
import com.example.bbmr_project.Product
import com.example.bbmr_project.R
import com.example.bbmr_project.databinding.DialogSeniorAdditionalOptionBinding

class Senior_AdditionalOptionDialog : DialogFragment() {
    private lateinit var binding : DialogSeniorAdditionalOptionBinding

    override fun onStart() {
        super.onStart()
        val darkTransparentBlack = Color.argb((255 * 0).toInt(), 0, 0, 0)
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
//        var size  = CartStorage.menuList.size
        var size  = 1
        var sugar: Boolean = false
        var cream: Boolean = false
        binding.cbSizeDSAO.setOnCheckedChangeListener { _, isChecked ->
            size = 2
        }
        binding.cbSugarDSAO.setOnCheckedChangeListener { _, isChecked ->
            sugar = true
        }
        binding.cbCreamDSAO.setOnCheckedChangeListener { _, isChecked ->
            cream = true
        }

        // ------ 추가 옵션 코드 끝 ------

        // ------ 추천 메뉴 코드 시작 ------
         // CartStorage.addProduct 사용해야 값이 들어감
        binding.btnRecommend1.setOnClickListener {
            val bread = Product(
                name = "데블스 초코케이크",
                price = 4500,
                count = 1,
                image = "https://shop-phinf.pstatic.net/20221223_97/16717893245683z1ju_PNG/I000022253.jpg?type=m510"
            )
            SuggestionProductAddDialog(view.rootView, bread)
        }
        binding.btnRecommend2.setOnClickListener {
            val bread = Product(
                name = "콘치즈 계란빵",
                price = 2900,
                count = 1,
                image = "https://shop-phinf.pstatic.net/20231102_156/1698909003769FXQYp_JPEG/25827574172676864_100459445.jpg?type=m510"
            )
            SuggestionProductAddDialog(view.rootView,bread)
        }
        binding.btnRecommend3.setOnClickListener {
            val bread = Product(
                name = "허니 카라멜 브레드",
                price = 4800,
                count = 1,
                image = "https://shop-phinf.pstatic.net/20221226_171/1672022807862vCIQQ_PNG/I000022726.jpg?type=m510"
            )
            SuggestionProductAddDialog(view.rootView, bread)
        }
        // ------ 추천 메뉴 코드 끝 ------

        // ------ 이전, 선택 완료 코드 시작 ------
        binding.btnCnclAddiOpDSAO.setOnClickListener {
            dismiss()
        }

        binding.btnOkAddiOpDSAO.setOnClickListener {
            // ------ Product라는 Class 담아오기 코드 시작 ------
            dismiss()
            val bundle = arguments
            if (bundle != null) {
                val customOption = bundle.getSerializable("product_option") as Product
                Log.d("추가옵션 진입", "${customOption}")
                // ------ 옵션 선택 시 금액 추가(합산) 코드 시작 ------
                var addprice : Int = customOption.price
                val count : Int = customOption.count
                if (size == 2) {
                    addprice += (500 * count)
                }
                if (sugar == true) {
                    addprice += (300 * count)
                }
                if (cream == true) {
                    addprice += (500 * count)
                }
                // ------ 옵션 선택 시 금액 추가(합산) 코드 끝   ------

                // ------ 객체 Product 값 수정하기 시작 ------
                customOption?.let { product ->
                    val finalproduct = product.copy(
                        price = addprice,
                        sugar = sugar,
                        cream = cream
                    )
                    Log.d("Product라는 data List", "${finalproduct}")
                    val DialogFragment = SeniorMenuDialog()
                    val bundle = Bundle()
                    bundle.putSerializable("커스텀_옵션_완료", finalproduct)

                    DialogFragment.arguments = bundle
                    DialogFragment.show(childFragmentManager, "Senior_MenuDialog")
                    // ------ 객체 Product 값 수정하기 끝 ------
                }
//                val intent = Intent(view.context, Senior_TakeOutActivity::class.java)
//                startActivity(intent)
            }
            // ------ Product라는 Class 담아오기 코드 끝 ------
//            dismiss()
        }
        // ------ 이전, 선택 완료 코드 끝 ------

    }
    private fun SuggestionProductAddDialog(view:View, product: Product) {
        val myLayout = layoutInflater.inflate(R.layout.dialog_senior_menu_add, null)
        val build = AlertDialog.Builder(view.context).apply {
            setView(myLayout)
            // 화면밖 터치 했을 때 안 됨
//            setCancelable(false)
        }

        val dialog = build.create()
        // 화면 밖 터치 잠금
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()

        myLayout.findViewById<Button>(R.id.btnCnclDSMA).setOnClickListener {
            dialog.dismiss()
        }

        // ------ 추천 상품 담겨야 하는 곳 코드 시작 ------
        myLayout.findViewById<Button>(R.id.btnOkDSMA).setOnClickListener {
            // 추가메뉴 -> 추천메뉴 -> 예 버튼을 누르면 값을 추가하는 코드
            CartStorage.productList.add(product)
            // 변한 값을 UI에 바꿔주는 코드
            CartStorage.notifyProductListChanged()

        // ------ 추천 상품 담겨야 하는 곳 코드 끝 ------

        dialog.dismiss()
        }
    }
}