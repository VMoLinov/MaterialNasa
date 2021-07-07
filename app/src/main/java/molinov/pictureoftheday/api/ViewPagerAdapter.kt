package molinov.pictureoftheday.api

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(
    private val fragmentManager: FragmentManager,
    private val lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return FRAGMENTS
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> EarthFragment()
            1 -> MarsFragment()
            2 -> WeatherFragment()
            else -> EarthFragment()
        }
    }

    companion object {
        const val FRAGMENTS = 3
    }
}
