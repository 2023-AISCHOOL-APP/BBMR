package com.example.bbmr_project.Senior_Fragment.seniorAdapters

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.widget.Button
import com.example.bbmr_project.CartStorage
import com.example.bbmr_project.OnCartChangeListener
import com.example.bbmr_project.Product
import com.example.bbmr_project.R

class SeniorSelectBasketAdapter(
    val context: Context,
    val layout: Int,
    val basketSeniorList: ArrayList<Product>,
    val seniorTotalCost : OnCartChangeListener
) : RecyclerView.Adapter<SeniorSelectBasketAdapter.ViewHolder>() {

    fun updateData(newList : ArrayList<Product>){
        basketSeniorList.clear()
        basketSeniorList.addAll(newList)
        notifyDataSetChanged()
    }

    val inflater : LayoutInflater = LayoutInflater.from(context)

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val basketImgSenior : ImageView = view.findViewById(R.id.basketImgSenior)
        val tvBasketNameSenior : TextView = view.findViewById(R.id.tvBasketNameSenior)
        val tvBasketCountSenior : TextView = view.findViewById(R.id.tvBasketCountSenior)
        val btnBasketMinusSenior : Button = view.findViewById(R.id.btnBasketMinusSenior)
        val btnBasketPlusSenior : Button = view.findViewById(R.id.btnBasketPlusSenior)
        val btnBasketCancellSenior : Button = view.findViewById(R.id.btnBasketCancelSenior)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SeniorSelectBasketAdapter.ViewHolder {
        val view = inflater.inflate(layout, parent, false)
        return SeniorSelectBasketAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: SeniorSelectBasketAdapter.ViewHolder, position: Int) {
        val selectSeniorItem = basketSeniorList[position]

//        holder.basketImgSenior.setImageResource(selectSeniorItem.image)
        holder.tvBasketNameSenior.text = selectSeniorItem.name
        holder.tvBasketCountSenior.text = selectSeniorItem.count.toString()

        // 시니어 장바구니에서 plus 버튼 클릭
        holder.btnBasketPlusSenior.setOnClickListener {
            var basicCount = selectSeniorItem.count
            var basicPrice = selectSeniorItem.price
            Log.d("기본값 ", "${basicCount}")
            Log.d("기본값 ", "${basicPrice}")

            selectSeniorItem.count++
            selectSeniorItem.price = basicPrice + (basicPrice / basicCount)
            notifyItemChanged(position)

//            CartStorage.getFormmatPrice(selectSeniorItem)
            seniorTotalCost.onChange(CartStorage.getProductList())

//            val addPrice = selectSeniorItem.formatPrice()
//            Log.d("더한 값", addPrice)

        }
        // 시니어 장바구니에서 minus 버튼 클릭Bak
        holder.btnBasketMinusSenior.setOnClickListener {
           if (selectSeniorItem.count > 1){
//               selectSeniorItem.count--
//               notifyItemChanged(position)
               var basicCount = selectSeniorItem.count
               var basicPrice = selectSeniorItem.price
               Log.d("기본값 ", "${basicCount}")
               Log.d("기본값 ", "${basicPrice}")

               selectSeniorItem.count--
               selectSeniorItem.price = basicPrice - (basicPrice / basicCount)

               notifyItemChanged(position)
               seniorTotalCost.onChange(CartStorage.getProductList())

           }
        }
        // 시니어 장바구니에서 X 버튼 클릭
        holder.btnBasketCancellSenior.setOnClickListener {
            val removedPosition = holder.adapterPosition
            val removedItem = basketSeniorList.removeAt(removedPosition)
            notifyItemChanged(removedPosition)

            CartStorage.removeProduct(removedItem)
            seniorTotalCost.onChange(CartStorage.getProductList())
        }
    }

    override fun getItemCount(): Int {
        return basketSeniorList.size
    }



}