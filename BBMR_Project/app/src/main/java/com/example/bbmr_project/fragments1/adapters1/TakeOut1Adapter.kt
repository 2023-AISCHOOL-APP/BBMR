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




// RecyclerView Adapter 클래스 정의
class TakeOut1Adapter (val context: Context, val layout : Int, val menuList: ArrayList<TakeOut1VO>)
    : RecyclerView.Adapter<TakeOut1Adapter.ViewHolder>() {

    // LayoutInflater를 이용하여 레이아웃을 인플레이트하기 위한객체 초기화
    private val inflater: LayoutInflater = LayoutInflater.from(context)


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgS: ImageView = view.findViewById(R.id.imgS)
        val tvNameS: TextView = view.findViewById(R.id.tvNameS)
        val tvPriceS: TextView = view.findViewById(R.id.tvPriceS)
    }

    fun updateList(newList: List<TakeOut1VO>) {
        menuList.clear()
        menuList.addAll(newList)
        notifyDataSetChanged()
    }


    // ViewHolder 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val view = inflater.inflate(layout, parent, false)
        return ViewHolder(view)
    }


    // 데이터 아이템 개수 반환
    override fun getItemCount(): Int {
        return menuList.size
    }


    // ViewHolder에 데이터 바인딩
    // 메뉴 사진, 이름, 가격 설정
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imgS.scaleType = ImageView.ScaleType.CENTER_CROP
        holder.imgS.setImageResource(menuList[position].img)
        holder.tvNameS.text = menuList[position].name
        holder.tvPriceS.text = menuList[position].price


    }

}