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
    val selectedMenuList: MutableList<NormalSelectedMenuInfo>
) : RecyclerView.Adapter<NormalSelectPayAdapter.ViewHolder>() {

    val inflater: LayoutInflater = LayoutInflater.from(context)

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val selectNormalName: TextView = view.findViewById(R.id.tvSelectNormalName)
        val selectNormalCount: TextView = view.findViewById(R.id.tvSelectNormalCount)
        val selectNormalMoney: TextView = view.findViewById(R.id.tvSelectNormalMoney)
        val selectNormalOption1: TextView = view.findViewById(R.id.selectNormalOption1)
        val selectNormalOptionCost: TextView = view.findViewById(R.id.selectNormalOptionCost1)
        val temperature2: TextView = view.findViewById(R.id.temperature2)
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

        // price가 String이므로 Int로 변환
        val priceInt: Int? = item.price?.replace("[^0-9]".toRegex(), "")?.toIntOrNull()

        // 장바구니에서 수량 증감해도 결제하기 rvSelectPayList에 반영하기 위해
        val calculateMenuCost = priceInt?.let { it * item.tvCount } ?: 0
        val formattedCost =
            NumberFormat.getNumberInstance(Locale.KOREA).format(calculateMenuCost) // 원화단위로 변경

        holder.selectNormalName.text = selectedMenuList[position].name.toString()
        holder.selectNormalCount.text = selectedMenuList[position].tvCount.toString()
        holder.selectNormalMoney.text = formattedCost
        holder.selectNormalOption1.text = selectedMenuList[position].options.toString()
        holder.selectNormalOptionCost.text = selectedMenuList[position].optionTvCount.toString()
        holder.temperature2.text = "옵션: " + selectedMenuList[position].temperature.toString()


        Log.d("페이어댑터", "${item.temperature}, ${holder.temperature2.text}")


        // 옵션 리스트가 비어있는지 확인
        if (item.options.isEmpty()) {
            // 옵션 리스트가 비어있으면 관련 UI 숨기기
            holder.selectNormalOption1.visibility = View.GONE
            holder.selectNormalOptionCost.visibility = View.GONE
        } else {
            // 옵션 리스트가 있으면 텍스트 설정
            holder.selectNormalOption1.text = item.options.joinToString(", ")
            holder.selectNormalOption1.visibility = View.VISIBLE
            holder.selectNormalOptionCost.text = item.optionTvCount.toString()
            holder.selectNormalOptionCost.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return selectedMenuList.size
    }
}
