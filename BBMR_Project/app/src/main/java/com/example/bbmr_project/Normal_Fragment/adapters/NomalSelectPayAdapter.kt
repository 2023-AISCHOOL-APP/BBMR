import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bbmr_project.R

class NormalSelectPayAdapter(
    val context: Context,
    val layout: Int,
    val selectedMenuList: MutableList<NormalSelectedMenuInfo>
) : RecyclerView.Adapter<NormalSelectPayAdapter.ViewHolder>() {

    val inflater: LayoutInflater = LayoutInflater.from(context)

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val selectNormalName: TextView = view.findViewById(R.id.tvSelectNormalName)
        val selectNormalCount: TextView = view.findViewById(R.id.tvSelectNormalCount)
        val selectNormalMoney: TextView = view.findViewById(R.id.tvSelectNormalMoney)
        val selectNormalOption: TextView = view.findViewById(R.id.selectNormalOption)
        val selectNormalOptionCost: TextView = view.findViewById(R.id.selectNormalOptionCost)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NormalSelectPayAdapter.ViewHolder {
        val view = inflater.inflate(layout, parent, false)
        return NormalSelectPayAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: NormalSelectPayAdapter.ViewHolder, position: Int) {
        val item = selectedMenuList[position]

        holder.selectNormalName.text = selectedMenuList[position].name.toString()
        holder.selectNormalCount.text = selectedMenuList[position].tvCount.toString()
        holder.selectNormalMoney.text = selectedMenuList[position].menuPrice.toString()
        holder.selectNormalOption.text = selectedMenuList[position].options.toString()
        holder.selectNormalOptionCost.text = selectedMenuList[position].optionTvCount.toString()

        // 옵션 리스트가 비어있는지 확인
        if (item.options.isEmpty()) {
            // 옵션 리스트가 비어있으면 관련 UI 숨기기
            holder.selectNormalOption.visibility = View.GONE
            holder.selectNormalOptionCost.visibility = View.GONE
        } else {
            // 옵션 리스트가 있으면 텍스트 설정
            holder.selectNormalOption.text = item.options.joinToString(", ")
            holder.selectNormalOption.visibility = View.VISIBLE
            holder.selectNormalOptionCost.text = item.optionTvCount.toString()
            holder.selectNormalOptionCost.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return selectedMenuList.size
    }

    fun updateItems(selectedMenuInfoList: List<NormalSelectedMenuInfo>) {
        selectedMenuList.addAll(selectedMenuInfoList)  // 새로운 아이템 추가
        notifyDataSetChanged()
    }
}
