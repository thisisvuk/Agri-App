package my.uk.agriapp

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import my.uk.agriapp.fragment.*
import org.json.JSONException
import org.json.JSONObject
import org.json.JSONTokener
import java.io.UnsupportedEncodingException

class HomeActivity : AppCompatActivity() {
    private val homeFragment = HomeFragment()
    private val cropFragment: Fragment = CropFragment()
    private val farmerFragment: Fragment = FarmerFragment()
    private val profileFragment: Fragment = ProfileFragment()

    private val fm = supportFragmentManager
    private val constants = Constants()
    var mobile : String? = null
    private var requestQueue : RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        println("**************************AT HOME********************")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        requestQueue = Volley.newRequestQueue(this)
        val sharedPreferences = getSharedPreferences("userProfile", MODE_PRIVATE)
        mobile = sharedPreferences?.getString("user_id", "")
        Toast.makeText(this, mobile, Toast.LENGTH_LONG).show()

        val homeBtn = findViewById<LinearLayout>(R.id.homebtn)
        val cropBtn = findViewById<LinearLayout>(R.id.cropbtn)
        val farmerBtn = findViewById<LinearLayout>(R.id.farmerbtn)
        val profileBtn = findViewById<LinearLayout>(R.id.profilebtn)

        navigation("home")

        homeBtn.setOnClickListener { navigation("home") }
        cropBtn.setOnClickListener { navigation("crop") }
        farmerBtn.setOnClickListener { navigation("farmer") }
        profileBtn.setOnClickListener { navigation("profile") }

        //getProfile(requestQueue!!, mobile)
    }

    private fun getProfile(requestQueue: RequestQueue, mobile: String?) {

        println("**********************Get Profile Request Send**********************")
        val apiRequest = StringRequest(
            Request.Method.GET,
            constants.getProfile + "?mobile=" + mobile,
            { response ->
                println(response)
                try {
                    val jsonObject = JSONTokener(response).nextValue() as JSONObject

                    val userId = jsonObject.getString("user_id")
                    val dName = jsonObject.getString("f_name")
                    val dAddress = jsonObject.getString("f_address")
                    val dVillage = jsonObject.getString("f_village")
                    val dTaluka = jsonObject.getString("f_taluka")
                    val dDistrict = jsonObject.getString("f_district")
                    val dState = jsonObject.getString("f_state")

                    val sharedPreferences = getSharedPreferences("userProfile", MODE_PRIVATE)

                    val myEdit = sharedPreferences.edit()

                    myEdit.putString("user_id", userId)
                    myEdit.putString("d_name", dName)
                    myEdit.putString("d_address", dAddress)
                    myEdit.putString("d_village", dVillage)
                    myEdit.putString("d_taluka", dTaluka)
                    myEdit.putString("d_district", dDistrict)
                    myEdit.putString("d_state", dState)

                    myEdit.apply()

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { error ->
                if (error?.networkResponse != null) {
                    println("<><><><><><>$error")
                    val body: String
                    //get status code here
                    val statusCode = java.lang.String.valueOf(error.networkResponse.statusCode)
                    //get response body and parse with appropriate encoding
                    try {
                        body = String(error.networkResponse.data, charset("UTF-8"))
                        Toast.makeText(this, body, Toast.LENGTH_SHORT).show()
                    } catch (e: UnsupportedEncodingException) {
                        // exception
                    }
                }
            })

        requestQueue.add(apiRequest)

    }

    private fun navigation(s: String = "home") {

        val homeLL = findViewById<LinearLayout>(R.id.homell)
        val homeImg = findViewById<ImageView>(R.id.homeimg)
        val homeTxt = findViewById<TextView>(R.id.hometxt)
        val cropLL = findViewById<LinearLayout>(R.id.cropll)
        val cropImg = findViewById<ImageView>(R.id.cropimg)
        val cropTxt = findViewById<TextView>(R.id.croptxt)
        val farmerLL = findViewById<LinearLayout>(R.id.storell)
        val farmerImg = findViewById<ImageView>(R.id.storeimg)
        val farmerTxt = findViewById<TextView>(R.id.storetxt)
        val profileLL = findViewById<LinearLayout>(R.id.profilell)
        val profileImg = findViewById<ImageView>(R.id.profileimg)
        val profileTxt = findViewById<TextView>(R.id.profiletxt)

        when (s) {
            "home" -> {
                fm.beginTransaction().setCustomAnimations(R.anim.slidein, R.anim.fadeout)
                    .replace(R.id.container, homeFragment).addToBackStack(null).commit()
                homeLL.setBackgroundColor(getColor(R.color.green))
                homeImg.setColorFilter(getColor(R.color.green))
                homeTxt.setTextColor(getColor(R.color.green))
                cropLL.setBackgroundColor(getColor(R.color.white))
                cropImg.setColorFilter(getColor(R.color.text_color))
                cropTxt.setTextColor(getColor(R.color.text_color))
                farmerLL.setBackgroundColor(getColor(R.color.white))
                farmerImg.setColorFilter(getColor(R.color.text_color))
                farmerTxt.setTextColor(getColor(R.color.text_color))
                profileLL.setBackgroundColor(getColor(R.color.white))
                profileImg.setColorFilter(getColor(R.color.text_color))
                profileTxt.setTextColor(getColor(R.color.text_color))
            }
            "crop" -> {
                fm.beginTransaction().setCustomAnimations(R.anim.slidein, R.anim.fadeout)
                    .replace(R.id.container, cropFragment).addToBackStack(null).commit()
                homeLL.setBackgroundColor(getColor(R.color.white))
                homeImg.setColorFilter(getColor(R.color.text_color))
                homeTxt.setTextColor(getColor(R.color.text_color))
                cropLL.setBackgroundColor(getColor(R.color.green))
                cropImg.setColorFilter(getColor(R.color.green))
                cropTxt.setTextColor(getColor(R.color.green))
                farmerLL.setBackgroundColor(getColor(R.color.white))
                farmerImg.setColorFilter(getColor(R.color.text_color))
                farmerTxt.setTextColor(getColor(R.color.text_color))
                profileLL.setBackgroundColor(getColor(R.color.white))
                profileImg.setColorFilter(getColor(R.color.text_color))
                profileTxt.setTextColor(getColor(R.color.text_color))
            }
            "farmer" -> {
                fm.beginTransaction().setCustomAnimations(R.anim.slidein, R.anim.fadeout)
                    .replace(R.id.container, farmerFragment).addToBackStack(null).commit()
                homeLL.setBackgroundColor(getColor(R.color.white))
                homeImg.setColorFilter(getColor(R.color.text_color))
                homeTxt.setTextColor(getColor(R.color.text_color))
                cropLL.setBackgroundColor(getColor(R.color.white))
                cropImg.setColorFilter(getColor(R.color.text_color))
                cropTxt.setTextColor(getColor(R.color.text_color))
                farmerLL.setBackgroundColor(getColor(R.color.green))
                farmerImg.setColorFilter(getColor(R.color.green))
                farmerTxt.setTextColor(getColor(R.color.green))
                profileLL.setBackgroundColor(getColor(R.color.white))
                profileImg.setColorFilter(getColor(R.color.text_color))
                profileTxt.setTextColor(getColor(R.color.text_color))
            }
            "profile" -> {
                fm.beginTransaction().setCustomAnimations(R.anim.slidein, R.anim.fadeout)
                    .replace(R.id.container, profileFragment).addToBackStack(null).commit()
                homeLL.setBackgroundColor(getColor(R.color.white))
                homeImg.setColorFilter(getColor(R.color.text_color))
                homeTxt.setTextColor(getColor(R.color.text_color))
                cropLL.setBackgroundColor(getColor(R.color.white))
                cropImg.setColorFilter(getColor(R.color.text_color))
                cropTxt.setTextColor(getColor(R.color.text_color))
                farmerLL.setBackgroundColor(getColor(R.color.white))
                farmerImg.setColorFilter(getColor(R.color.text_color))
                farmerTxt.setTextColor(getColor(R.color.text_color))
                profileLL.setBackgroundColor(getColor(R.color.green))
                profileImg.setColorFilter(getColor(R.color.green))
                profileTxt.setTextColor(getColor(R.color.green))
            }
        }
    }

    override fun onBackPressed() {
       navigation("home")
    }

    override fun onPostResume() {
        if (intent.getStringExtra("destination") != null) {
            when (intent.getStringExtra("destination")) {
                "profile" -> {
                    navigation("profile")
                }
                else -> {
                   navigation("home")
                }
            }
        }
        super.onPostResume()
    }
}
