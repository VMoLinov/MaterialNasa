package molinov.pictureoftheday.api

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import molinov.pictureoftheday.R
import molinov.pictureoftheday.databinding.ActivityApiBinding

private const val EARTH = 0
private const val MARS = 1
private const val WEATHER = 2

class ApiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityApiBinding

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setHighlightedTab(EARTH)
        binding.apply {
            viewPager.adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
            indicator.setViewPager(viewPager)
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = when (position) {
                    EARTH -> "EARTH"
                    MARS -> "MARS"
                    WEATHER -> "SYSTEM"
                    else -> "EARTH"
                }
            }.attach()
            tabLayout.getTabAt(EARTH)?.customView =
                layoutInflater.inflate(R.layout.activity_api_custom_tab_earth, null)
            tabLayout.getTabAt(MARS)?.customView =
                layoutInflater.inflate(R.layout.activity_api_custom_tab_mars, null)
            tabLayout.getTabAt(WEATHER)?.customView =
                layoutInflater.inflate(R.layout.activity_api_custom_tab_system, null)
            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    when (position) {
                        EARTH -> EarthFragment()
                        MARS -> MarsFragment()
                        WEATHER -> WeatherFragment()
                        else -> EarthFragment()
                    }
                }
            })
        }
    }

    private fun setHighlightedTab(position: Int) {
        val layoutInflater = LayoutInflater.from(this@ApiActivity)
        binding.apply {
            tabLayout.getTabAt(EARTH)?.customView = null
            tabLayout.getTabAt(MARS)?.customView = null
            tabLayout.getTabAt(WEATHER)?.customView = null
            when (position) {
                EARTH -> setEarthTabHighlighted(layoutInflater)
                MARS -> setMarsTabHighlighted(layoutInflater)
                WEATHER -> setWeatherTabHighlighted(layoutInflater)
                else -> setEarthTabHighlighted(layoutInflater)
            }
        }
    }

    @SuppressLint("InflateParams")
    private fun setEarthTabHighlighted(layoutInflater: LayoutInflater) {
        val earth = layoutInflater.inflate(R.layout.activity_api_custom_tab_earth, null)
        earth.findViewById<AppCompatTextView>(R.id.tab_image_textview)
            .setTextColor(ContextCompat.getColor(this@ApiActivity, R.color.colorAccent))
        binding.apply {
            tabLayout.getTabAt(EARTH)?.customView = earth
            tabLayout.getTabAt(MARS)?.customView =
                layoutInflater.inflate(R.layout.activity_api_custom_tab_mars, null)
            tabLayout.getTabAt(WEATHER)?.customView =
                layoutInflater.inflate(R.layout.activity_api_custom_tab_system, null)
        }
    }

    @SuppressLint("InflateParams")
    private fun setMarsTabHighlighted(layoutInflater: LayoutInflater) {
        val mars = layoutInflater.inflate(R.layout.activity_api_custom_tab_mars, null)
        mars.findViewById<AppCompatTextView>(R.id.tab_image_textview)
            .setTextColor(ContextCompat.getColor(this@ApiActivity, R.color.colorAccent))
        binding.apply {
            tabLayout.getTabAt(EARTH)?.customView =
                layoutInflater.inflate(R.layout.activity_api_custom_tab_earth, null)
            tabLayout.getTabAt(MARS)?.customView = mars
            tabLayout.getTabAt(WEATHER)?.customView =
                layoutInflater.inflate(R.layout.activity_api_custom_tab_system, null)
        }
    }

    @SuppressLint("InflateParams")
    private fun setWeatherTabHighlighted(layoutInflater: LayoutInflater) {
        val weather = layoutInflater.inflate(R.layout.activity_api_custom_tab_system, null)
        weather.findViewById<AppCompatTextView>(R.id.tab_image_textview)
            .setTextColor(ContextCompat.getColor(this@ApiActivity, R.color.colorAccent))
        binding.apply {
            tabLayout.getTabAt(EARTH)?.customView =
                layoutInflater.inflate(R.layout.activity_api_custom_tab_earth, null)
            tabLayout.getTabAt(MARS)?.customView =
                layoutInflater.inflate(R.layout.activity_api_custom_tab_mars, null)
            tabLayout.getTabAt(WEATHER)?.customView = weather
        }
    }
}
