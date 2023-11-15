package com.example.bbmr_project.fragments2.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bbmr_project.R
import com.example.bbmr_project.VO.BasketVO

class BasketAdapter (val context: Context, val layout: Int, val basketList: MutableList<BasketVO>)
    : RecyclerView.Adapter<BasketAdapter.ViewHolder>(){

    val inflater : LayoutInflater = LayoutInflater.from(context)

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val basketImg : ImageView = view.findViewById(R.id.basketImg)
        val tvBasketCount : TextView = view.findViewById(R.id.tvBasketCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketAdapter.ViewHolder {
        val view = inflater.inflate(layout, parent, false)
        return BasketAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: BasketAdapter.ViewHolder, position: Int) {
        holder.basketImg.setImageResource(basketList[position].basketImg)
        holder.tvBasketCount.text = basketList[position].tvBasketCount
    }

    override fun getItemCount(): Int {
        return basketList.size
    }

    fun addItem(basketVO: BasketVO) {
        basketList.add(basketVO)
        notifyItemInserted(basketList.size - 1)
    }

}