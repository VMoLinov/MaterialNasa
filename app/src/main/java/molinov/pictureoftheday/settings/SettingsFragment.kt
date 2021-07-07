package molinov.pictureoftheday.settings

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.chip.Chip
import molinov.pictureoftheday.R
import molinov.pictureoftheday.databinding.FragmentSettingsBinding
import molinov.pictureoftheday.util.THEME
import molinov.pictureoftheday.util.THEME_INT

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCheckedChip()
        binding.chipGroup.setOnCheckedChangeListener { group, position ->
            group.findViewById<Chip>(position).let {
                if (it != null) setChipsBehavior(it)
            }
        }
    }

    private fun setChipsBehavior(chip: Chip) {
        val editor = context?.getSharedPreferences(THEME, MODE_PRIVATE)?.edit()
        val value = when (chip.id) {
            (R.id.two) -> R.style.App_POD2
            (R.id.three) -> R.style.App_POD3
            (R.id.four) -> R.style.App_POD4
            else -> R.style.App_POD1
        }
        editor?.putInt(THEME_INT, value)?.apply()
        activity?.recreate()
        Toast.makeText(context, "Выбран ${chip.text}", Toast.LENGTH_SHORT).show()
    }

    private fun setCheckedChip() {
        val shared = context?.getSharedPreferences(THEME, MODE_PRIVATE)
        when (shared?.getInt(THEME_INT, R.style.App_POD1)) {
            (R.style.App_POD1) -> binding.one.isChecked = true
            (R.style.App_POD2) -> binding.two.isChecked = true
            (R.style.App_POD3) -> binding.three.isChecked = true
            (R.style.App_POD4) -> binding.four.isChecked = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = SettingsFragment()
    }
}
