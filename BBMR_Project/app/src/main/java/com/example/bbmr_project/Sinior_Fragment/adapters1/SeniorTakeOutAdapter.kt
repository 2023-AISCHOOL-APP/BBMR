package com.example.bbmr_project.Sinior_Fragment.adapters1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bbmr_project.Dialog.Senior_MenuDialog
import com.example.bbmr_project.Normal_Fragment.adapters.ItemClickListener
import com.example.bbmr_project.R
import com.example.bbmr_project.Sinior_Fragment.Senior_Fragment_Tab_Recommend
import com.example.bbmr_project.VO.Senior_TakeOutVO

// 정확한 예시가 모르겠음
// 외부의 접근 유무



// RecyclerView Adapter 클래스 정의
class SeniorTakeOutAdapter (val context: Context, val layout : Int, val menuList: ArrayList<Senior_TakeOutVO>,
//                            private val itemClickListener: ItemClickListener? = null,
//                            private val fragmentManager: FragmentManager
)
    : RecyclerView.Adapter<SeniorTakeOutAdapter.ViewHolder>() {

//    interface ItemClickListener {
//        fun onItemClick(item: Senior_TakeOutVO)
//    }


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
        holder.imgS.setImageResource(menuList[position].simg)
        holder.tvNameS.text = menuList[position].sname
        holder.tvPriceS.text = menuList[position].sprice

//        holder.itemView.setOnClickListener {
//            itemClickListener?.onItemClick(menuList[position])
//
//            val siniorDialog = Senior_MenuDialog()
//            siniorDialog.show(fragmentManager, "siniorDialog")
//
//        }


    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
    }

}
