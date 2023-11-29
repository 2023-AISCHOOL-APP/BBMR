package com.example.bbmr_project.Senior_Fragment.seniorAdapters

import android.content.Context
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bbmr_project.Dialog.SeniorDessertDialog
import com.example.bbmr_project.Dialog.SeniorMenuDialog
import com.example.bbmr_project.R
import com.example.bbmr_project.Senior_Fragment.Category
import com.example.bbmr_project.VO.Senior_TakeOutVO


interface ItemClickListener {
    fun onItemClick(item: Senior_TakeOutVO)
}

// RecyclerView Adapter 클래스 정의
class SeniorTakeOutAdapter(
    val context: Context,
    val layout: Int,
    private val category: Category,
    private val itemClickListener: ItemClickListener?,
    private val fragmentManager: FragmentManager,
) : RecyclerView.Adapter<SeniorTakeOutAdapter.ViewHolder>() {

    private val menuList = ArrayList<Senior_TakeOutVO>()

    // LayoutInflater를 이용하여 레이아웃을 인플레이트하기 위한객체 초기화
    private val inflater: LayoutInflater = LayoutInflater.from(context)

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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(layout, parent, false)
        return ViewHolder(view)
    }

    // ViewHolder에 데이터 바인딩
    // 메뉴 사진, 이름, 가격 설정
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(menuList[position].simg).into(holder.imgS)
        Log.d("안녕하세요", menuList.toString())
        holder.tvNameS.text = menuList[position].sname
        // ------ 메뉴 목록 이름 접근해서 줄 바꿈 코드 시작 ------
        val text = menuList[position].sname
        val spannableStringBuilder = SpannableStringBuilder(text)
//        if (text.length >= 6 && text.contains(" ")) {
//            val indexLine = text.indexOf(' ')
//            val modifiedText = StringBuilder(text)
//                .replace(indexLine, indexLine + 1, "\n")
//                .toString()
//            spannableStringBuilder.replace(0, text.length, modifiedText)
//            holder.tvNameS.text = spannableStringBuilder
//        } else if (text.length >= 7) { // 카라멜마끼야또 저격
//            val modifiedText = StringBuilder(text)
//                .insert(3, "\n").toString()
//            spannableStringBuilder.replace(0, text.length, modifiedText)
//            holder.tvNameS.text = spannableStringBuilder
//        } else { // 띄어쓰기 제외한 것 들
//            holder.tvNameS.text = menuList[position].sname
//        }
        if (text.contains(" ")) {
            val indexLine = text.indexOf(' ')
            val modifiedText = StringBuilder(text)
                .replace(indexLine, indexLine + 1, "\n")
                .toString()
            spannableStringBuilder.replace(0, text.length, modifiedText)
            holder.tvNameS.text = spannableStringBuilder
        }
        // ------ 메뉴 목록 이름 접근해서 줄 바꿈 코드 끝 ------

        // 기본값을 1000단위로 나누는 코드
        val basicPrice = String.format("%,d 원", menuList[position].sprice)

        holder.tvPriceS.text = basicPrice
//        holder.imgS.setImageResource(menuList[position].simg)

        holder.itemView.setOnClickListener {
            itemClickListener?.onItemClick(menuList[position])
            // MenuDialog에 값을 보내주기 위한 코드
            if (category == Category.DESSERT){
                showDessertDialaog(position)
            } else {
                showMenuDialog(position)

            }
        }
    }

    private fun showMenuDialog(position: Int){
        // MenuDialog에 값을 보내주는 코드
        val seniorMenuDialog = SeniorMenuDialog()
        val bundle = Bundle().apply{
            putParcelable("seniorTakeOutVO", menuList[position])
        }
        seniorMenuDialog.arguments = bundle
        seniorMenuDialog.show(fragmentManager, "seniorDialog")
    }

    private fun showDessertDialaog(position: Int){
        val seniorDessertDialog = SeniorDessertDialog()
        val bundle = Bundle().apply {
            putParcelable("seniorTakeOutVO", menuList[position])
        }
        seniorDessertDialog.arguments = bundle
        seniorDessertDialog.show(fragmentManager, "seniorDialog")
    }

    // 데이터 아이템 개수 반환
    override fun getItemCount(): Int {
        return menuList.size
    }

}
