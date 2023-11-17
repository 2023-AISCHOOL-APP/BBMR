package com.example.bbmr_project.Dialog

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.bbmr_project.CartStorage
import com.example.bbmr_project.Product
import com.example.bbmr_project.databinding.DialogSeniorBasketBinding
import com.example.bbmr_project.databinding.DialogSeniorMenuBinding

const val KeyProductBundleKey = "Product"

class Senior_BasketDialog() : DialogFragment() {

    private lateinit var binding: DialogSeniorBasketBinding


    override fun onStart() {
        super.onStart()
        val darkTransparentBlack = Color.argb((255 * 0.6).toInt(), 0, 0, 0)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(darkTransparentBlack))
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog?.window?.setDimAmount(0.4f)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogSeniorBasketBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val product = requireArguments().getParcelable<Product>(KeyProductBundleKey)
//        binding.tvSeniorPayPrice.text= product?.price?.toString() ?: "0"

        // val product = parentFragmentManager.fragments

        val product = arguments?.getParcelable<Product>(KeyProductBundleKey)
        Log.d("Senior_BasketDialog", "Received Product: $product")



        if (product != null) {
            // 인자가 있을 경우
            binding.tvSeniorPayPrice.text = product.price.toString()


            Log.d("Senior_BasketDialog", "Received Product: $product")

        } else {
            // 인자가 없을 경우
            binding.tvSeniorPayPrice.text = ""

        }

        CartStorage.productList

        binding.btnTurn.setOnClickListener {
            dismiss()
        }
    }
}