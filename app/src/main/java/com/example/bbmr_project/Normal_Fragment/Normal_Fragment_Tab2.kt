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

class Normal_Fragment_Tab2 : Fragment() {

    private lateinit var rvDessert: RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.frag_normal_tab2, container, false)
        rvDessert = view.findViewById(R.id.rvDessert)

        val layoutManager = GridLayoutManager(requireContext(), 4)
        rvDessert.layoutManager = layoutManager

        val frag1List: ArrayList<NormalTakeOutVO> = ArrayList()
        frag1List.add(NormalTakeOutVO(R.drawable.coffee, "디저트", "2,000원"))
        frag1List.add(NormalTakeOutVO(R.drawable.coffee, "디저트", "2,000원"))

        val adapter = context?.let { NormalTakeOutAdapter(it, R.layout.frag_normal_list, frag1List, childFragmentManager) }
        rvDessert.adapter = adapter

        return view
    }
}
