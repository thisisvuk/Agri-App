package my.uk.agriapp.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import my.uk.agriapp.Constants
import my.uk.agriapp.R
import my.uk.agriapp.adapter.ChatAdapter
import my.uk.agriapp.model.ChatModel
import org.json.JSONArray
import org.json.JSONException
import java.io.UnsupportedEncodingException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {

    private val constants = Constants()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeFragment: View = inflater.inflate(R.layout.fragment_home, container, false)

        val sharedPreferences = activity?.getSharedPreferences("userProfile", AppCompatActivity.MODE_PRIVATE)
        val mobile = sharedPreferences?.getString("user_id", "")

        val requestQueue = Volley.newRequestQueue(context)
        val chatRecyclerView = homeFragment.findViewById<RecyclerView>(R.id.chatRecyclerView)
        val recyclerViewLayoutManager = LinearLayoutManager(context)
        // Set LayoutManager on Recycler View
        chatRecyclerView.layoutManager = recyclerViewLayoutManager
        // Set Horizontal Layout Manager
        // for Recycler view
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        chatRecyclerView.layoutManager = linearLayoutManager

        //getChats(requestQueue, chatRecyclerView, mobile)

        val calender = Calendar.getInstance()
        calender.timeZone = TimeZone.getTimeZone("Asia/Calcutta")
        val s = SimpleDateFormat("ddMMyyhhmmss")
        val format: String = s.format(Date())
        homeFragment.findViewById<TextView>(R.id.sessionId).text = "Session Id: $mobile $format"

        return homeFragment
    }

    private fun getChats(
        requestQueue: RequestQueue,
        chatRecyclerView: RecyclerView,
        mobile: String?
    ) {

        val apiRequest = StringRequest(
            Request.Method.GET,
            constants.getChats + "?mobile=" + mobile,
            { response ->
                try {
                    println(response)
                    val chats: ArrayList<ChatModel?> = ArrayList()
                    //converting the string to json array object
                    val array = JSONArray(response)
                    //traversing through all the object
                    println(array.length())
                    for (i in 0 until array.length()) {
                        //getting product object from json array
                        val result = array.getJSONObject(i)
                        println(result)
                        //adding the product to product list
                        chats.add(
                            ChatModel(
                                result.getString("group_name"),
                                result.getString("group_desc")
                            )
                        )
                    }
                    println(chats)
                    //creating adapter object and setting it to recyclerview
                    //creating adapter object and setting it to recyclerview
                    val adapter = ChatAdapter(context, chats)
                    chatRecyclerView.adapter = adapter

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { error ->
                if (error?.networkResponse != null) {
                    println(error)
                    val body: String
                    try {
                        body = String(error.networkResponse.data, charset("UTF-8"))
                        Toast.makeText(context, body, Toast.LENGTH_SHORT).show()
                    } catch (e: UnsupportedEncodingException) {
                        // exception
                    }
                }
            })

        requestQueue.add(apiRequest)
    }

}