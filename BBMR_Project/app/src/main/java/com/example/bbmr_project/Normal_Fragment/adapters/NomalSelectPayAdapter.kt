import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bbmr_project.R
import java.text.NumberFormat
import java.util.Locale

class NormalSelectPayAdapter(
    val context: Context,
    val layout: Int,
    val _selectedMenuList: MutableList<NormalSelectedMenuInfo>
) : RecyclerView.Adapter<NormalSelectPayAdapter.ViewHolder>() {

    val inflater: LayoutInflater = LayoutInflater.from(context)

    // 속성의 이름을 변경
    val selectedMenuListProperty: List<NormalSelectedMenuInfo>
        get() = _selectedMenuList

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
        val item = _selectedMenuList[position]

        // price가 String이므로 Int로 변환
        val priceInt: Int? = item.price?.replace("[^0-9]".toRegex(), "")?.toIntOrNull()

        // 장바구니에서 수량 증감해도 결제하기 rvSelectPayList에 반영하기 위해
        val calculateMenuCost = priceInt?.let { it * item.tvCount } ?: 0
        val formattedCost =
            NumberFormat.getNumberInstance(Locale.KOREA).format(calculateMenuCost) // 원화단위로 변경

        holder.selectNormalName.text = _selectedMenuList[position].name.toString()
        holder.selectNormalCount.text = _selectedMenuList[position].tvCount.toString()
        holder.selectNormalMoney.text = formattedCost
        holder.selectNormalOption.text = _selectedMenuList[position].options.toString()
        holder.selectNormalOptionCost.text = _selectedMenuList[position].optionTvCount.toString()

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
        return _selectedMenuList.size
    }

    fun removeItem(position: Int) {
        Log.d("RemoveItem", "Trying to remove item at position $position")
        if (position in 0 until _selectedMenuList.size) {
            Log.d("페이어댑터", "Removing item at position $position")
            _selectedMenuList.removeAt(position)
            notifyItemRemoved(position) // 특정 위치의 아이템 갱신
            Log.d("페이어댑터", "Item removed. Updated data: $_selectedMenuList")
        }
    }

    fun updateItems(selectedMenuInfoList: List<NormalSelectedMenuInfo>) {
        _selectedMenuList.clear()  // 기존 데이터를 모두 지우고
        _selectedMenuList.addAll(selectedMenuInfoList)  // 새로운 아이템 추가
        notifyDataSetChanged()
        Log.d("페이어댑터", "Data updated. Updated data: $_selectedMenuList")
    }
}
