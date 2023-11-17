package com.example.bbmr_project.Dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.bbmr_project.NMenuDialogListener
import com.example.bbmr_project.VO.NormalSelectBasketVO
import com.example.bbmr_project.databinding.DialogNormalMenuBinding

// 메뉴 선택 시 출력되는 Dialog

class Normal_MenuDialog : DialogFragment() {

    private lateinit var binding: DialogNormalMenuBinding
    private var listener: NMenuDialogListener? = null  // 리스너 선언

    // 부모 액티비티에 리스너를 설정하는 메서드
    fun setNMenuDialogListener(listener: NMenuDialogListener) {
        this.listener = listener
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

        // 이전으로 클릭 시 TakeOut2로 이동
        binding.btnBackN.setOnClickListener {
            dismiss()
        }

        // 메뉴담기 클릭 시 부모 Activity에 값 전달
        binding.btnBasketN.setOnClickListener {
            // 선택한 메뉴 정보
            val selectedMenu = NormalSelectBasketVO(basketImg = 0, tvBasketCount = toString())

            // 부모 Activity에 값 전달
            listener?.onMenuAdded(selectedMenu)

            dismiss()  // Dialog 닫기
        }

        // btnMinus 클릭 이벤트
        var tvCount = 1
        binding.btnMinus.setOnClickListener {
            // tvCount이 1보다 큰 경우에만 감소하도록 설정
            if (tvCount > 1) {
                tvCount--
                binding.tvCount.text = tvCount.toString()
            }
            // tvCount이 1일 때 btnMinus 비활성화
            if (tvCount == 1) {
                binding.btnMinus.isEnabled = false
            }
        }
        // btnPlus 클릭 이벤트 처리
        binding.btnPlus.setOnClickListener {
            tvCount++
            binding.tvCount.text = tvCount.toString()

            // btnMinus 활성화
            binding.btnMinus.isEnabled = true
        }

        // btnMinus1 클릭 이벤트
        var tvCount1 = 0
        binding.btnMinus1.setOnClickListener {
            // tvCount1이 0보다 큰 경우에만 감소하도록 설정
            if (tvCount1 > 0) {
                tvCount1--
                binding.tvCount1.text = tvCount1.toString()
            }
            // tvCount1이 0일 때 btnMinus1 비활성화
            if (tvCount1 == 0) {
                binding.btnMinus1.isEnabled = false
            }
        }
        // btnPlus1 클릭 이벤트 처리
        binding.btnPlus1.setOnClickListener {
            tvCount1++
            binding.tvCount1.text = tvCount1.toString()

            // btnMinus1 활성화
            binding.btnMinus1.isEnabled = true
        }

        // btnMinus2 클릭 이벤트
        var tvCount2 = 0
        binding.btnMinus2.setOnClickListener {
            // tvCount2가 0보다 큰 경우에만 감소하도록 설정
            if (tvCount2 > 0) {
                tvCount2--
                binding.tvCount2.text = tvCount2.toString()
            }
            // tvCount2가 0일 때 btnMinus2 비활성화
            if (tvCount2 == 0) {
                binding.btnMinus2.isEnabled = false
            }
        }
        // btnPlus2 클릭 이벤트 처리
        binding.btnPlus2.setOnClickListener {
            tvCount2++
            binding.tvCount2.text = tvCount2.toString()

            // btnMinus2 활성화
            binding.btnMinus2.isEnabled = true
        }

        // btnMinus3 클릭 이벤트
        var tvCount3 = 0
        binding.btnMinus3.setOnClickListener {
            // tvCount3이 0보다 큰 경우에만 감소하도록 설정
            if (tvCount3 > 0) {
                tvCount3--
                binding.tvCount3.text = tvCount3.toString()
            }
            // tvCount3이 0일 때 btnMinus3 비활성화
            if (tvCount3 == 0) {
                binding.btnMinus3.isEnabled = false
            }
        }
        // btnPlus3 클릭 이벤트 처리
        binding.btnPlus3.setOnClickListener {
            tvCount3++
            binding.tvCount3.text = tvCount3.toString()

            // btnMinus3 활성화
            binding.btnMinus3.isEnabled = true
        }

        // btnMinus4 클릭 이벤트
        var tvCount4 = 0
        binding.btnMinus4.setOnClickListener {
            // tvCount4가 0보다 큰 경우에만 감소하도록 설정
            if (tvCount4 > 0) {
                tvCount4--
                binding.tvCount4.text = tvCount4.toString()
            }
            // tvCount4가 0일 때 btnMinus4 비활성화
            if (tvCount4 == 0) {
                binding.btnMinus4.isEnabled = false
            }
        }
        // btnPlus4 클릭 이벤트 처리
        binding.btnPlus4.setOnClickListener {
            tvCount4++
            binding.tvCount4.text = tvCount4.toString()

            // btnMinus4 활성화
            binding.btnMinus4.isEnabled = true
    }



    }
}