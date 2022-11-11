package my.uk.agriapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat

class LangActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lang)

        val appInfoSharedPreferences = getSharedPreferences("appInfo", MODE_PRIVATE)
        var isSelected = 0

        findViewById<Button>(R.id.langContinue).setOnClickListener {
            if (isSelected == 1){
                val myEdit: SharedPreferences.Editor = appInfoSharedPreferences.edit()
                myEdit.putInt("isFirstTime", 1)
                myEdit.apply()
                val mainIntent = Intent(this, LoginActivity::class.java)
                startActivity(mainIntent)
            finish()
            } else {
                Toast.makeText(this, "Select the language", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<View>(R.id.en_card).setOnClickListener {
            isSelected = 1
            changeLangCardColor("en")
            val myEdit: SharedPreferences.Editor = appInfoSharedPreferences.edit()
            myEdit.putString("lang", "en")
            myEdit.apply()
        }

        findViewById<View>(R.id.mr_card).setOnClickListener {
            isSelected = 1
            changeLangCardColor("mr")
            val myEdit: SharedPreferences.Editor = appInfoSharedPreferences.edit()
            myEdit.putString("lang", "mr")
            myEdit.apply()
        }

        findViewById<View>(R.id.hi_card).setOnClickListener {
            isSelected = 1
            changeLangCardColor("hi")
            val myEdit: SharedPreferences.Editor = appInfoSharedPreferences.edit()
            myEdit.putString("lang", "hi")
            myEdit.apply()
        }
    }

    private fun changeLangCardColor(lang: String) {
        val en_icon = findViewById<TextView>(R.id.en_icon)
        val en_text = findViewById<TextView>(R.id.en_text)
        val mr_icon = findViewById<TextView>(R.id.mr_icon)
        val mr_text = findViewById<TextView>(R.id.mr_text)
        val hi_icon = findViewById<TextView>(R.id.hi_icon)
        val hi_text = findViewById<TextView>(R.id.hi_text)
        val en_card: CardView = findViewById(R.id.en_card)
        val mr_card: CardView = findViewById(R.id.mr_card)
        val hi_card: CardView = findViewById(R.id.hi_card)
        if (lang == "mr") {
            en_icon.setTextColor(getColor(R.color.text_color))
            en_text.setTextColor(getColor(R.color.text_color))
            mr_icon.setTextColor(getColor(R.color.green))
            mr_text.setTextColor(getColor(R.color.green))
            hi_icon.setTextColor(getColor(R.color.text_color))
            hi_text.setTextColor(getColor(R.color.text_color))
            en_card.background = ContextCompat.getDrawable(this, R.drawable.btn_transperant)
            mr_card.background = ContextCompat.getDrawable(this, R.drawable.btn_outbg)
            hi_card.background = ContextCompat.getDrawable(this, R.drawable.btn_transperant)
        } else if (lang == "hi") {
            en_icon.setTextColor(getColor(R.color.text_color))
            en_text.setTextColor(getColor(R.color.text_color))
            mr_icon.setTextColor(getColor(R.color.text_color))
            mr_text.setTextColor(getColor(R.color.text_color))
            hi_icon.setTextColor(getColor(R.color.green))
            hi_text.setTextColor(getColor(R.color.green))
            en_card.background = ContextCompat.getDrawable(this, R.drawable.btn_transperant)
            mr_card.background = ContextCompat.getDrawable(this, R.drawable.btn_transperant)
            hi_card.background = ContextCompat.getDrawable(this, R.drawable.btn_outbg)
        } else {
            en_icon.setTextColor(getColor(R.color.green))
            en_text.setTextColor(getColor(R.color.green))
            mr_icon.setTextColor(getColor(R.color.text_color))
            mr_text.setTextColor(getColor(R.color.text_color))
            hi_icon.setTextColor(getColor(R.color.text_color))
            hi_text.setTextColor(getColor(R.color.text_color))
            en_card.background = ContextCompat.getDrawable(this, R.drawable.btn_outbg)
            mr_card.background = ContextCompat.getDrawable(this, R.drawable.btn_transperant)
            hi_card.background = ContextCompat.getDrawable(this, R.drawable.btn_transperant)
        }
    }

}