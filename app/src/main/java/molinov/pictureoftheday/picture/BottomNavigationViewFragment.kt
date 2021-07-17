package molinov.pictureoftheday.picture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import molinov.pictureoftheday.R
import molinov.pictureoftheday.databinding.FragmentBottomNavigationViewBinding
import molinov.pictureoftheday.settings.SettingsFragment

class BottomNavigationViewFragment : Fragment() {

    private var _binding: FragmentBottomNavigationViewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomNavigationViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.navigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_view_settings -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.container, SettingsFragment.newInstance())
                        .addToBackStack(null)
                        .commit()
                    true
                }
                R.id.bottom_view_mars -> {
                    true
                }
                R.id.bottom_view_weather -> {
                    true
                }
                R.id.main -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                        .addToBackStack(null)
                        .commit()
                    true
                }
                else -> false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = BottomNavigationViewFragment()
    }
}
