package molinov.pictureoftheday

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import molinov.pictureoftheday.databinding.MainActivityBinding
import molinov.pictureoftheday.picture.BottomNavigationViewFragment
import molinov.pictureoftheday.picture.PictureOfTheDayFragment
import molinov.pictureoftheday.settings.SettingsFragment
import molinov.pictureoftheday.util.THEME
import molinov.pictureoftheday.util.THEME_INT

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(shared())
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container,
                    PictureOfTheDayFragment.newInstance()
                ) //Why not fabric method is worked? PictureOfTheDayFragment()
                .commitNow()
        }
        setBottomNavigation(findViewById(R.id.bottom_navigation_view))
    }

    private fun setBottomNavigation(layout: ConstraintLayout) {
        val navigation = layout.findViewById<BottomNavigationView>(R.id.navigation_view)
        navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_view_settings -> {
                    supportFragmentManager.beginTransaction()
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
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                        .addToBackStack(null)
                        .commit()
                    true
                }
                else -> false
            }
        }
    }

    private fun shared(): Int {
        val theme = getSharedPreferences(THEME, MODE_PRIVATE)
        return theme.getInt(THEME_INT, R.style.App_POD1)
    }
}
