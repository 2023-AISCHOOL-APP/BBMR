package com.example.bbmr_project.fragments2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bbmr_project.R
import com.example.bbmr_project.databinding.FragmentN1Binding

class Fragment2_1 : Fragment() {

    private lateinit var binding: FragmentN1Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentN1Binding.inflate(inflater)

        binding.btnCoffee.setOnClickListener {
            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.rv, Fragment2_1_Coffee())
            fragmentTransaction.addToBackStack(null) // 백 스택에 추가
            fragmentTransaction.commit()
        }

        binding.btnBeverage.setOnClickListener {
            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.rv, Fragment2_1_Beverage())
            fragmentTransaction.addToBackStack(null) // 백 스택에 추가
            fragmentTransaction.commit()
        }
        return binding.root
    }
}

