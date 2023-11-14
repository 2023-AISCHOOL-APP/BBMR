package com.example.bbmr_project.fragments1.adapters1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bbmr_project.R
import com.example.bbmr_project.VO.TakeOut1VO





class TakeOut1Adapter (val context: Context, val layout : Int, val menuList: ArrayList<TakeOut1VO>)
    : RecyclerView.Adapter<TakeOut1Adapter.ViewHolder>() {

    val inflater = LayoutInflater.from(context)



    class ViewHolder(view : View, ) : RecyclerView.ViewHolder(view){
        val imgS : ImageView = view.findViewById(R.id.imgS)
        val tvNameS : TextView
        val tvPriceS : TextView
        init {
            tvNameS = view.findViewById(R.id.tvNameS)
            tvPriceS = view.findViewById(R.id.tvPriceS)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TakeOut1Adapter.ViewHolder {
        val view = inflater.inflate(layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    override fun onBindViewHolder(holder: TakeOut1Adapter.ViewHolder, position: Int) {
        holder.imgS.scaleType = ImageView.ScaleType.CENTER_CROP
        holder.imgS.setImageResource(menuList[position].img)
        holder.tvNameS.text = menuList[position].name
        holder.tvPriceS.text = menuList[position].price

    }

}