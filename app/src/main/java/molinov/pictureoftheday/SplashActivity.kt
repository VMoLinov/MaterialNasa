package molinov.pictureoftheday

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AlphaAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView

class SplashActivity : AppCompatActivity() {

//    private var handler = Handler(Looper.myLooper()!!)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        findViewById<AppCompatImageView>(R.id.image_view)
            .animate()
            .rotationBy(750f)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .setDuration(3000)
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {}
                override fun onAnimationEnd(animation: Animator?) {
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                }

                override fun onAnimationCancel(animation: Animator?) {}
                override fun onAnimationRepeat(animation: Animator?) {}
            })
        val alphaAnimation = AlphaAnimation(0f, 1f)
        alphaAnimation.duration = 1750
        findViewById<AppCompatImageView>(R.id.image_view).startAnimation(alphaAnimation)
    }
}
