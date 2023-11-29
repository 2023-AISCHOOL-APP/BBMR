import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bbmr_project.R
import com.example.bbmr_project.Normal_Fragment.Normal_Fragment_Tab1_Menu
import com.example.bbmr_project.databinding.FragNormalTab1Binding

class Normal_Fragment_Tab1 : Fragment() {

    private lateinit var binding: FragNormalTab1Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragNormalTab1Binding.inflate(inflater)

        // 초기화면 커피로 설정
        childFragmentManager.beginTransaction().replace(R.id.flMenu, Normal_Fragment_Tab1_Menu(type = 0)).commit()

        binding.btnGroup.setOnCheckedChangeListener { _, checkedId ->
            // 체크된 버튼에 따라 해당하는 Fragment로 교체
            when (checkedId) {
                R.id.btnCoffee -> replaceFragment(Normal_Fragment_Tab1_Menu(0))
                R.id.btnBeverage -> replaceFragment(Normal_Fragment_Tab1_Menu(1))
                R.id.btnFlatccino -> replaceFragment(Normal_Fragment_Tab1_Menu(2))
                R.id.btnShake -> replaceFragment(Normal_Fragment_Tab1_Menu(3))
            }
        }

        return binding.root
    }

    private fun replaceFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction().replace(R.id.flMenu, fragment).commit()
    }
}
