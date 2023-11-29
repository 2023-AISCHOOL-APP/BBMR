package com.example.bbmr_project.Normal_Fragment.adapters
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
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
        val item = frag1List[position]
        Glide.with(context)
            .load(item.img)
            .apply(RequestOptions().placeholder(null)) // 로딩 중에 표시할 플레이스홀더 이미지
            .into(holder.img)

        holder.tvName.text = frag1List[position].name
        val basicPrice = String.format("%,d 원", frag1List[position].price) // 1,000단위
        holder.tvPrice.text = basicPrice
        holder.itemView.setOnClickListener {
            itemClickListener?.onItemClick(frag1List[position])
        }

    }
    override fun getItemCount(): Int {
        return frag1List.size
    }
}