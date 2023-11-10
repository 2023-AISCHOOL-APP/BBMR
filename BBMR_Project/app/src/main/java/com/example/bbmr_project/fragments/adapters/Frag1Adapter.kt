package com.example.bbmr_project.fragments.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bbmr_project.R
import com.example.bbmr_project.VO.Frag1VO

class Frag1Adapter (val context: Context, val layout : Int, val frag1List: ArrayList<Frag1VO>)
    : RecyclerView.Adapter<Frag1Adapter.ViewHolder>(){

        val inflater : LayoutInflater = LayoutInflater.from(context)

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val img : ImageView = view.findViewById(R.id.img)
        val tvName : TextView = view.findViewById(R.id.tvName)
        val tvPrice : TextView = view.findViewById(R.id.tvPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Frag1Adapter.ViewHolder {
        val view = inflater.inflate(layout, parent, false)
        return Frag1Adapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: Frag1Adapter.ViewHolder, position: Int) {
        holder.tvName.text = frag1List[position].name
        holder.tvPrice.text = frag1List[position].Price
        holder.img.setImageResource(frag1List[position].img)
    }

    override fun getItemCount(): Int {
        return frag1List.size
    }
}