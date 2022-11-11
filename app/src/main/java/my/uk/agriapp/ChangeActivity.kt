package my.uk.agriapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.json.JSONException
import org.json.JSONObject


class ChangeActivity : AppCompatActivity() {

    var myVar: String? = null
    private val constants = Constants()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change)

        val saveProfileBtn = findViewById<Button>(R.id.saveProfileBtn)

        val nameTIL = findViewById<TextInputLayout>(R.id.nameTIL)
        val nameACT = nameTIL.editText as? TextInputEditText
        val addressTIL = findViewById<TextInputLayout>(R.id.addressTIL)
        val addressACT = addressTIL.editText as? TextInputEditText
        val villageTIL = findViewById<TextInputLayout>(R.id.villageTIL)
        val villageACT = villageTIL.editText as? TextInputEditText
        val talukaTIL = findViewById<TextInputLayout>(R.id.talukaTIL)
        val talukaACT = talukaTIL.editText as? TextInputEditText
        val districtTIL = findViewById<TextInputLayout>(R.id.districtTIL)
        val districtACT = districtTIL.editText as? AutoCompleteTextView

        //setDetails(nameACT, addressACT, villageACT, talukaACT, districtACT)

        showSoftKeyboard(nameTIL)

        val districts = resources.getStringArray(R.array.districtArray).toList()
        val districtAdapter =
            ArrayAdapter(applicationContext, R.layout.layout_dropdown_item, districts)
        districtACT?.setAdapter(districtAdapter)

        districtACT?.setOnItemClickListener { _, _, position, _ ->
            myVar = districtAdapter.getItem(position).toString()
        }
        districtACT?.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                myVar = null; }

            override fun afterTextChanged(s: Editable) {}
        })

        saveProfileBtn.setOnClickListener {
            if (myVar != null) {
                val nameText = nameACT?.text.toString()
                val addressText = addressACT?.text.toString()
                val villageText = villageACT?.text.toString()
                val talukaText = talukaACT?.text.toString()
                val distStateArray = districtACT?.text?.split(", ")
//                saveAddressChange(
//                    nameText,
//                    addressText,
//                    villageText,
//                    talukaText,
//                    distStateArray?.get(0),
//                    distStateArray?.get(1)
//                )
            } else {
                Toast.makeText(applicationContext, "Select district from list", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun setDetails(
        nameACT: TextInputEditText?,
        addressACT: TextInputEditText?,
        villageACT: TextInputEditText?,
        talukaACT: TextInputEditText?,
        districtACT: AutoCompleteTextView?
    ) {
        val sharedPreferences = getSharedPreferences("userProfile", MODE_PRIVATE)

        val dId = sharedPreferences?.getString("d_id", "")
        val dName = sharedPreferences?.getString("d_name", "")
        val dAddress = sharedPreferences?.getString("d_address", "")
        val dVillage = sharedPreferences?.getString("d_village", "")
        val dTaluka = sharedPreferences?.getString("d_taluka", "")
        val dDistrict = sharedPreferences?.getString("d_district", "")
        val dState = sharedPreferences?.getString("d_state", "")

        nameACT?.setText(dName)
        addressACT?.setText(dAddress)
        villageACT?.setText(dVillage)
        talukaACT?.setText(dTaluka)
        districtACT?.setText("$dDistrict, $dState")
        myVar = ""
    }

    private fun saveAddressChange(
        nameText: String,
        addressText: String,
        villageText: String,
        talukaText: String,
        districtText: String?,
        stateText: String?
    ) {

        val sharedPreferences = getSharedPreferences("userProfile", MODE_PRIVATE)
        val userId = sharedPreferences?.getString("user_id", "")

        val postData = JSONObject()
        try {
            postData.put("id", userId)
            postData.put("name", nameText)
            postData.put("address", addressText)
            postData.put("village", villageText)
            postData.put("taluka", talukaText)
            postData.put("district", districtText)
            postData.put("state", stateText)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        Toast.makeText(this, "$postData", Toast.LENGTH_SHORT).show()

        val requestQueue = Volley.newRequestQueue(this)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, constants.updateProfile, postData,
            { response ->
                println(response)
                val sharedPreferences = getSharedPreferences("userProfile", MODE_PRIVATE)

                val myEdit = sharedPreferences.edit()

                myEdit.putString("user_id", userId)
                myEdit.putString("d_name", nameText)
                myEdit.putString("d_address", addressText)
                myEdit.putString("d_village", villageText)
                myEdit.putString("d_taluka", talukaText)
                myEdit.putString("d_district", districtText)
                myEdit.putString("d_state", stateText)
                myEdit.apply()

                Toast.makeText(this, "Save Successful", Toast.LENGTH_SHORT).show()
                val profileIntent = Intent(applicationContext, HomeActivity::class.java)
                profileIntent.putExtra("destination", "profile")
                startActivity(profileIntent)
            }
        ) { error ->
            error.printStackTrace()
            Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show()
        }

        requestQueue.add(jsonObjectRequest)
    }

    private fun showSoftKeyboard(view: View) {
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