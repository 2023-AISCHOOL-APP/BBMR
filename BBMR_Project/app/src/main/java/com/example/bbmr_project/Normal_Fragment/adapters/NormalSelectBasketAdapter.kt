package com.example.bbmr_project.Normal_Fragment.adapters

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
import com.example.bbmr_project.Dialog.TotalCostListener
//import com.example.bbmr_project.Menu.NormalSelectedMenuInfo
import com.example.bbmr_project.R
import com.example.bbmr_project.VO.NormalSelectBasketVO

class NormalSelectBasketAdapter(
    val context: Context,
    val layout: Int,
    val basketList: MutableList<NormalSelectedMenuInfo>,
    val totalCostListener: TotalCostListener
) : RecyclerView.Adapter<NormalSelectBasketAdapter.ViewHolder>() {

    val inflater: LayoutInflater = LayoutInflater.from(context)

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
        val selectedItem = basketList[position]

        holder.basketImg.setImageResource(selectedItem.menuImg)
        holder.tvBasketCount.text = selectedItem.tvCount.toString()

        // btnBasketPlus 클릭 시
        holder.btnBasketPlus.setOnClickListener {
            // tvBasketCount 값을 증가시키는 로직 추가
            selectedItem.tvCount++
            notifyItemChanged(position)

            // 현재 총 비용 출력
            val currentTotalCost = calculateTotalCost()
            Log.d("TotalCostUpdated", "Total Cost Increased: $currentTotalCost")

            totalCostListener.onTotalCostUpdated(calculateTotalCost(), 0)
        }

        // btnBasketMinus 클릭 시
        holder.btnBasketMinus.setOnClickListener {
            // tvBasketCount가 2 이상일 때만 감소시키는 로직 추가
            if (selectedItem.tvCount > 1) {
                selectedItem.tvCount--
                notifyItemChanged(position)

                // 현재 총 비용 출력
                val currentTotalCost = calculateTotalCost()
                Log.d("TotalCostUpdated", "Total Cost Increased: $currentTotalCost")

                totalCostListener.onTotalCostUpdated(calculateTotalCost(), 0)
            }
        }

        // btnBasketCancel 클릭 시
        holder.btnBasketCancel.setOnClickListener {
            // 해당 아이템의 비용을 총 비용에서 차감
            val itemCost = calculateItemCost(selectedItem)
            val remainingTotalCost = calculateTotalCost() - itemCost

            // 로그로 현재 총 비용 출력
            Log.d("TotalCostUpdated", "Total Cost Decreased: $remainingTotalCost")

            // TotalCostListener에 알림
            totalCostListener.onTotalCostUpdated(remainingTotalCost, itemCost)

            // 해당 아이템을 RecyclerView에서 삭제하는 로직 추가
            val removedItemPosition = holder.adapterPosition
            basketList.removeAt(removedItemPosition)
            notifyItemRemoved(removedItemPosition)

            // 전체 취소인 경우 해당 메뉴의 비용을 전체에서 제거
            if (basketList.isEmpty()) {
                Log.d("TotalCostUpdated", "Total Cost Reset: 0")
                totalCostListener.onTotalCostUpdated(0, itemCost)
            }
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
            // 각 메뉴의 tvCount가 0 이상인 경우에만 price를 곱해서 누적
            if (item.tvCount > 0) {
                totalCost += item.tvCount * (item.price?.replace(",", "")?.replace("원", "")?.toIntOrNull() ?: 0)
            }
        }
        return totalCost
    }

    // 아이템의 비용 계산
    private fun calculateItemCost(item: NormalSelectedMenuInfo): Int {
        // 각 메뉴의 tvCount와 price를 곱해서 반환 (음수 값이 아닌 0을 반환하도록 수정)
        val cost = item.tvCount * (item.price?.replace(",", "")?.replace("원", "")?.toIntOrNull() ?: 0)
        return if (cost < 0) 0 else cost
    }
}