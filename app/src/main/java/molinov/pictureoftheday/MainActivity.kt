package molinov.pictureoftheday

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import molinov.pictureoftheday.picture.PictureOfTheDayFragment
import molinov.pictureoftheday.util.THEME
import molinov.pictureoftheday.util.THEME_INT

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(shared())
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

    private fun shared(): Int {
        val theme = getSharedPreferences(THEME, MODE_PRIVATE)
        return theme.getInt(THEME_INT, R.style.App_POD1)
    }
}
