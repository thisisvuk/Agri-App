package my.uk.agriapp.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import my.uk.agriapp.R
import android.widget.RelativeLayout
import androidx.core.view.isVisible
import my.uk.agriapp.ChangeActivity
import my.uk.agriapp.LoginActivity


class ProfileFragment : Fragment() {

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val profileFragment = inflater.inflate(R.layout.fragment_profile, container, false)

        val profileProgressBar = profileFragment.findViewById<ProgressBar>(R.id.profileProgressBar)

        //setProfile(profileFragment, profileProgressBar);

        val btnChangeName = profileFragment.findViewById<RelativeLayout>(R.id.changeName)
        val btnChangeAddress = profileFragment.findViewById<RelativeLayout>(R.id.changeAddress)
        val btnShareApp = profileFragment.findViewById<RelativeLayout>(R.id.shareApp)
        val btnNeedHelp = profileFragment.findViewById<RelativeLayout>(R.id.needHelp)
        val btnLogout = profileFragment.findViewById<Button>(R.id.btnLogout)

        btnChangeName.setOnClickListener { changeName() }
        btnChangeAddress.setOnClickListener { changeAddress() }
        btnShareApp.setOnClickListener { shareApp() }
        btnNeedHelp.setOnClickListener { needHelp() }
        btnLogout.setOnClickListener { logoutUser() }

        return profileFragment
    }

    private fun changeName() {
        activity?.startActivity(Intent(activity, ChangeActivity::class.java))
    }

    private fun changeAddress() {
        activity?.startActivity(Intent(activity, ChangeActivity::class.java))
    }

    private fun shareApp() {
    }

    private fun needHelp() {
    }


    private fun logoutUser() {
        val loginSharedPreferences = activity?.getSharedPreferences("userProfile", AppCompatActivity.MODE_PRIVATE)
        val myEdit = loginSharedPreferences?.edit()
        myEdit?.clear()
        myEdit?.apply()
        val loginIntent = Intent(activity, LoginActivity::class.java)
        activity?.startActivity(loginIntent)
        activity?.finish()
    }

    @SuppressLint("SetTextI18n")
    private fun setProfile(profileFragment: View, profileProgressBar: ProgressBar) {
        val userName = profileFragment.findViewById<TextView>(R.id.user_name)
        val userAddress = profileFragment.findViewById<TextView>(R.id.user_address)
        val userMobile = profileFragment.findViewById<TextView>(R.id.user_mobile)

        val sharedPreferences = activity?.getSharedPreferences("userProfile", AppCompatActivity.MODE_PRIVATE)

        val dId = sharedPreferences?.getString("user_id", "")
        val dName = sharedPreferences?.getString("d_name", "")
        val dAddress = sharedPreferences?.getString("d_address", "")
        val dVillage = sharedPreferences?.getString("d_village", "")
        val dTaluka = sharedPreferences?.getString("d_taluka", "")
        val dDistrict = sharedPreferences?.getString("d_district", "")
        val dState = sharedPreferences?.getString("d_state", "")

        userName.text = "$dName"
        userAddress.text = "$dAddress, $dVillage,\n $dTaluka, $dDistrict,\n $dState"
        userMobile.text = "+91 $dId"
        profileProgressBar.isVisible = false
    }

}