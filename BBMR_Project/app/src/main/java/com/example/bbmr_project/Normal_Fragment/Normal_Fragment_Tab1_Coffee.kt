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

class Normal_Fragment_Tab1_Coffee : Fragment() {

    private lateinit var rvCoffee: RecyclerView
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.frag_normal_tab1_coffee, container, false)
        rvCoffee = view.findViewById(R.id.rvCoffee)

        val layoutManager = GridLayoutManager(requireContext(), 4)
        rvCoffee.layoutManager = layoutManager

        val frag1List: ArrayList<NormalTakeOutVO> = ArrayList()
        frag1List.add(NormalTakeOutVO(R.drawable.coffee, "아메리카노", "2,000원"))
        frag1List.add(NormalTakeOutVO(R.drawable.coffee, "카페라떼", "2,000원"))
        frag1List.add(NormalTakeOutVO(R.drawable.coffee, "카푸치노", "2,000원"))
        frag1List.add(NormalTakeOutVO(R.drawable.coffee, "바닐라라떼", "2,000원"))

        val adapter = context?.let { NormalTakeOutAdapter(it, R.layout.frag_normal_list, frag1List, childFragmentManager) }
        rvCoffee.adapter = adapter

        return view
    }
}