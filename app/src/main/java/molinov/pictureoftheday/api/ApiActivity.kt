package molinov.pictureoftheday.api

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import molinov.pictureoftheday.R

class ApiActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_api)
        findViewById<ViewPager2>(R.id.view_pager).adapter =
            ViewPagerAdapter(FragmentActivity())
    }
}
