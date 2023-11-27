package com.example.bbmr_project.fragments1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bbmr_project.Menu.MenuListViewModel
import com.example.bbmr_project.R
import com.example.bbmr_project.fragments1.adapters1.TakeOut1Adapter


class Fragment1_1 : Fragment(){



//    private val menuList: ArrayList<TakeOut1VO> = ArrayList() // 1차
//    private lateinit var adapter: TakeOut1Adapter
//    private lateinit var rvS : RecyclerView
    private lateinit var viewModel: MenuListViewModel
    private lateinit var adapter: TakeOut1Adapter
    private lateinit var rvS: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // ViewModel 초기화
        viewModel = ViewModelProvider(this).get(MenuListViewModel::class.java)

        val view = inflater.inflate(R.layout.fragment_s_1, container, false)

        rvS = view.findViewById(R.id.rvS)


        // RecyclerView 어댑터 초기화
        adapter = TakeOut1Adapter(requireContext(), R.layout.frag_s_list, arrayListOf())
        rvS.adapter = adapter
        rvS.layoutManager = GridLayoutManager(requireContext(), 3)

        // menuList1 LiveData 어댑터 업데이트
        viewModel.menuList1.observe(viewLifecycleOwner) { menuList ->
            adapter.updateList(menuList)
        }

        return view
    }


    // menuList1로 전환하는 함수
    fun switchToMenuList1() {
        viewModel.menuList1.value?.let { menuList1 ->
            adapter.updateList(menuList1)
        }
    }
    // menuList2로 전환하는 함수
    fun switchToMenuList2() {
        viewModel.menuList2.value?.let { menuList2 ->
            adapter.updateList(menuList2)
        }
    }
    }


