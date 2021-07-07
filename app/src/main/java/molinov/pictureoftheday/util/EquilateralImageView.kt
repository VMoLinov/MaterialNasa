package molinov.pictureoftheday.util

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

const val BEFORE_YESTERDAY = "BEFORE_YESTERDAY"
const val YESTERDAY = "YESTERDAY"
const val TODAY = "TODAY"
const val RANDOM = "TODAY"
const val THEME = "THEME"
const val THEME_INT = "THEME_INT"

class EquilateralImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
}
