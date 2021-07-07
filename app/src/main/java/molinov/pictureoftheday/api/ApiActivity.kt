package molinov.pictureoftheday.api

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import molinov.pictureoftheday.R
import molinov.pictureoftheday.databinding.ActivityApiBinding

class ApiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityApiBinding

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
            tabLayout.getTabAt(0)?.setIcon(R.drawable.ic_earth)
            tabLayout.getTabAt(1)?.setIcon(R.drawable.ic_mars)
            tabLayout.getTabAt(2)?.setIcon(R.drawable.ic_system)
        }
    }
}
