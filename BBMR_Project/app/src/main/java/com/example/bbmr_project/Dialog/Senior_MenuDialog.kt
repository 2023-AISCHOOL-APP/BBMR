package com.example.bbmr_project.Dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.DialogFragment
import com.example.bbmr_project.CartStorage
import com.example.bbmr_project.Product
import com.example.bbmr_project.VO.Senior_TakeOutVO
import com.example.bbmr_project.databinding.DialogSeniorMenuBinding

class Senior_MenuDialog : DialogFragment() {


    var onClick: (Product) -> Unit = {
        val args = Bundle().apply {
            putParcelable(KeyProductBundleKey, it)
        }
        val fragment = Senior_BasketDialog()
        fragment.arguments = args
        fragment.show(childFragmentManager, "")
    }

    // Adapter에서 값을 받아오는 코드
    companion object {
        fun Senior_Menu(item: Senior_TakeOutVO): Senior_MenuDialog {
            val args = Bundle().apply {
                putString("sname", item.sname)
                putInt("sprice", item.sprice)
                putInt("simg", item.simg)
            }
            val fragment = Senior_MenuDialog()
            fragment.arguments = args
            return fragment
        }
    }


    private lateinit var binding: DialogSeniorMenuBinding


    override fun onStart() {
        super.onStart()
        val darkTransparentBlack = Color.argb((255 * 0.6).toInt(), 0, 0, 0)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(darkTransparentBlack))
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog?.window?.setDimAmount(0.4f)
        isCancelable = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogSeniorMenuBinding.inflate(layoutInflater)



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 메뉴 선택시 Dialog에 메뉴의 기본정보 제공하는 코드 (Adapter에서 받아온 값을 화면에 보여주기 위한 코드)
        val sname = arguments?.getString("sname")
        val sprice = arguments?.getInt("sprice")
        val simg = arguments?.getInt("simg")

        binding.tvMenuName.text = sname
        binding.tvMenuPrice.text = sprice.toString()
        if (simg != null) {
            binding.imgMenu.setImageResource(simg)
        }
        // ------ 추가 옵션 이동 코드 시작 ------
        binding.btnAddtionOption.setOnClickListener {

            val product = Product(
                binding.tvMenuName.text.toString(),
                binding.tvMenuPrice.text.toString().toInt(),
                binding.tvMenuCount.text.toString().toInt()
            )
            val dialogFragment = Senior_AdditionalOptionDialog()
            val bundle = Bundle()
            bundle.putSerializable("product_option", product)
            dialogFragment.arguments = bundle
            dialogFragment.show(childFragmentManager, "Senior_AdditionalOptionDialog")

        }
        // ------ 추가 옵션으로 이동 코드 끝 ------

        // ------ 이전으로 버튼 클릭시 화면 꺼지는 코드 ------
        binding.btnBack.setOnClickListener {
            dismiss()
        }

        // 선택완료 누르면 값을 보내는 코드
        binding.btnComplet.setOnClickListener {
            // ------ 라디오 버튼에서도 값 가져오기 시작------
            val radiogroup = binding.rbCooHot.checkedRadioButtonId
            val radioButton: RadioButton = binding.rbCooHot.findViewById(radiogroup)
            val coolhot: Boolean = radioButton.isChecked
            onClick.invoke(
                Product(
                    name = binding.tvMenuName.text.toString(),
                    price = binding.tvMenuPrice.text.toString().toInt(),
                    count = binding.tvMenuCount.text.toString().toInt(),
                    temperature = coolhot


                )
            )


            CartStorage.productList.add(
                Product(
                    name = binding.tvMenuName.text.toString(),
                    price = binding.tvMenuPrice.text.toString().toInt(),
                    count = binding.tvMenuCount.text.toString().toInt(),
                    temperature = coolhot
                )
            )

            dismiss()
        }
        // ------ 라디오 버튼에서도 값 가져오기 시작------

        // ------ 상품의 수량 조절하는 코드 시작 ------
        var MenuCount = 1

        // Plus버튼 누르면 증가하는 코드
        binding.btnSeniorPlus.setOnClickListener {
            MenuCount++
            binding.tvMenuCount.text = MenuCount.toString()
            binding.btnSeniorMinus.isClickable = true  // Plus버튼 이후에 지속적으로 Minus버튼 클릭시 버튼 활성화
        }

        // Minus버튼 누르면 감소하는 코드
        binding.btnSeniorMinus.setOnClickListener {

            if (MenuCount == 1) {
                binding.btnSeniorMinus.isClickable = false  // 수량이 1이면 Minus버튼 비활성화
            }

            if (MenuCount > 1) {
                MenuCount--
                binding.tvMenuCount.text = MenuCount.toString()
            }

        }
        // ------ 상품의 수량 조절하는 코드 끝 ------


    }
}