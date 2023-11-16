package com.example.bbmr_project.Normal_Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bbmr_project.R
import com.example.bbmr_project.VO.NormalTakeOutVO
import com.example.bbmr_project.Normal_Fragment.adapters.NormalTakeOutAdapter

class Normal_Fragment_Tab1_Beverage : Fragment() {
    private lateinit var rvBeverage: RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.frag_normal_tab1_beverage, container, false)
        rvBeverage = view.findViewById(R.id.rvBeverage)

        // RecyclerView에 레이아웃 매니저 설정
        val layoutManager = GridLayoutManager(requireContext(), 4)
        rvBeverage.layoutManager = layoutManager

        val frag1List: ArrayList<NormalTakeOutVO> = ArrayList()
        frag1List.add(NormalTakeOutVO(R.drawable.coffee, "밀크티", "2,000원"))
        frag1List.add(NormalTakeOutVO(R.drawable.coffee, "녹차라떼", "2,000원"))
        frag1List.add(NormalTakeOutVO(R.drawable.coffee, "고구마라떼", "2,000원"))

        val adapter = context?.let { NormalTakeOutAdapter(it, R.layout.frag_normal_list, frag1List, childFragmentManager) }
        rvBeverage.adapter = adapter

        return view
    }
}