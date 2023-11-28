package com.example.bbmr_project.Normal_Fragment.adapters

import NormalSelectPayAdapter
import NormalSelectedMenuInfo
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.bbmr_project.Dialog.TotalCostListener
//import com.example.bbmr_project.Menu.NormalSelectedMenuInfo
import com.example.bbmr_project.R
class NormalSelectBasketAdapter(
    val context: Context,
    val layout: Int,
    val totalCostListener: TotalCostListener,
) : RecyclerView.Adapter<NormalSelectBasketAdapter.ViewHolder>() {

    private val basketList: MutableList<NormalSelectedMenuInfo> = mutableListOf()

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val basketImg: ImageView = view.findViewById(R.id.basketImg)
        val tvBasketCount: TextView = view.findViewById(R.id.tvBasketCount)
        val btnBasketPlus: Button = view.findViewById(R.id.btnBasketPlus)
        val btnBasketMinus: Button = view.findViewById(R.id.btnBasketMinus)
        val btnBasketCancel: Button = view.findViewById(R.id.btnBasketCancel)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NormalSelectBasketAdapter.ViewHolder {
        val view = inflater.inflate(layout, parent, false)
        return NormalSelectBasketAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: NormalSelectBasketAdapter.ViewHolder, position: Int) {
        val item = basketList[position]
        Glide.with(context)
            .load(item.menuImg)
            .apply(RequestOptions().placeholder(null)) // 로딩 중에 표시할 플레이스홀더 이미지
            .into(holder.basketImg)

//        holder.basketImg.setImageResource(item.menuImg)
        holder.tvBasketCount.text = item.tvCount.toString()

        // btnBasketPlus 클릭 시
        holder.btnBasketPlus.setOnClickListener {
            // tvBasketCount 값을 증가
            item.tvCount++
            notifyDataSetChanged()
            // 현재 총 비용 출력
            val currentTotalCost = calculateTotalCost()
            Log.d("TotalCostUpdated", "Total Cost Increased: $currentTotalCost")

            totalCostListener.onTotalCostUpdated(currentTotalCost)
        }

        // btnBasketMinus 클릭 시
        holder.btnBasketMinus.setOnClickListener {
            // tvBasketCount가 2 이상일 때만 감소
            if (item.tvCount > 1) {
                item.tvCount--
                notifyDataSetChanged()

                // 현재 총 비용 출력
                val currentTotalCost = calculateTotalCost()
                Log.d("TotalCostUpdated", "Total Cost Increased: $currentTotalCost")

                totalCostListener.onTotalCostUpdated(currentTotalCost)
            }
        }

        // btnBasketCancel 클릭 시
        holder.btnBasketCancel.setOnClickListener {
            // 해당 아이템의 비용을 총 비용에서 차감
            val itemCost = calculateItemCost(item)
            val remainingTotalCost = calculateTotalCost() - itemCost

            // TotalCostListener에 알림
            totalCostListener.onTotalCostUpdated(remainingTotalCost)

            // 해당 아이템을 RecyclerView에서 삭제
            val removedItemPosition = holder.adapterPosition
            removeItemFromBasket(removedItemPosition)


            // 전체 취소인 경우 해당 메뉴의 비용을 전체에서 제거
            if (basketList.isEmpty()) {
                totalCostListener.onTotalCostUpdated(0)
            }
        }

    }


    private fun removeItemFromBasket(position: Int) { // 아이템 삭제 메서드
        if (position in 0 until basketList.size) {
            basketList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    override fun getItemCount(): Int {
        return basketList.size
    }

    fun addItem(selectedMenuInfo: NormalSelectedMenuInfo) {
        basketList.add(selectedMenuInfo)
        notifyItemInserted(basketList.size - 1)
    }

    // 현재 장바구니의 총 비용 계산
    private fun calculateTotalCost(): Int {
        var totalCost = 0
        for (item in basketList) {
            val optionCost = item.tvCount1*500 + item.tvCount2*500 + item.tvCount3*500 + item.tvCount4*500

            // 각 메뉴의 tvCount에 상관없이 price를 곱해서 누적
            totalCost += item.tvCount * (item.price.toString()?.replace(",", "")?.replace("원", "")?.toIntOrNull() ?: 0) + (optionCost * item.tvCount)
        }
        return totalCost
    }

    // 아이템의 비용 계산
    private fun calculateItemCost(item: NormalSelectedMenuInfo): Int {
        // 각 메뉴의 tvCount에 상관없이 price를 곱해서 반환 (음수 값이 아닌 0을 반환하도록 수정)
        val menuPrice = (item.price.toString().replace(",", "")?.replace("원", "")?.toIntOrNull() ?: 0)
        val optionCost = calculateOptionCost(item)

        // tvCount에 따라 메뉴의 가격을 곱해서 반환
        // 여기서 optionCost를 더해줘야 합니다.
        val cost = (menuPrice + optionCost) * item.tvCount

        // 음수 값이 나올 경우 0을 반환
        return if (cost < 0) 0 else cost
    }

    // 옵션 비용 계산
    private fun calculateOptionCost(item: NormalSelectedMenuInfo): Int {
        return item.tvCount1 * 500 + item.tvCount2 * 500 + item.tvCount3 * 500 + item.tvCount4 * 500
    }

    fun clearItems() {
        basketList.clear()
    }

    fun getCurrentList(): List<NormalSelectedMenuInfo> {
        return basketList
    }
}