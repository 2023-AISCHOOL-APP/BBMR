package com.example.bbmr_project.fragments2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bbmr_project.R

class Fragment2_3 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment2_3, container, false)
    }

    fun newInstant(): Any {
        val args = Bundle()
        val frag = Fragment2_3()
        frag.arguments = args
        return frag
    }
}
