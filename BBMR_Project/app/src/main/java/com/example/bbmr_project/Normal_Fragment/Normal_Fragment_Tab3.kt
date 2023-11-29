package com.example.bbmr_project.Normal_Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bbmr_project.CartStorage
import com.example.bbmr_project.Dialog.Normal_MenuMDDialog
import com.example.bbmr_project.Dialog.Normal_MenuMDDialogListener
import com.example.bbmr_project.Normal_Fragment.adapters.ItemClickListener
import com.example.bbmr_project.R
import com.example.bbmr_project.VO.NormalTakeOutVO
import com.example.bbmr_project.Normal_Fragment.adapters.NormalTakeOutAdapter
import com.example.bbmr_project.Product

class Normal_Fragment_Tab3 : Fragment() {
    private lateinit var rvMD: RecyclerView

    private fun createNormalTab3List(): List<NormalTakeOutVO> {
        val menuList: ArrayList<Product> = CartStorage.menuList
        val MDList: List<NormalTakeOutVO> = menuList.filter { product ->
            product.cate == "md"
        }.map { product ->
            NormalTakeOutVO(
                img = product.image,
                name = product.name,
                price = product.price
            )
        }
        return MDList
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.frag_normal_tab3, container, false)
        rvMD = view.findViewById(R.id.rvMD)

        val layoutManager = GridLayoutManager(requireContext(), 4)
        rvMD.layoutManager = layoutManager

        val frag1List: List<NormalTakeOutVO> = createNormalTab3List()

        val adapter =
            NormalTakeOutAdapter(
                requireContext(),
                R.layout.frag_normal_list,
                frag1List,
                childFragmentManager,
                object : ItemClickListener {
                    override fun onItemClick(item: NormalTakeOutVO) {
                        // NMenuDialog를 보여주기
                        val normalMenuMDDialog = Normal_MenuMDDialog.newInstance(item)
                        normalMenuMDDialog.show(childFragmentManager, "NMenuMDDialog")
                        normalMenuMDDialog.setMDListener(requireActivity() as Normal_MenuMDDialogListener)
                    }
                })
        rvMD.adapter = adapter

        return view
    }
}
