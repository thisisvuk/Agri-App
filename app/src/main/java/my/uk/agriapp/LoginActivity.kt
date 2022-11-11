package my.uk.agriapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.chaos.view.PinView
import org.json.JSONObject
import org.json.JSONTokener
import java.io.UnsupportedEncodingException
import java.lang.Integer.parseInt


class LoginActivity : AppCompatActivity() {

    private var doubleBackToExitPressedOnce = false
    private val constant = Constants();
    private var mobile = "";
    private var otp = "";
    private val constants = Constants()

    override fun onCreate(savedInstanceState: Bundle?) {
        println("**************************AT LOGIN********************")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnContinue = findViewById<Button>(R.id.nextbtn)
        val btnBack = findViewById<ImageView>(R.id.login_backbtn)

        val etMobile = findViewById<EditText>(R.id.editTextPhone)
        val etOTP = findViewById<PinView>(R.id.otp)

        val scrollViewMobile = findViewById<ScrollView>(R.id.mobile_number_scrollView)
        val scrollViewOTP = findViewById<ScrollView>(R.id.verify_number_scrollView)

        showSoftKeyboard(etMobile)

        btnContinue.setOnClickListener {
            if (scrollViewMobile.isVisible) {
                scrollViewMobile.visibility = View.GONE
                scrollViewOTP.visibility = View.VISIBLE
                mobile = etMobile.text.toString()
                otp = constants.randomNumeric()
                Toast.makeText(this, otp, Toast.LENGTH_SHORT).show()
                checkUser(mobile)
                showSoftKeyboard(etOTP)
            } else if (scrollViewOTP.isVisible) {
                if (etOTP.text.toString() != otp) {
                    hideKeyboard()
                    val sharedPreferences = getSharedPreferences("userProfile", MODE_PRIVATE)
                    val myEdit = sharedPreferences.edit()
                    myEdit.putString("user_id", mobile)
                    myEdit.apply()
                    startActivity(Intent(this, HomeActivity::class.java))
                } else {
                    Toast.makeText(this, otp, Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnBack.setOnClickListener {
            if (scrollViewOTP.isVisible) {
                scrollViewOTP.visibility = View.GONE
                scrollViewMobile.visibility = View.VISIBLE
                showSoftKeyboard(etMobile)
            } else if (scrollViewMobile.isVisible) {
                onBackPressed()
            }
        }
    }

    private fun checkUser(mobile: String) {
//        val postData = JSONObject()
//        try {
//            postData.put("mobile", mobile)
//        } catch (e: JSONException) {
//            e.printStackTrace()
//        }

        val requestQueue = Volley.newRequestQueue(this)

        val apiRequest = StringRequest(
            Request.Method.GET, constants.checkUser + "?mobile=" + mobile,
            { response ->
                val jsonObject = JSONTokener(response).nextValue() as JSONObject

                val userExists = jsonObject.getString("total")
                val userType = jsonObject.getString("user_type")

                if (parseInt(userExists) > 0) {
                    val sharedPreferences = getSharedPreferences("userProfile", MODE_PRIVATE)
                    val myEdit = sharedPreferences.edit()
                    myEdit.putString("user_type", userType)
                    myEdit.apply()
                    Toast.makeText(this, "$userType $userExists", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "$userType $userExists", Toast.LENGTH_SHORT).show()
                }
            }
        )
        { error ->
            if (error?.networkResponse != null) {
                val body: String
                //get status code here
                //get status code here
                val statusCode = java.lang.String.valueOf(error.networkResponse.statusCode)
                //get response body and parse with appropriate encoding
                //get response body and parse with appropriate encoding
                try {
                    body = String(error.networkResponse.data, charset("UTF-8"))
                    Toast.makeText(this, body, Toast.LENGTH_SHORT).show()
                    println(body)
                } catch (e: UnsupportedEncodingException) {
                    // exception
                }
                println(statusCode)
            }
        }
        requestQueue.add(apiRequest)
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            doubleBackToExitPressedOnce = false
        }, 2000)
    }

    fun showSoftKeyboard(view: View) {
        if (view.requestFocus()) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    fun hideKeyboard() {
        val view = findViewById<View>(android.R.id.content)
        if (view != null) {
            val imm = this.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}

