package com.example.bbmr_project.fragments2

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bbmr_project.R
import com.example.bbmr_project.VO.TakeOut2VO
import com.example.bbmr_project.fragments2.adapters.TakeOut2Adapter

class Fragment2_1_Shake : Fragment() {
    private lateinit var rvShake: RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_n_1_shake, container, false)
        rvShake = view.findViewById(R.id.rvShake)

        val layoutManager = GridLayoutManager(requireContext(), 4)
        rvShake.layoutManager = layoutManager

        val frag1List: ArrayList<TakeOut2VO> = ArrayList()
        frag1List.add(TakeOut2VO(R.drawable.coffee, "쿠키 쉐이크", "2,000원"))
        frag1List.add(TakeOut2VO(R.drawable.coffee, "딸기 쉐이크", "2,000원"))
        frag1List.add(TakeOut2VO(R.drawable.coffee, "밀크 쉐이크", "2,000원"))

        val adapter = context?.let { TakeOut2Adapter(it, R.layout.frag_n_list, frag1List, childFragmentManager) }
        rvShake.adapter = adapter

        return view
    }
}