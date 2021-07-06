package molinov.pictureoftheday.api

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.ViewPager
import molinov.pictureoftheday.R

class ApiActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_api)
        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        findViewById<ViewPager>(R.id.view_pager).adapter = viewPagerAdapter
    }
}
