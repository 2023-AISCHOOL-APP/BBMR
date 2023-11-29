package com.example.bbmr_project.Dialog

import NormalSelectedMenuInfo
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.bbmr_project.VO.NormalTakeOutVO
import com.example.bbmr_project.databinding.DialogNormalMenuMdBinding
import java.text.DecimalFormat

interface Normal_MenuMDDialogListener {
    fun onMDMenuAdded(normalSelectedMenuInfo: NormalSelectedMenuInfo)
}

class Normal_MenuMDDialog : DialogFragment() {
    private lateinit var binding: DialogNormalMenuMdBinding
    private var MDDialogListener: Normal_MenuMDDialogListener? = null

    // NormalTakeOutAdapter랑 연결
    companion object {
        fun newInstance(item: NormalTakeOutVO): Normal_MenuMDDialog {
            val args = Bundle().apply {
                putString("normal_img", item.img)
                putString("normal_name", item.name)
                putInt("normal_price", item.price)
            }
            val fragment = Normal_MenuMDDialog()
            fragment.arguments = args
            return fragment
        }
    }

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
        binding = DialogNormalMenuMdBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val img = arguments?.getString("normal_img")
        val name = arguments?.getString("normal_name")
        val price = arguments?.getInt("normal_price")

        if (img != null) {
            // Glide를 사용하여 이미지를 로드하고 설정
            Glide.with(requireContext())
                .load(img) // img는 이미지의 URL
                .into(binding.imgMD)
        }
        binding.tvMDName.text = name
        binding.tvMDPrice.text = formatPrice(price) // 한화표기


        // 이전으로 클릭 시 NormalTakeOut으로 이동
        binding.btnBackN.setOnClickListener {
            dismiss()
        }

        binding.btnBasketN.setOnClickListener {
            val tvCount = binding.tvCount.text.toString().toIntOrNull() ?: 0
            val priceString = price.toString()?.replace(",", "")?.replace("원", "")
            val menuOnePrice = priceString?.toIntOrNull() ?: 0 // 메뉴 하나의 가격
            val menuPrice = menuOnePrice * tvCount // 메뉴 * 수량

            val normalSelectedMenuInfo = NormalSelectedMenuInfo(
                menuImg = arguments?.getString("normal_img") ?: "",
                name = arguments?.getString("normal_name") ?: "",
                price = priceString ?: "",
                tvCount = tvCount,
                totalCost = menuPrice,
                temperature = "", // 이 값은 DessertDialog에서는 설정하지 않으므로 빈 문자열로 설정
                size = "", // 이 값은 DessertDialog에서는 설정하지 않으므로 빈 문자열로 설정
                tvCount1 = 0, // 이 값은 DessertDialog에서는 설정하지 않으므로 0으로 설정
                tvCount2 = 0, // 이 값은 DessertDialog에서는 설정하지 않으므로 0으로 설정
                tvCount3 = 0, // 이 값은 DessertDialog에서는 설정하지 않으므로 0으로 설정
                tvCount4 = 0, // 이 값은 DessertDialog에서는 설정하지 않으므로 0으로 설정
                options = listOf(), // 이 값은 DessertDialog에서는 설정하지 않으므로 빈 리스트로 설정
                optionTvCount = 0, // 이 값은 DessertDialog에서는 설정하지 않으므로 0으로 설정
                menuPrice = menuPrice
            )

            // selectedMenuInfo에 담을 코드
            MDDialogListener?.onMDMenuAdded(normalSelectedMenuInfo)
            dismiss()
        }

        // btnMinus, btnPlus 클릭 이벤트
        var tvCount = 1
        binding.btnMinus.setOnClickListener {
            if (tvCount > 1) {
                tvCount--
                binding.tvCount.text = tvCount.toString()

                // 수량에 맞춰 가격이 감소하는 코드
                val MenuPlusCountInt: Int? = binding.tvCount.text.toString().toIntOrNull()
                if (MenuPlusCountInt != null) {
                    val price: Int? = arguments?.getInt("normal_price")

                    if (price != null) {
                        val MenuPlusCountInt: Int? = binding.tvCount.text.toString().toIntOrNull()

                        if (MenuPlusCountInt != null) {
                            val priceInt: Int? = price.toString().replace("[^0-9]".toRegex(), "").toIntOrNull()

                            if (priceInt != null) {
                                val plusPrice = priceInt * MenuPlusCountInt
                                binding.tvMDPrice.text = String.format("%,d원", plusPrice)
                            }
                        }
                    }
                }
            }
            if (tvCount == 1) {
                binding.btnMinus.isEnabled = false
            }
        }
        binding.btnPlus.setOnClickListener {
            tvCount++
            binding.tvCount.text = tvCount.toString()

            binding.btnMinus.isEnabled = true

            // 수량에 맞춰 가격이 증가하는 코드
            val MenuPlusCountInt: Int? = binding.tvCount.text.toString().toIntOrNull()
            if (MenuPlusCountInt != null) {
                val price: Int? = arguments?.getInt("normal_price")

                if (price != null) {
                    val MenuPlusCountInt: Int? = binding.tvCount.text.toString().toIntOrNull()

                    if (MenuPlusCountInt != null) {
                        val priceInt: Int? = price.toString().replace("[^0-9]".toRegex(), "").toIntOrNull()

                        if (priceInt != null) {
                            val plusPrice = priceInt * MenuPlusCountInt
                            binding.tvMDPrice.text = String.format("%,d원", plusPrice)
                        }
                    }
                }
            }
        }
    }

    private fun formatPrice(price: Int?): String {
        // 1,000원 표기
        if (price != null) {
            val decimalFormat = DecimalFormat("#,###")
            return "${decimalFormat.format(price)}원"
        }
        return ""
    }

    fun setMDListener(listener: Normal_MenuMDDialogListener) {
        this.MDDialogListener = listener
    }
}