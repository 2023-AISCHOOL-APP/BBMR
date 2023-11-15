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

class Fragment2_3 : Fragment() {
    private lateinit var rvMD: RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_n_3, container, false)
        rvMD = view.findViewById(R.id.rvMD)

        val layoutManager = GridLayoutManager(requireContext(), 4)
        rvMD.layoutManager = layoutManager

        val frag1List: ArrayList<TakeOut2VO> = ArrayList()
        frag1List.add(TakeOut2VO(R.drawable.coffee, "MD", "2,000원"))
        frag1List.add(TakeOut2VO(R.drawable.coffee, "MD", "2,000원"))

        val adapter = context?.let { TakeOut2Adapter(it, R.layout.frag_n_list, frag1List, childFragmentManager) }
        rvMD.adapter = adapter

        return view
    }
}
