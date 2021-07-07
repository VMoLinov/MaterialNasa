package molinov.pictureoftheday.api

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import molinov.pictureoftheday.R
import molinov.pictureoftheday.databinding.ActivityApiBinding

class ApiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityApiBinding

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            viewPager.adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
            indicator.setViewPager(viewPager)
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = when (position) {
                    0 -> "EARTH"
                    1 -> "MARS"
                    2 -> "SYSTEM"
                    else -> "EARTH"
                }
            }.attach()
            tabLayout.getTabAt(0)?.customView =
                layoutInflater.inflate(R.layout.activity_api_custom_tab_earth, null)
            tabLayout.getTabAt(1)?.customView =
                layoutInflater.inflate(R.layout.activity_api_custom_tab_mars, null)
            tabLayout.getTabAt(2)?.customView =
                layoutInflater.inflate(R.layout.activity_api_custom_tab_system, null)
            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    when (position) {
                        0 -> EarthFragment()
                        1 -> MarsFragment()
                        2 -> WeatherFragment()
                        else -> EarthFragment()
                    }
                }
            })
        }
    }
}
