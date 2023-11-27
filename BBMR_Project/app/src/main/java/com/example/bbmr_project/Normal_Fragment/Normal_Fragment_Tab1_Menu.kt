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
import com.example.bbmr_project.Dialog.Normal_MenuDialog
import com.example.bbmr_project.Dialog.Normal_MenuDialogListener
import com.example.bbmr_project.Normal_Fragment.adapters.ItemClickListener
import com.example.bbmr_project.R
import com.example.bbmr_project.VO.NormalTakeOutVO
import com.example.bbmr_project.Normal_Fragment.adapters.NormalTakeOutAdapter
import com.example.bbmr_project.Product

class Normal_Fragment_Tab1_Menu(val type: Int) : Fragment() {
    private lateinit var rvBeverage: RecyclerView
    private lateinit var adapter: NormalTakeOutAdapter


    private fun createNormalTab1List(): List<NormalTakeOutVO> {
        val menuList: ArrayList<Product> = CartStorage.menuList
        Log.d("카트스토리지", "${CartStorage.menuList}")
        return when (type) {
            0 -> {
                // Coffee 카테고리에 해당하는 NormalTakeOutVO 리스트 생성
                menuList.filter { product ->
                    product.cate == "coffee" && product.size == 1
                }.map { product ->
                    NormalTakeOutVO(
                        img = product.image,
                        name = product.name,
                        price = product.price
                    )
                }
            }
            1 -> {
                // Beverage 카테고리에 해당하는 NormalTakeOutVO 리스트 생성
                menuList.filter { product ->
                    product.cate == "beverage" && product.size == 1
                }.map { product ->
                    NormalTakeOutVO(
                        img = product.image,
                        name = product.name,
                        price = product.price
                    )
                }
            }
            2 -> {
                // FlatChino 카테고리에 해당하는 NormalTakeOutVO 리스트 생성
                menuList.filter { product ->
                    product.cate == "flatchino" && product.size == 1
                }.map { product ->
                    NormalTakeOutVO(
                        img = product.image,
                        name = product.name,
                        price = product.price
                    )
                }
            }
            else -> {
                // ShakeAndAde 카테고리에 해당하는 NormalTakeOutVO 리스트 생성
                menuList.filter { product ->
                    product.cate == "tea" && product.size == 1
                }.map { product ->
                    NormalTakeOutVO(
                        img = product.image,
                        name = product.name,
                        price = product.price
                    )
                }
            }
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.frag_normal_tab1_menu, container, false)
        rvBeverage = view.findViewById(R.id.rvBeverage)

        val layoutManager = GridLayoutManager(requireContext(), 4)
        rvBeverage.layoutManager = layoutManager

        val frag1List: List<NormalTakeOutVO> = createNormalTab1List()

        adapter = NormalTakeOutAdapter(requireContext(),
            R.layout.frag_normal_list,
            frag1List,
            childFragmentManager,
            object : ItemClickListener {
                override fun onItemClick(item: NormalTakeOutVO) {
                    // NMenuDialog를 보여주기
                    val normalMenuDialog = Normal_MenuDialog.newInstance(item)
                    normalMenuDialog.show(childFragmentManager, "NMenuDialog")
                    normalMenuDialog.setListener(requireActivity() as Normal_MenuDialogListener)
                }
            }
        )
        rvBeverage.adapter = adapter

        return view
    }

}