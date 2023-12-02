package com.example.bbmr_project.Dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bbmr_project.CartStorage
import com.example.bbmr_project.Product
import com.example.bbmr_project.R
import com.example.bbmr_project.Senior_Fragment.seniorAdapters.SeniorSelectBasketAdapter
import com.example.bbmr_project.VO.Senior_TakeOutVO
import com.example.bbmr_project.base.BaseDialogFragment
import com.example.bbmr_project.databinding.DialogSeniorBasketBinding

//const val KeyProductBundleKey = "Product"

class SeniorBasketDialog : BaseDialogFragment() {


    var buttonDoubleDefend = false
    private lateinit var binding: DialogSeniorBasketBinding
    private val adapter: SeniorSelectBasketAdapter by lazy {
        SeniorSelectBasketAdapter(
            context = requireContext(),
            layout = R.layout.senior_basketlist,
            onChanged = {
                onUpdate(it)
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogSeniorBasketBinding.inflate(inflater, container, false) //layoutInflater
        initView()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initializeDialogOptions()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val _text = "카드\n&\n삼성페이"
        val spannableStringBuilder = SpannableStringBuilder(_text)
        spannableStringBuilder.setSpan(
            RelativeSizeSpan(1.3f),
            0,
            2,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableStringBuilder.setSpan(
            RelativeSizeSpan(0.5f),
            3,
            4,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableStringBuilder.setSpan(
            RelativeSizeSpan(1.2f),
            5,
            _text.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.btnCardDSB.text = spannableStringBuilder
        binding.btnCardDSB.setLineSpacing(0.6f, 0.63f)


        val discountPrice = arguments?.getString("discount_price").toString().toIntOrNull()
            ?: 0 // 여기서 각 상품에 맞는 게 String으로 가져와 짐

        var totalAmount = CartStorage.getProductList().sumOf { it.price }

        // 남은 금액
//        var extraPrice = 0
//
//        if (totalAmount >= discountPrice) {
//            totalAmount = totalAmount - discountPrice
//            // 1000의 단위마다 , 넣어주는 코드
//            var amountPrice = String.format("%,d 원", totalAmount)
//            binding.tvAmount.text = amountPrice
////            if (::adapter.isInitialized){
////                adapter.updateData(CartStorage.getProductList() as ArrayList<Product>)
////            }
//        } else if (totalAmount < discountPrice) {
//            totalAmount = totalAmount - discountPrice
//            // 이 부분에서 남은 금액을 교환권에 되돌려 주기
//            extraPrice = discountPrice - totalAmount
//        } else {
//        }


//        val product = requireArguments().getParcelable<Product>(KeyProductBundleKey)
//        binding.tvSeniorPayPrice.text= product?.price?.toString() ?: "0"

        // val product = parentFragmentManager.fragments


        // 쿠폰은 바로 보내버리기

        // 쿠폰 클릭 시, 쿠폰 창으로 넘어가기

        binding.btnCpnDSB.setOnClickListener {
            if (!buttonDoubleDefend) {
                buttonDoubleDefend = true

                val dialogFragment = Senior_CouponPayDialog()
                dialogFragment.show(
                    requireActivity().supportFragmentManager,
                    "Senior_CouponPayDialog"
                )
                Handler().postDelayed({
                    buttonDoubleDefend = false
                }, 2000)
            }
        }

        // 결제창 클릭했을 때, 결제로 넘어가기
        binding.btnCardDSB.setOnClickListener {

            val dialogFragment = Senior_PaymentDialog()
            val bundle = Bundle()
            // 결제 창으로 넘기기 amount라는 걸 받아오면 됨 받아오는 방법은  discountPrice변수 초기화에 적혀있음
            bundle.putString("amount", "${totalAmount}")
            dialogFragment.arguments = bundle

            dialogFragment.show(requireActivity().supportFragmentManager, "Senior_PaymentDialog")
        }

        binding.btnTurnDSB.setOnClickListener {
            dismiss()
        }

    }

    private fun initView() {
        initRecyclerView()
        binding.tvAmount.text =
            String.format("%,d 원", CartStorage.getProductList().sumOf { it.price })
    }

    private fun initRecyclerView() {
        binding.rvSeniorBasket.adapter = adapter
        binding.rvSeniorBasket.layoutManager =
            GridLayoutManager(requireContext(), 1, GridLayoutManager.HORIZONTAL, false)
        adapter.updateData(CartStorage.getProductList() as ArrayList<Product>)
    }

    private fun initializeDialogOptions() {
        val darkTransparentBlack = Color.argb((255 * 0.6).toInt(), 0, 0, 0)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(darkTransparentBlack))
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog?.window?.setDimAmount(0.4f)
        isCancelable = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        CartStorage.notifyProductListChanged()
    }

    // 값이 바뀌는 기능
    private fun onUpdate(productList: List<Product>) {
        binding.tvAmount.text = String.format("%,d 원", productList.sumOf { it.price })

    }

}