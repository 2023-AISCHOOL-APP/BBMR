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
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NormalSelectPayAdapter.ViewHolder {
        val view = inflater.inflate(layout, parent, false)
        return NormalSelectPayAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: NormalSelectPayAdapter.ViewHolder, position: Int) {
        holder.selectNormalName.text = selectedMenuList[position].name.toString()
        holder.selectNormalCount.text = selectedMenuList[position].tvCount.toString()
        holder.selectNormalMoney.text = selectedMenuList[position].price.toString()
    }

    override fun getItemCount(): Int {
        return selectedMenuList.size
    }

}
