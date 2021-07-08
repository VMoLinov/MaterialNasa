package molinov.pictureoftheday.api

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter


class ViewPagerAdapter(
    private val fragmentManager: Fragment,
//    private val lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> EarthFragment()
            1 -> MarsFragment()
            2 -> WeatherFragment()
            else -> EarthFragment()
        }
    }
}
