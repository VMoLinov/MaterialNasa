package molinov.pictureoftheday.api

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(private val fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val count = arrayOf("Земля", "Марс", "Погода")

//    override fun getCount(): Int {
//        return count.size
//    }

//    override fun getItem(position: Int): Fragment {
//        return when (position) {
//            0 -> EarthFragment()
//            1 -> MarsFragment()
//            2 -> WeatherFragment()
//            else -> EarthFragment()
//        }
//    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun createFragment(position: Int): Fragment {
        TODO("Not yet implemented")
    }

}
