package com.example.bbmr_project.Dialog

import NormalSelectPayAdapter
import NormalSelectedMenuInfo
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bbmr_project.R
import com.example.bbmr_project.databinding.DialogNormalSelectPayBinding

interface NormalSelectPayDialogListener : Normal_MenuDialogListener {
    override fun onMenuAdded(
        normalSelectedMenuInfo: NormalSelectedMenuInfo,
        tvCount: Int,
        totalCost: Int,
        options: List<String>,
        optionTvCount: Int
    )
}

class Normal_SelectPayDialog : DialogFragment() {

    private lateinit var binding: DialogNormalSelectPayBinding
    private var listener: Normal_MenuDialogListener? = null
    private var selectedMenuList: MutableList<NormalSelectedMenuInfo> = mutableListOf()
    private lateinit var adapter: NormalSelectPayAdapter

    companion object {
        fun newInstance(selectedMenuList: List<NormalSelectedMenuInfo>): Normal_SelectPayDialog {
            val args = Bundle().apply {
                // selectedMenuList를 인자로 전달
                putParcelableArrayList("selectedMenuList", ArrayList(selectedMenuList))
            }
            val fragment = Normal_SelectPayDialog()
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

        isCancelable = true
//        dialog?.setCanceledOnTouchOutside(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogNormalSelectPayBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 이전으로 클릭 시 NormalTakeOut으로 이동
        binding.btnNormalSelectPayBack.setOnClickListener {
            dismiss()
        }

        // btnSelectPayNext 클릭 시 선택된 라디오 버튼에 따라 다이얼로그로 이동
        binding.btnSelectPayNext.setOnClickListener {
            when (binding.btnNormalSelectPay.checkedRadioButtonId) {
                R.id.btnNormalCardPay -> showNormalCardPayDialog()
                R.id.btnNormalCouponPay -> showNormalCouponPayDialog()
                else -> {
                    // 선택된 라디오 버튼이 없는 경우
                    showNormalNoSelectPayDialog()
                }
            }
        }
        // 인자로부터 selectedMenuList를 가져옴
        selectedMenuList = arguments?.getParcelableArrayList("selectedMenuList") ?: mutableListOf()

        // RecyclerView를 selectedMenuList로 설정
        adapter = NormalSelectPayAdapter(
            requireActivity(),
            R.layout.normal_selectpaylist,
            selectedMenuList
        )
        binding.rvSelectPayList.adapter = adapter
        binding.rvSelectPayList.layoutManager = LinearLayoutManager(context)
        binding.rvSelectPayList.invalidateItemDecorations()
        adapter.notifyDataSetChanged()
    }

    private fun showNormalCardPayDialog() {
        val normalCardPayDialog = Normal_CardPayDialog()
        normalCardPayDialog.show(requireActivity().supportFragmentManager, "Normal_CardPayDialog")
    }

    private fun showNormalCouponPayDialog() {
        val normalCouponPayDialog = Normal_CouponPayDialog()
        normalCouponPayDialog.show(
            requireActivity().supportFragmentManager,
            "Normal_CouponPayDialog"
        )
    }

    private fun showNormalNoSelectPayDialog() {
        val normalNoSelectPayDialog = Normal_NoSelectPayDialog()
        normalNoSelectPayDialog.show(
            requireActivity().supportFragmentManager,
            "Normal_NoSelectPayDialog"
        )
    }

    fun setListener(listener: NormalSelectPayDialogListener) {
        this.listener = listener
    }

    fun updateSelectedMenuList(selectedMenuInfoList: List<NormalSelectedMenuInfo>) {
        // 전달받은 리스트의 각 항목을 현재 리스트에 추가
        selectedMenuList.addAll(selectedMenuInfoList)

        // UI 스레드에서 어댑터에 변경 사항 알림
        activity?.runOnUiThread {
            adapter.notifyDataSetChanged()
            Log.d("UpdateUI", "UI Updated")
        }
    }

}
