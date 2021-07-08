package molinov.pictureoftheday.viewpager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import molinov.pictureoftheday.util.BEFORE_YESTERDAY
import molinov.pictureoftheday.util.TODAY
import molinov.pictureoftheday.util.YESTERDAY


class ViewPagerAdapter(
    private val fragmentManager: Fragment,
) : FragmentStateAdapter(fragmentManager) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            1 -> ViewPagerItems(YESTERDAY)
            2 -> ViewPagerItems(TODAY)
            else -> ViewPagerItems(BEFORE_YESTERDAY)
        }
    }
}
