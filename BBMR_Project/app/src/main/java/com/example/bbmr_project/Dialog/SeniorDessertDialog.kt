package com.example.bbmr_project.Dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.bbmr_project.CartStorage
import com.example.bbmr_project.Product
import com.example.bbmr_project.VO.Senior_TakeOutVO
import com.example.bbmr_project.base.BaseDialogFragment
import com.example.bbmr_project.databinding.DialogSeniorDessertBinding

class SeniorDessertDialog : BaseDialogFragment() {

    private lateinit var binding: DialogSeniorDessertBinding
    var imgUrl: String? = null

    // Adapter에서 값을 받아오는 코드
    companion object {
        fun setArgument(item: Senior_TakeOutVO): SeniorDessertDialog {
            val args = Bundle().apply {
                putString("sname", item.sname)
                putInt("sprice", item.sprice)
                putString("simg", item.simg)
            }
            val fragment = SeniorDessertDialog()
            fragment.arguments = args
            return fragment
        }
    }

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
        binding = DialogSeniorDessertBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 메뉴 선택시 Dialog에 메뉴의 기본정보 제공하는 코드 (Adapter에서 받아온 값을 화면에 보여주기 위한 코드)
//        val sname = arguments?.getString("sname")
//        val sprice = arguments?.getInt("sprice")
//        val simg = arguments?.getInt("simg")
        val seniorDessertTakeoutVO = arguments?.getParcelable<Senior_TakeOutVO>("seniorTakeOutVO")
        Log.d("시니어2", seniorDessertTakeoutVO.toString())
        seniorDessertTakeoutVO?.let {item ->
            var text = item.sname
            // ------ 각 메뉴의 이름들의 길이에 조건을 걸어 줄 바꿈 해주기 ------
            val spannableStringBuilder = SpannableStringBuilder(text)
            if (text.contains(" ")) {
                // 첫 번째 공백에서 개행하는 코드
                val indexFirstSpace = text.indexOf(' ')
                val modiText = StringBuilder(text)
                    .replace(indexFirstSpace, indexFirstSpace + 1, "\n")
                    .toString()
                spannableStringBuilder.replace(0, text.length, modiText)
                binding.tvSeniorDessertName.text = spannableStringBuilder
            } else {
                binding.tvSeniorDessertName.text = item.sname
            }
            binding.tvSeniorDessertPrice.text =
                String.format("%,d원", item.sprice) // String.format("%,d", 값) -> 1000 단위마다 , 표시
            Glide.with(requireContext()).load(item.simg).into(binding.imgSeniorDessert)
            imgUrl = item.simg  // 받아온 값의 URL을 let밖에서 사용할 수 있게 담아둠
            Log.d("이건뭐야2", binding.imgSeniorDessert.toString())  // 지워주세요
        }

////        val seniorgetprice = String.format("%,d", sprice)
//        binding.tvSeniorDessertName.text = sname
//        binding.tvSeniorDessertPrice.text =
//            String.format("%,d원", sprice) // String.format("%,d", 값) -> 1000 단위마다 , 표시
//        if (simg != null) {
////            binding.imgSeniorDessert.setImageResource(simg)
//        }


        // ------ 이전으로 버튼 클릭시 화면 꺼지는 코드 ------
        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        // 선택완료 누르면 값을 보내는 코드
        binding.btnSelect.setOnClickListener {

            //클릭하면 onClick 실행 후 값을 보냄

            CartStorage.addProduct(
                Product(
                    name = binding.tvSeniorDessertName.text.toString(),
                    price = binding.tvSeniorDessertPrice.text.toString().replace(",", "").replace("원", "").toIntOrNull() ?: 0,
                    count = binding.tvSeniorCount.text.toString().toInt(),
                    image = imgUrl.toString()
                )
            )
            dismiss()
        }


        // ------ 상품의 수량 조절하는 코드 시작 ------
        var MenuCount = 1
        val DessertPrice = binding.tvSeniorDessertPrice.text.toString()

                // Plus버튼 누르면 증가하는 코드
        binding.btnPlus.setOnClickListener {
            MenuCount++
            // 상품 수량이 증가하는 코드
            binding.tvSeniorCount.text = MenuCount.toString()
            // 수량에 맞춰 가격이 증가하는 코드
            val MenuPlusCountInt: Int? = binding.tvSeniorCount.text.toString().toIntOrNull()
            if (MenuPlusCountInt != null) {
                val getPrice = DessertPrice
                    .replace(",", "")
                    .replace("원", "")
                    .toIntOrNull() ?: 0
//                val getPrice = arguments?.getInt("sprice") ?: 0
                val plusPrice = getPrice * MenuPlusCountInt
                binding.tvSeniorDessertPrice.text =
                    String.format(
                        "%,d원",
                        plusPrice
                    ) // String.format("%,d", 값) -> 1000 단위마다 , 표시
            } else {
                binding.tvSeniorDessertPrice.text = "취소 후 다시 부탁드립니다."
            }
            binding.btnMinus.isClickable = true  // Plus버튼 이후에 지속적으로 Minus버튼 클릭시 버튼 활성화
        }

        // Minus버튼 누르면 감소하는 코드
        binding.btnMinus.setOnClickListener {

            if (MenuCount <= 1) {
                binding.btnMinus.isClickable = false  // 수량이 1이면 Minus버튼 비활성화
            }

            if (MenuCount > 1) {
                MenuCount--
                // 상품 수랑이 감소하는 코드
                binding.tvSeniorCount.text = MenuCount.toString()
                // 수량에 맞춰 가격이 감소하는 코드
                val MenuMinusCountInt: Int? =
                    binding.tvSeniorCount.text.toString().toIntOrNull()  //replace
                if (MenuMinusCountInt != null) {
                    val getPrice = DessertPrice
                        .replace(",", "")
                        .replace("원", "")
                        .toIntOrNull() ?: 0
                    val minusPrice = getPrice * MenuMinusCountInt
                    binding.tvSeniorDessertPrice.text = String.format(
                        "%,d원",
                        minusPrice
                    ) // String.format("%,d", 값) -> 1000 단위마다 , 표시 String.format("%, d 원", minusPrice)
                }

            }

        }
        // ------ 상품의 수량 조절하는 코드 끝 ------ //


    }

//    fun setListener(listnenr: ItemDessertClickListnenr){
//        this.dessertDialogListnenr = listnenr
//    }

}