package molinov.pictureoftheday.api

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(
    private val fragmentActivity: FragmentActivity,
) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> EarthFragment()
            1 -> MarsFragment()
            2 -> WeatherFragment()
            else -> EarthFragment()
        }
    }
}
