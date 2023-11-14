import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bbmr_project.R
import com.example.bbmr_project.fragments2.Fragment2_1_Beverage
import com.example.bbmr_project.fragments2.Fragment2_1_Coffee
import com.example.bbmr_project.fragments2.Fragment2_1_Flatccino
import com.example.bbmr_project.fragments2.Fragment2_1_Shake
import com.example.bbmr_project.databinding.FragmentN1Binding

class Fragment2_1 : Fragment() {

    private lateinit var binding: FragmentN1Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentN1Binding.inflate(inflater)

        // 초기에 Fragment2_1_Coffee를 설정
        childFragmentManager.beginTransaction().replace(R.id.flMenu, Fragment2_1_Coffee()).commit()

        binding.btnGroup.setOnCheckedChangeListener { _, checkedId ->
            // 체크된 버튼에 따라 해당하는 Fragment로 교체
            when (checkedId) {
                R.id.btnCoffee -> replaceFragment(Fragment2_1_Coffee())
                R.id.btnBeverage -> replaceFragment(Fragment2_1_Beverage())
                R.id.btnFlatccino -> replaceFragment(Fragment2_1_Flatccino())
                R.id.btnShake -> replaceFragment(Fragment2_1_Shake())
            }
        }
        return binding.root
    }

    private fun replaceFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction().replace(R.id.flMenu, fragment).addToBackStack(null).commit()
    }
}
