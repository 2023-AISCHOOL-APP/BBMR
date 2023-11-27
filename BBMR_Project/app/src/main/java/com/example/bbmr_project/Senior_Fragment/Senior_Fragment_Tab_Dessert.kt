package com.example.bbmr_project.Senior_Fragment

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
import com.example.bbmr_project.Senior_Fragment.seniorAdapters.ItemClickListener
import com.example.bbmr_project.Senior_Fragment.seniorAdapters.SeniorTakeOutAdapter
import com.example.bbmr_project.VO.Senior_TakeOutVO


class Senior_Fragment_Tab_Dessert : Fragment(), ItemClickListener {


    override fun onItemClick(item: Senior_TakeOutVO) {
    }

    private lateinit var viewModel: MenuListViewModel
    private lateinit var adapter: SeniorTakeOutAdapter
    private lateinit var rvDessert: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // ------ server인 base url을 간접적으로 가져와야 하는 곳 ------
        val factory = MenuListViewModel.MenuListViewModelFactory(getString(R.string.baseUrl))
        viewModel = ViewModelProvider(this, factory).get(MenuListViewModel::class.java)

        val view = inflater.inflate(R.layout.frag_senior_tab_dessert, container, false)
        rvDessert = view.findViewById(R.id.rvDessert)

        adapter = SeniorTakeOutAdapter(
            requireContext(),
            R.layout.frag_senior_list,
            arrayListOf(),
            this
            ,parentFragmentManager
        )
        rvDessert.adapter = adapter
        rvDessert.layoutManager = GridLayoutManager(requireContext(), 3)

        viewModel.menuList3.observe(viewLifecycleOwner) { menuList ->
            adapter.updateList(menuList)
        }

        return view
    }



}