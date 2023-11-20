package com.example.bbmr_project.Normal_Fragment.adapters

import NormalSelectedMenuInfo
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
//import com.example.bbmr_project.Menu.NormalSelectedMenuInfo
import com.example.bbmr_project.R
import com.example.bbmr_project.VO.NormalSelectBasketVO

class NormalSelectBasketAdapter(
    val context: Context,
    val layout: Int,
    val basketList: MutableList<NormalSelectedMenuInfo>
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
            notifyDataSetChanged()
        }

        // btnBasketMinus 클릭 시
        holder.btnBasketMinus.setOnClickListener {
            // tvBasketCount가 2 이상일 때만 감소시키는 로직 추가
            if (selectedItem.tvCount > 1) {
                selectedItem.tvCount--
                notifyDataSetChanged()
            }
        }

        // btnBasketCancel 클릭 시
        holder.btnBasketCancel.setOnClickListener {
            // 해당 아이템을 RecyclerView에서 삭제하는 로직 추가
            basketList.removeAt(position)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return basketList.size
    }

    fun addItem(selectedMenuInfo: NormalSelectedMenuInfo) {
        basketList.add(selectedMenuInfo)
        notifyItemInserted(basketList.size - 1)
    }

}