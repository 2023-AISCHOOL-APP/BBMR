package com.example.bbmr_project.Normal_Fragment.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bbmr_project.Dialog.Normal_MenuDialog
import com.example.bbmr_project.R
import com.example.bbmr_project.VO.NormalTakeOutVO


interface ItemClickListener {
    fun onItemClick(item: NormalTakeOutVO)
}

class NormalTakeOutAdapter(val context: Context, val layout: Int, val frag1List: List<NormalTakeOutVO>,
                      private val fragmentManager: FragmentManager,
                      private val itemClickListener: ItemClickListener? = null)
    : RecyclerView.Adapter<NormalTakeOutAdapter.ViewHolder>(){

        val inflater : LayoutInflater = LayoutInflater.from(context)

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val img : ImageView = view.findViewById(R.id.img)
        val tvName : TextView = view.findViewById(R.id.tvName)
        val tvPrice : TextView = view.findViewById(R.id.tvPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NormalTakeOutAdapter.ViewHolder {
        val view = inflater.inflate(layout, parent, false)
        return NormalTakeOutAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: NormalTakeOutAdapter.ViewHolder, position: Int) {
        holder.tvName.text = frag1List[position].name
        holder.tvPrice.text = frag1List[position].price
        holder.img.setImageResource(frag1List[position].img)
        holder.itemView.setOnClickListener {
            Log.d("TakeOut2Adapter", "Item clicked at position $position")
            // 아이템 클릭 이벤트 전달
            itemClickListener?.onItemClick(frag1List[position])

            // NMenuDialog를 보여주기
            val normalMenuDialog = Normal_MenuDialog()
            normalMenuDialog.show(fragmentManager, "NMenuDialog")
        }

    }

    override fun getItemCount(): Int {
        return frag1List.size
    }
}