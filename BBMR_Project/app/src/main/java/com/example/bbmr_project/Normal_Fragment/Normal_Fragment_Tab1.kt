import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bbmr_project.R
import com.example.bbmr_project.Normal_Fragment.Normal_Fragment_Tab1_Beverage
import com.example.bbmr_project.Normal_Fragment.Normal_Fragment_Tab1_Coffee
import com.example.bbmr_project.Normal_Fragment.Normal_Fragment_Tab1_Flatccino
import com.example.bbmr_project.Normal_Fragment.Normal_Fragment_Tab1_Shake
import com.example.bbmr_project.databinding.FragNormalTab1Binding

class Normal_Fragment_Tab1 : Fragment() {

    private lateinit var binding: FragNormalTab1Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragNormalTab1Binding.inflate(inflater)

        // 초기에 Fragment2_1_Coffee를 설정
        childFragmentManager.beginTransaction().replace(R.id.flMenu, Normal_Fragment_Tab1_Coffee()).commit()

        binding.btnGroup.setOnCheckedChangeListener { _, checkedId ->
            // 체크된 버튼에 따라 해당하는 Fragment로 교체
            when (checkedId) {
                R.id.btnCoffee -> replaceFragment(Normal_Fragment_Tab1_Coffee())
                R.id.btnBeverage -> replaceFragment(Normal_Fragment_Tab1_Beverage())
                R.id.btnFlatccino -> replaceFragment(Normal_Fragment_Tab1_Flatccino())
                R.id.btnShake -> replaceFragment(Normal_Fragment_Tab1_Shake())
            }
        }

        return binding.root
    }

    private fun replaceFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction().replace(R.id.flMenu, fragment).addToBackStack(null).commit()
    }
}
