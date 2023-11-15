package com.example.bbmr_project.fragments2.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bbmr_project.NDialog.NMenuDialog
import com.example.bbmr_project.R
import com.example.bbmr_project.VO.TakeOut2VO
interface ItemClickListener {
    fun onItemClick(item: TakeOut2VO)
}

class TakeOut2Adapter(val context: Context, val layout: Int, val frag1List: List<TakeOut2VO>,
                      private val fragmentManager: FragmentManager,
                      private val itemClickListener: ItemClickListener? = null)
    : RecyclerView.Adapter<TakeOut2Adapter.ViewHolder>(){

        val inflater : LayoutInflater = LayoutInflater.from(context)

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val img : ImageView = view.findViewById(R.id.img)
        val tvName : TextView = view.findViewById(R.id.tvName)
        val tvPrice : TextView = view.findViewById(R.id.tvPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TakeOut2Adapter.ViewHolder {
        val view = inflater.inflate(layout, parent, false)
        return TakeOut2Adapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: TakeOut2Adapter.ViewHolder, position: Int) {
        holder.tvName.text = frag1List[position].name
        holder.tvPrice.text = frag1List[position].Price
        holder.img.setImageResource(frag1List[position].img)
        holder.itemView.setOnClickListener {
            Log.d("TakeOut2Adapter", "Item clicked at position $position")
            // 아이템 클릭 이벤트 전달
            itemClickListener?.onItemClick(frag1List[position])

            // NMenuDialog를 보여주기
            val nMenuDialog = NMenuDialog()
            nMenuDialog.show(fragmentManager, "NMenuDialog")
        }

    }

    override fun getItemCount(): Int {
        return frag1List.size
    }
}