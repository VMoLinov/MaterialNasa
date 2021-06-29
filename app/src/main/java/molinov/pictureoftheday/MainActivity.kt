package molinov.pictureoftheday

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import molinov.pictureoftheday.main.MainFragment
import molinov.pictureoftheday.picture.PictureOfTheDayFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment()) //Why not fabric method worked?
                .commitNow()
        }
    }
}
