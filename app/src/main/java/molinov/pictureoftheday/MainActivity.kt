package molinov.pictureoftheday

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import molinov.pictureoftheday.databinding.MainActivityBinding
import molinov.pictureoftheday.earth.EarthFragment
import molinov.pictureoftheday.mars.MarsFragment
import molinov.pictureoftheday.picture.PictureOfTheDayFragment
import molinov.pictureoftheday.settings.SettingsFragment
import molinov.pictureoftheday.system.SystemFragment
import molinov.pictureoftheday.util.getActualTheme

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setTheme(getActualTheme(this))
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container,
                    PictureOfTheDayFragment.newInstance()
                )
                .commit()
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
                R.id.bottom_view_earth -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, EarthFragment.newInstance())
                        .addToBackStack(null)
                        .commit()
                    true
                }
                R.id.bottom_view_mars -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, MarsFragment.newInstance())
                        .addToBackStack(null)
                        .commit()
                    true
                }
                R.id.bottom_view_system -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, SystemFragment.newInstance())
                        .addToBackStack(null)
                        .commit()
                    true
                }
                R.id.bottom_view_main -> {
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
}
