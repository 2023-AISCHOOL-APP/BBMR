package com.example.bbmr_project.Normal_Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bbmr_project.CartStorage
import com.example.bbmr_project.Dialog.Normal_MenuDessertDialog
import com.example.bbmr_project.Dialog.Normal_MenuDessertDialogListener
import com.example.bbmr_project.Normal_Fragment.adapters.ItemClickListener
import com.example.bbmr_project.R
import com.example.bbmr_project.VO.NormalTakeOutVO
import com.example.bbmr_project.Normal_Fragment.adapters.NormalTakeOutAdapter
import com.example.bbmr_project.Product

class Normal_Fragment_Tab2 : Fragment() {

    private lateinit var rvDessert: RecyclerView

    private fun createNormalTab2List(): List<NormalTakeOutVO> {
        val menuList: ArrayList<Product> = CartStorage.menuList
        val dessertList: List<NormalTakeOutVO> = menuList.filter { product ->
            product.cate == "dessert"
        }.map { product ->
            NormalTakeOutVO(
                img = product.image,
                name = product.name,
                price = product.price
            )
        }
        return dessertList
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.frag_normal_tab2, container, false)
        rvDessert = view.findViewById(R.id.rvDessertPage)

        val layoutManager = GridLayoutManager(requireContext(), 4)
        rvDessert.layoutManager = layoutManager

        val frag1List: List<NormalTakeOutVO> = createNormalTab2List()

        val adapter =
            NormalTakeOutAdapter(
                requireContext(),
                R.layout.frag_normal_list,
                frag1List,
                childFragmentManager,
                object : ItemClickListener {
                    override fun onItemClick(item: NormalTakeOutVO) {
                        // NMenuDessertDialog를 보여주기
                        val normalMenuDessertDialog = Normal_MenuDessertDialog.newInstance(item)
                        normalMenuDessertDialog.show(childFragmentManager, "NMenuDessertDialog")
                        normalMenuDessertDialog.setDessertListener(requireActivity() as Normal_MenuDessertDialogListener)
                        Log.d("프래그먼트2 클릭", "프래그먼트2 클릭 오류")
                    }
                })
        rvDessert.adapter = adapter

        return view
    }
}
