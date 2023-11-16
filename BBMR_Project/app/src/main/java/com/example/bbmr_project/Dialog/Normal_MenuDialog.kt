package com.example.bbmr_project.Dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.bbmr_project.VO.NormalTakeOutVO
import com.example.bbmr_project.databinding.DialogNormalMenuBinding

// 메뉴 선택 시 출력되는 Dialog

class Normal_MenuDialog : DialogFragment() {

    private lateinit var binding: DialogNormalMenuBinding

    // NormalTakeOutAdapter랑 연결
    companion object {
        fun newInstance(item: NormalTakeOutVO): Normal_MenuDialog {
            val args = Bundle().apply {
                putInt("normal_img", item.img)
                putString("normal_name", item.name)
                putString("normal_price", item.price)
            }
            val fragment = Normal_MenuDialog()
            fragment.arguments = args
            return fragment
        }
    }


    override fun onStart() {
        super.onStart()
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
        binding = DialogNormalMenuBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val img = arguments?.getInt("normal_img")
        val name = arguments?.getString("normal_name")
        val price = arguments?.getString("normal_price")

        if (img != null) {binding.menuImg.setImageResource(img)}
        binding.name.text = name
        binding.price.text = price



        // 이전으로 클릭 시 NormalTakeOut으로 이동
        binding.btnBackN.setOnClickListener {
            dismiss()
        }

        // 메뉴담기 클릭 시 부모 Activity에 값 전달
        binding.btnBasketN.setOnClickListener {
//            // 선택한 메뉴의 이미지 리소스 ID를 가져옴
//            val selectedImg = arguments?.getInt("normal_img") ?: 0
//            // 선택한 메뉴 정보
//            val selectedMenu = NormalSelectBasketVO(basketImg = selectedImg, tvBasketCount = binding.tvCount.text.toString())
//            // 부모 Activity에 값 전달
//            listener?.onMenuAdded(selectedMenu)
//            Log.d("Dialog", "Menu added: $selectedMenu")
            dismiss()
        }

        // btnMinus, btnPlus 클릭 이벤트
        var tvCount = 1
        binding.btnMinus.setOnClickListener {
            if (tvCount > 1) {
                tvCount--
                binding.tvCount.text = tvCount.toString()
            }
            if (tvCount == 1) {
                binding.btnMinus.isEnabled = false
            }
        }
        binding.btnPlus.setOnClickListener {
            tvCount++
            binding.tvCount.text = tvCount.toString()

            binding.btnMinus.isEnabled = true
        }

        // btnMinus1, btnPlus1 클릭 이벤트
        var tvCount1 = 0
        binding.btnMinus1.setOnClickListener {
            if (tvCount1 > 0) {
                tvCount1--
                binding.tvCount1.text = tvCount1.toString()
            }
            if (tvCount1 == 0) {
                binding.btnMinus1.isEnabled = false
            }
        }
        binding.btnPlus1.setOnClickListener {
            tvCount1++
            binding.tvCount1.text = tvCount1.toString()

            binding.btnMinus1.isEnabled = true
        }

        // btnMinus2, btnPlus2 클릭 이벤트
        var tvCount2 = 0
        binding.btnMinus2.setOnClickListener {
            if (tvCount2 > 0) {
                tvCount2--
                binding.tvCount2.text = tvCount2.toString()
            }
            if (tvCount2 == 0) {
                binding.btnMinus2.isEnabled = false
            }
        }
        binding.btnPlus2.setOnClickListener {
            tvCount2++
            binding.tvCount2.text = tvCount2.toString()

            binding.btnMinus2.isEnabled = true
        }

        // btnMinus3, btnPlus3 클릭 이벤트
        var tvCount3 = 0
        binding.btnMinus3.setOnClickListener {
            if (tvCount3 > 0) {
                tvCount3--
                binding.tvCount3.text = tvCount3.toString()
            }
            if (tvCount3 == 0) {
                binding.btnMinus3.isEnabled = false
            }
        }
        binding.btnPlus3.setOnClickListener {
            tvCount3++
            binding.tvCount3.text = tvCount3.toString()

            binding.btnMinus3.isEnabled = true
        }

        // btnMinus4, btnPlus4 클릭 이벤트
        var tvCount4 = 0
        binding.btnMinus4.setOnClickListener {
            if (tvCount4 > 0) {
                tvCount4--
                binding.tvCount4.text = tvCount4.toString()
            }
            if (tvCount4 == 0) {
                binding.btnMinus4.isEnabled = false
            }
        }
        binding.btnPlus4.setOnClickListener {
            tvCount4++
            binding.tvCount4.text = tvCount4.toString()
            binding.btnMinus4.isEnabled = true
    }
    }
}