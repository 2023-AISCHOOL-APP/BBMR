package com.example.bbmr_project.Dialog

import NormalSelectedMenuInfo
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.bbmr_project.R
import com.example.bbmr_project.VO.NormalTakeOutVO
import com.example.bbmr_project.databinding.DialogNormalMenuBinding

// 메뉴 선택 시 출력되는 Dialog


// Normal_TakeOutActivity에서 장바구니에 추가하기 위한 인터페이스
interface Normal_MenuDialogListener {
    fun onMenuAdded(normalSelectedMenuInfo: NormalSelectedMenuInfo, tvCount: Int, totalCost: Int)
}

// Normal_TakeOutActivity에서 총합계를 위한 인터페이스
interface TotalCostListener {
    fun onTotalCostUpdated(totalCost: Int, itemCost: Int)
    fun onMenuAdded(normalSelectedMenuInfo: NormalSelectedMenuInfo, tvCount: Int, totalCost: Int)
}

class Normal_MenuDialog : DialogFragment() {

    private lateinit var binding: DialogNormalMenuBinding
    private var listener: Normal_MenuDialogListener? = null
    private var selectedMenuList: MutableList<NormalSelectedMenuInfo> = mutableListOf()


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

        if (img != null) {
            binding.menuImg.setImageResource(img)
        }
        binding.name.text = name
        binding.price.text = price


        // 이전으로 클릭 시 NormalTakeOut으로 이동
        binding.btnBackN.setOnClickListener {
            dismiss()
        }

        binding.btnBasketN.setOnClickListener {
            // 선택한 메뉴의 정보를 NormalSelectedMenuInfo 클래스에 저장
            val selectedMenuInfo = NormalSelectedMenuInfo(
                menuImg = img ?: 0,
                name = name,
                price = price,
                temperature = getSelectedTemperature(),
                tvCount = binding.tvCount.text.toString().toIntOrNull()
                    ?: 0, // tv값을 정수로 변환하거나 null 반환
                tvCount1 = binding.tvCount1.text.toString().toIntOrNull() ?: 0,
                tvCount2 = binding.tvCount2.text.toString().toIntOrNull() ?: 0,
                tvCount3 = binding.tvCount3.text.toString().toIntOrNull() ?: 0,
                tvCount4 = binding.tvCount4.text.toString().toIntOrNull() ?: 0
            )

            // 장바구니에 담고 나서도 수량 증감을 위해 따로 값 보내기
            val tvCount = binding.tvCount.text.toString().toIntOrNull() ?: 0

            // 총 비용 계산
            val priceString = selectedMenuInfo.price?.replace(",", "")?.replace("원", "") // 쉼표, 원 제거 후 정수변환
            val menuPrice = priceString?.toIntOrNull() ?: 0
            val totalCost = menuPrice * selectedMenuInfo.tvCount

            listener?.onMenuAdded(selectedMenuInfo, tvCount, totalCost)
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

    private fun getSelectedTemperature(): String {
        // hot cold 라디오 버튼 선택
        return when (binding.btnNormalTempGroup.checkedRadioButtonId) {
            R.id.btnHot -> "Hot"
            R.id.btnCold -> "Cold"
            else -> ""
        }
    }

    // 리스너 설정 메서드
    fun setListener(listener: Normal_MenuDialogListener) {
        this.listener = listener
    }

}