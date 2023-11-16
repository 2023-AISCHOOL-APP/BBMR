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

class Normal_Fragment_Tab1_Flatccino : Fragment() {
    private lateinit var rvFlatccino: RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.frag_normal_tab1_flatccino, container, false)
        rvFlatccino = view.findViewById(R.id.rvFlatccino)

        val layoutManager = GridLayoutManager(requireContext(), 4)
        rvFlatccino.layoutManager = layoutManager

        val frag1List: ArrayList<NormalTakeOutVO> = ArrayList()
        frag1List.add(NormalTakeOutVO(R.drawable.coffee, "꿀복숭아 플랫치노", "2,000원"))
        frag1List.add(NormalTakeOutVO(R.drawable.coffee, "민트초코 플랫치노", "2,000원"))
        frag1List.add(NormalTakeOutVO(R.drawable.coffee, "초콜릿칩 플랫치노", "2,000원"))

        val adapter = context?.let { NormalTakeOutAdapter(it, R.layout.frag_normal_list, frag1List, childFragmentManager) }
        rvFlatccino.adapter = adapter

        return view
    }
}