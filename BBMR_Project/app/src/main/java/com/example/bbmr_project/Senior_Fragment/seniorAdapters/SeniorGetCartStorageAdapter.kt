package com.example.bbmr_project.Senior_Fragment.seniorAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bbmr_project.Product
import com.example.bbmr_project.R
//val context: Context, val layout : Int, val cartlist : ArrayList<Product>

// Senior_MenuDialog
interface Senior_MenuDialogListener{
    fun onSeniorMenuAdd(
        price : Int,
        count : Int,
    )
    fun onSeniorMenuSelectForPay(selectMenuInof : List<Product>)
}

interface TotalSeniorCostListener{
    fun onTotalCostUpdate(totalCost : Int,  )
}



class SeniorGetCartStorageAdapter (val context: Context, private val productList : List<Product>)
    :RecyclerView.Adapter<SeniorGetCartStorageAdapter.ViewHolder>(){

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val tvMenuName : TextView = view.findViewById(R.id.tvMenuName)
        val tvMenuPrice : TextView = view.findViewById(R.id.tvMenuPrice)
        val tvCount : TextView = view.findViewById(R.id.tvSeniorCount)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SeniorGetCartStorageAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_senior_takeout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: SeniorGetCartStorageAdapter.ViewHolder, position: Int) {
        val product = productList[position]
        holder.tvMenuName.text = product.name
        holder.tvMenuPrice.text = product.price.toString()
        holder.tvCount.text =product.count.toString()

    }

    override fun getItemCount(): Int {
        return productList.size
    }
}