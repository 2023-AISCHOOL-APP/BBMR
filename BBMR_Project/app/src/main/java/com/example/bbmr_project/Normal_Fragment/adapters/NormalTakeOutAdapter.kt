package com.example.bbmr_project.Normal_Fragment.adapters
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bbmr_project.CartStorage.menuList
import com.example.bbmr_project.R
import com.example.bbmr_project.VO.NormalTakeOutVO

interface ItemClickListener {
    fun onItemClick(item: NormalTakeOutVO)
}

class NormalTakeOutAdapter(
    val context: Context, val layout: Int, val frag1List: List<NormalTakeOutVO>,
    private val fragmentManager: FragmentManager,
    private val itemClickListener: ItemClickListener?
) : RecyclerView.Adapter<NormalTakeOutAdapter.ViewHolder>() {
    val inflater: LayoutInflater = LayoutInflater.from(context)
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val img: ImageView = view.findViewById(R.id.img)
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvPrice: TextView = view.findViewById(R.id.tvPrice)
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NormalTakeOutAdapter.ViewHolder {
        val view = inflater.inflate(layout, parent, false)
        return NormalTakeOutAdapter.ViewHolder(view)
    }
    override fun onBindViewHolder(holder: NormalTakeOutAdapter.ViewHolder, position: Int) {
//        holder.img.setImageResource(frag1List[position].img)
        holder.tvName.text = frag1List[position].name
        holder.tvPrice.text = frag1List[position].price.toString()
        holder.itemView.setOnClickListener {
            itemClickListener?.onItemClick(frag1List[position])
        }

    }
    override fun getItemCount(): Int {
        return menuList.size
    }
}