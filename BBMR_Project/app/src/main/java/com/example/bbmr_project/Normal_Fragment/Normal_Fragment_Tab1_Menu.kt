package com.example.bbmr_project.Normal_Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bbmr_project.Dialog.Normal_MenuDialog
import com.example.bbmr_project.Dialog.Normal_MenuDialogListener
import com.example.bbmr_project.Normal_Fragment.adapters.ItemClickListener
import com.example.bbmr_project.R
import com.example.bbmr_project.VO.NormalTakeOutVO
import com.example.bbmr_project.Normal_Fragment.adapters.NormalTakeOutAdapter

class Normal_Fragment_Tab1_Menu(val type: Int) : Fragment() {
    private lateinit var rvBeverage: RecyclerView
    private lateinit var adapter: NormalTakeOutAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.frag_normal_tab1_beverage, container, false)
        rvBeverage = view.findViewById(R.id.rvBeverage)

        val layoutManager = GridLayoutManager(requireContext(), 4)
        rvBeverage.layoutManager = layoutManager

        val frag1List: ArrayList<NormalTakeOutVO> = ArrayList()

        // 차례대로 커피, 베버리지, 플랫치노, 쉐이크&에이드
        if (type == 0) {
            frag1List.add(NormalTakeOutVO(R.drawable.coffee, "아메리카노", "2,000원"))
            frag1List.add(NormalTakeOutVO(R.drawable.coffee, "카페라떼", "2,000원"))
            frag1List.add(NormalTakeOutVO(R.drawable.coffee, "카푸치노", "2,000원"))
            frag1List.add(NormalTakeOutVO(R.drawable.coffee, "바닐라라떼", "2,000원"))
            frag1List.add(NormalTakeOutVO(R.drawable.coffee, "아메리카노", "2,000원"))
            frag1List.add(NormalTakeOutVO(R.drawable.coffee, "카페라떼", "2,000원"))
            frag1List.add(NormalTakeOutVO(R.drawable.coffee, "카푸치노", "2,000원"))
            frag1List.add(NormalTakeOutVO(R.drawable.coffee, "바닐라라떼", "2,000원"))
            frag1List.add(NormalTakeOutVO(R.drawable.coffee, "아메리카노", "2,000원"))
            frag1List.add(NormalTakeOutVO(R.drawable.coffee, "카페라떼", "2,000원"))
            frag1List.add(NormalTakeOutVO(R.drawable.coffee, "카푸치노", "2,000원"))
            frag1List.add(NormalTakeOutVO(R.drawable.coffee, "바닐라라떼", "2,000원"))
            frag1List.add(NormalTakeOutVO(R.drawable.coffee, "아메리카노", "2,000원"))
            frag1List.add(NormalTakeOutVO(R.drawable.coffee, "카페라떼", "2,000원"))
            frag1List.add(NormalTakeOutVO(R.drawable.coffee, "카푸치노", "2,000원"))
            frag1List.add(NormalTakeOutVO(R.drawable.coffee, "바닐라라떼", "2,000원"))
            frag1List.add(NormalTakeOutVO(R.drawable.coffee, "아메리카노", "2,000원"))
            frag1List.add(NormalTakeOutVO(R.drawable.coffee, "카페라떼", "2,000원"))
            frag1List.add(NormalTakeOutVO(R.drawable.coffee, "카푸치노", "2,000원"))
            frag1List.add(NormalTakeOutVO(R.drawable.coffee, "바닐라라떼", "2,000원"))
            frag1List.add(NormalTakeOutVO(R.drawable.coffee, "아메리카노", "2,000원"))
            frag1List.add(NormalTakeOutVO(R.drawable.coffee, "카페라떼", "2,000원"))
            frag1List.add(NormalTakeOutVO(R.drawable.coffee, "카푸치노", "2,000원"))
            frag1List.add(NormalTakeOutVO(R.drawable.coffee, "바닐라라떼", "2,000원"))
        } else if (type == 1) {
            frag1List.add(NormalTakeOutVO(R.drawable.coffee, "밀크티", "5,000원"))
            frag1List.add(NormalTakeOutVO(R.drawable.coffee, "녹차라떼", "4,000원"))
            frag1List.add(NormalTakeOutVO(R.drawable.coffee, "고구마라떼", "5,500원"))
        } else if (type == 2) {
            frag1List.add(NormalTakeOutVO(R.drawable.coffee, "꿀복숭아 플랫치노", "2,000원"))
            frag1List.add(NormalTakeOutVO(R.drawable.coffee, "민트초코 플랫치노", "2,000원"))
            frag1List.add(NormalTakeOutVO(R.drawable.coffee, "초콜릿칩 플랫치노", "2,000원"))
        } else {
            frag1List.add(NormalTakeOutVO(R.drawable.coffee, "쿠키 쉐이크", "2,000원"))
            frag1List.add(NormalTakeOutVO(R.drawable.coffee, "딸기 쉐이크", "2,000원"))
            frag1List.add(NormalTakeOutVO(R.drawable.coffee, "밀크 쉐이크", "2,000원"))
        }

        adapter = NormalTakeOutAdapter(requireContext(),
            R.layout.frag_normal_list,
            frag1List,
            childFragmentManager,
            object : ItemClickListener {
                override fun onItemClick(item: NormalTakeOutVO) {
                    // NMenuDialog를 보여주기
                    val normalMenuDialog = Normal_MenuDialog.newInstance(item)
                    normalMenuDialog.show(childFragmentManager, "NMenuDialog")
                    normalMenuDialog.setListener(requireActivity() as Normal_MenuDialogListener)
                }
            }
        )
        rvBeverage.adapter = adapter

        return view
    }

}