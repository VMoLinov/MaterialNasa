package molinov.pictureoftheday.picture

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import molinov.pictureoftheday.R
import molinov.pictureoftheday.databinding.MainFragmentBinding
import molinov.pictureoftheday.util.BEFORE_YESTERDAY
import molinov.pictureoftheday.util.TODAY
import molinov.pictureoftheday.util.YESTERDAY
import molinov.pictureoftheday.viewpager.ViewPagerAdapter
import molinov.pictureoftheday.viewpager.ViewPagerItems

class PictureOfTheDayFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
            })
        }
        setTableLayout()
    }

    private fun setTableLayout() {
        binding.tableLayout.apply {
            viewPager.adapter = ViewPagerAdapter(this@PictureOfTheDayFragment)
            indicator.setViewPager(viewPager)
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = when (position) {
                    1 -> resources.getString(R.string.yesterday)
                    2 -> resources.getString(R.string.today)
                    else -> resources.getString(R.string.before_yesterday)
                }
            }.attach()
            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    when (position) {
                        1 -> ViewPagerItems.newInstance(YESTERDAY)
                        2 -> ViewPagerItems.newInstance(TODAY)
                        else -> ViewPagerItems.newInstance(BEFORE_YESTERDAY)
                    }
                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
    }
}
