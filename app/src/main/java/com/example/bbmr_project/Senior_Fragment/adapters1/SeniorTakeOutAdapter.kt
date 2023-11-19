package com.example.bbmr_project.Senior_Fragment.adapters1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bbmr_project.Dialog.Senior_MenuDialog
import com.example.bbmr_project.R
import com.example.bbmr_project.VO.Senior_TakeOutVO


interface ItemClickListener {
    fun onItemClick(item: Senior_TakeOutVO)
}

// RecyclerView Adapter 클래스 정의
class SeniorTakeOutAdapter (val context: Context, val layout : Int, val menuList: ArrayList<Senior_TakeOutVO>,
                            private val itemClickListener: ItemClickListener? = null,
                            private val fragmentManager: FragmentManager
)
    : RecyclerView.Adapter<SeniorTakeOutAdapter.ViewHolder>() {



    // LayoutInflater를 이용하여 레이아웃을 인플레이트하기 위한객체 초기화
    val inflater: LayoutInflater = LayoutInflater.from(context)

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgS: ImageView = view.findViewById(R.id.imgS)
        val tvNameS: TextView = view.findViewById(R.id.tvNameS)
        val tvPriceS: TextView = view.findViewById(R.id.tvPriceS)
    }

    fun updateList(newList: List<Senior_TakeOutVO>) {
        menuList.clear()
        menuList.addAll(newList)
        notifyDataSetChanged()
    }


    // ViewHolder 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val view = inflater.inflate(layout, parent, false)
        return ViewHolder(view)
    }

    // ViewHolder에 데이터 바인딩
    // 메뉴 사진, 이름, 가격 설정
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvNameS.text = menuList[position].sname
        holder.tvPriceS.text = menuList[position].sprice
        holder.imgS.setImageResource(menuList[position].simg)
        holder.itemView.setOnClickListener {
            itemClickListener?.onItemClick(menuList[position])

            val siniorDialog = Senior_MenuDialog.newInstance(menuList[position])
            siniorDialog.show(fragmentManager, "siniorDialog")

        }
    }
    // 데이터 아이템 개수 반환
    override fun getItemCount(): Int {
        return menuList.size
    }

}
