package my.uk.agriapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import my.uk.agriapp.Constants
import my.uk.agriapp.R
import my.uk.agriapp.adapter.CropAdapter
import my.uk.agriapp.model.CropModel
import org.json.JSONArray
import org.json.JSONException
import java.io.UnsupportedEncodingException

class CropFragment : Fragment() {

    private val constants = Constants()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val cropFragment = inflater.inflate(R.layout.fragment_crop, container, false)

        val sharedPreferences = activity?.getSharedPreferences("userProfile", AppCompatActivity.MODE_PRIVATE)
        val mobile = sharedPreferences?.getString("user_id", "")

        val requestQueue = Volley.newRequestQueue(context)
        val myCropsRV = cropFragment.findViewById<RecyclerView>(R.id.myCropsRV)
        // Set Horizontal Layout Manager
        // set a GridLayoutManager with default vertical orientation and 2 number of columns
        val gridLayoutManager = GridLayoutManager(context, 3)
        gridLayoutManager.orientation = LinearLayoutManager.VERTICAL // set Horizontal Orientation

        myCropsRV.layoutManager = gridLayoutManager

        //getCrops(requestQueue, myCropsRV, mobile)

        return cropFragment
    }

    private fun getCrops(
        requestQueue: RequestQueue,
        myCropsRV: RecyclerView,
        mobile: String?
    ) {

        val apiRequest = StringRequest(
            Request.Method.GET,
            constants.getCrops + "?mobile=" + mobile,
            { response ->
                try {
                    println(response)
                    val crops: ArrayList<CropModel?> = ArrayList()
                    //converting the string to json array object
                    val array = JSONArray(response)
                    //traversing through all the object
                    println(array.length())
                    for (i in 0 until array.length()) {
                        //getting product object from json array
                        val result = array.getJSONObject(i)
                        println(result)
                        //adding the product to product list
                        crops.add(
                            CropModel(
                                result.getString("crop_name"),
                                result.getString("crop_img")
                            )
                        )
                    }
                    println(crops)
                    //creating adapter object and setting it to recyclerview
                    //creating adapter object and setting it to recyclerview
                    val adapter = CropAdapter(context, crops)
                    myCropsRV.adapter = adapter

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

