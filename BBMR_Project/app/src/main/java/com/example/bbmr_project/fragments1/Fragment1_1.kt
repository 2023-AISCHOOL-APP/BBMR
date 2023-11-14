package com.example.bbmr_project.fragments1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bbmr_project.R
import com.example.bbmr_project.VO.TakeOut1VO
import com.example.bbmr_project.fragments1.adapters1.TakeOut1Adapter


class Fragment1_1 : Fragment() {

    private val menuList: ArrayList<TakeOut1VO> = ArrayList()
    private lateinit var adapter: TakeOut1Adapter
    private lateinit var rvS : RecyclerView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_s_1, container, false)
//        val rvS : RecyclerView = view.findViewById(R.id.rvS)
        rvS = view.findViewById(R.id.rvS)
        val menuList1 : ArrayList<TakeOut1VO> = ArrayList()

        menuList1.add(TakeOut1VO(R.drawable.coffee, "아메리카노", "2000원"))
        menuList1.add(TakeOut1VO(R.drawable.coffee, "아메리카노", "2000원"))
        menuList1.add(TakeOut1VO(R.drawable.coffee, "아메리카노", "2000원"))
        menuList1.add(TakeOut1VO(R.drawable.coffee, "아메리카노", "2000원"))
        menuList1.add(TakeOut1VO(R.drawable.coffee, "아메리카노", "2000원"))
        menuList1.add(TakeOut1VO(R.drawable.coffee, "아메리카노", "2000원"))


        val adapter = TakeOut1Adapter (requireContext(), R.layout.frag_s_list, menuList1)
        rvS.adapter = adapter

        val gridLayoutManager = GridLayoutManager(requireContext(),3)
        rvS.layoutManager = gridLayoutManager



        return view
    }


    // 리스트 1번 페이지 제거후 2번 페이지
    fun clearMenuList1(){
        menuList.clear()
        val menuList2 : ArrayList<TakeOut1VO> = ArrayList()
        menuList2.add(TakeOut1VO(R.drawable.coffee, "러시아노", "2000원"))
        menuList2.add(TakeOut1VO(R.drawable.coffee, "러시아노", "2000원"))
        menuList2.add(TakeOut1VO(R.drawable.coffee, "러시아노", "2000원"))
        menuList2.add(TakeOut1VO(R.drawable.coffee, "러시아노", "2000원"))
        menuList2.add(TakeOut1VO(R.drawable.coffee, "러시아노", "2000원"))
        menuList2.add(TakeOut1VO(R.drawable.coffee, "러시아노", "2000원"))

        menuList.addAll(menuList2)
        adapter = TakeOut1Adapter(requireContext(), R.layout.frag_s_list, menuList2)
        rvS.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    // 리스트 2번 페이지 제거후 1번 페이지
    fun clearMenuList2(){
        menuList.clear()
        val menuList1 : ArrayList<TakeOut1VO> = ArrayList()
        menuList1.add(TakeOut1VO(R.drawable.coffee, "아메리카노", "2000원"))
        menuList1.add(TakeOut1VO(R.drawable.coffee, "아메리카노", "2000원"))
        menuList1.add(TakeOut1VO(R.drawable.coffee, "아메리카노", "2000원"))
        menuList1.add(TakeOut1VO(R.drawable.coffee, "아메리카노", "2000원"))
        menuList1.add(TakeOut1VO(R.drawable.coffee, "아메리카노", "2000원"))
        menuList1.add(TakeOut1VO(R.drawable.coffee, "아메리카노", "2000원"))

        menuList.addAll(menuList1)
        adapter = TakeOut1Adapter(requireContext(), R.layout.frag_s_list, menuList1)
        rvS.adapter = adapter
        adapter.notifyDataSetChanged()
    }


}