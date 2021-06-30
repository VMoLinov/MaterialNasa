package molinov.pictureoftheday

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import molinov.pictureoftheday.picture.PictureOfTheDayFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container,
                    PictureOfTheDayFragment.newInstance()
                ) //Why not fabric method is worked? PictureOfTheDayFragment()
                .commitNow()
        }
    }
}
