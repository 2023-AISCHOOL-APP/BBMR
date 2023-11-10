package com.example.bbmr_project.fragments2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bbmr_project.R
import com.example.bbmr_project.VO.Frag1VO
import com.example.bbmr_project.fragments2.adapters.Frag1Adapter

class Fragment2_1 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment2_1, container, false)
        val rv : RecyclerView = view.findViewById(R.id.rv)
        val frag1List : ArrayList<Frag1VO> = ArrayList()
        frag1List.add(Frag1VO(R.drawable.coffee, "아메리카노", "2,000원"))
        frag1List.add(Frag1VO(R.drawable.coffee, "아메리카노", "2,000원"))
        frag1List.add(Frag1VO(R.drawable.coffee, "아메리카노", "2,000원"))
        frag1List.add(Frag1VO(R.drawable.coffee, "아메리카노", "2,000원"))
        frag1List.add(Frag1VO(R.drawable.coffee, "아메리카노", "2,000원"))
        frag1List.add(Frag1VO(R.drawable.coffee, "아메리카노", "2,000원"))

        val layoutManager = GridLayoutManager(requireContext(), 4)
        rv.layoutManager = layoutManager

        val adapter = context?.let { Frag1Adapter(it, R.layout.frag1_list, frag1List) }
        rv.adapter = adapter
        return view
    }
}

