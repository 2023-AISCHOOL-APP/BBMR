package com.example.bbmr_project.Normal_Fragment.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bbmr_project.R
import com.example.bbmr_project.VO.NormalSelectBasketVO

class NormalSelectBasketAdapter (val context: Context, val layout: Int, val basketList: MutableList<NormalSelectBasketVO>)
    : RecyclerView.Adapter<NormalSelectBasketAdapter.ViewHolder>(){

    val inflater : LayoutInflater = LayoutInflater.from(context)

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val basketImg : ImageView = view.findViewById(R.id.basketImg)
        val tvBasketCount : TextView = view.findViewById(R.id.tvBasketCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NormalSelectBasketAdapter.ViewHolder {
        val view = inflater.inflate(layout, parent, false)
        return NormalSelectBasketAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: NormalSelectBasketAdapter.ViewHolder, position: Int) {
        holder.basketImg.setImageResource(basketList[position].basketImg)
        holder.tvBasketCount.text = basketList[position].tvBasketCount
    }

    override fun getItemCount(): Int {
        return basketList.size
    }

    fun addItem(normalSelectBasketVO: NormalSelectBasketVO) {
        basketList.add(normalSelectBasketVO)
        notifyItemInserted(basketList.size - 1)
    }

}