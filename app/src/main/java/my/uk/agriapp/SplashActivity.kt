package my.uk.agriapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val a = AnimationUtils.loadAnimation(this, R.anim.fadeout)
        a.reset()
        val tv = findViewById<View>(R.id.app_name) as TextView
        tv.clearAnimation()
        tv.startAnimation(a)

        val appInfoSharedPreferences = getSharedPreferences("appInfo", MODE_PRIVATE)
        val isFirstTime = appInfoSharedPreferences.getInt("isFirstTime",0)

        Handler().postDelayed({
            if (isFirstTime == 0){
                val mainIntent = Intent(this, LangActivity::class.java)
                startActivity(mainIntent)
                finish()
            } else {
                val mainIntent = Intent(this, LoginActivity::class.java)
                startActivity(mainIntent)
                finish()
            }
        }, 3000)
    }
}