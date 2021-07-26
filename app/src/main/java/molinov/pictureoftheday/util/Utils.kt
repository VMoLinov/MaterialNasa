package molinov.pictureoftheday.util

import androidx.appcompat.app.AppCompatActivity
import molinov.pictureoftheday.R

const val BEFORE_YESTERDAY = "BEFORE_YESTERDAY"
const val YESTERDAY = "YESTERDAY"
const val TODAY = "TODAY"
const val RANDOM = "TODAY"
const val THEME = "THEME"
const val THEME_INT = "THEME_INT"

val RECYCLER_NAMES = listOf("EARTH", "MARS")

fun getActualTheme(activity: AppCompatActivity): Int {
    val theme = activity.getSharedPreferences(THEME, AppCompatActivity.MODE_PRIVATE)
    return theme.getInt(THEME_INT, R.style.App_POD1)
}
