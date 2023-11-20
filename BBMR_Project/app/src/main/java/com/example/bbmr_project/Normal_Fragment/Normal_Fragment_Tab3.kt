package com.example.bbmr_project.Normal_Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bbmr_project.Dialog.Normal_MenuDialog
import com.example.bbmr_project.Dialog.Normal_MenuDialogListener
import com.example.bbmr_project.Normal_Fragment.adapters.ItemClickListener
import com.example.bbmr_project.R
import com.example.bbmr_project.VO.NormalTakeOutVO
import com.example.bbmr_project.Normal_Fragment.adapters.NormalTakeOutAdapter

class Normal_Fragment_Tab3 : Fragment() {
    private lateinit var rvMD: RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.frag_normal_tab3, container, false)
        rvMD = view.findViewById(R.id.rvMD)

        val layoutManager = GridLayoutManager(requireContext(), 4)
        rvMD.layoutManager = layoutManager

        val frag1List: ArrayList<NormalTakeOutVO> = ArrayList()
        frag1List.add(NormalTakeOutVO(R.drawable.coffee, "MD", "2,000원"))
        frag1List.add(NormalTakeOutVO(R.drawable.coffee, "MD", "2,000원"))

        val adapter =
            NormalTakeOutAdapter(
                requireContext(),
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
                })
        rvMD.adapter = adapter

        return view
    }
}
