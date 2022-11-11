package my.uk.agriapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import my.uk.agriapp.ChatActivity
import my.uk.agriapp.R
import my.uk.agriapp.model.CropModel
import java.util.ArrayList

class CropAdapter(private val context: Context?, private val crops: ArrayList<CropModel?>?) : RecyclerView.Adapter<CropAdapter.ViewHolder>() {

    private var screenWidth : Int? = null
    private var width : Int? = null

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_crops, parent, false)

        screenWidth = parent.width
        width = (parent.width / 3)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.myCropsLL.layoutParams.width = width!!
        holder.myCropsCV.layoutParams.height = width!! - 20

        val cropModel = crops?.get(position)
        // sets the text to the textview from our itemHolder class
        if (cropModel != null) {
            holder.cropName.text = cropModel.cropName
            Glide.with(context!!).load(cropModel.cropImg).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(holder.cropImg)
        }

        holder.itemView.setOnClickListener {
//            val chatIntent = Intent(context, ChatActivity::class.java)
//            chatIntent.putExtra("groupName", cropModel?.cropName)
//            chatIntent.putExtra("groupDesc", cropModel?.cropImg)
//            context?.startActivity(chatIntent)
            Toast.makeText(context, cropModel?.cropName, Toast.LENGTH_SHORT).show()
        }

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
            return crops!!.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val cropName: TextView = itemView.findViewById(R.id.cropName)
        val cropImg: ImageView = itemView.findViewById(R.id.cropImg)
        val myCropsCV: CardView = itemView.findViewById(R.id.myCropsCV)
        val myCropsLL: LinearLayout = itemView.findViewById(R.id.myCropsLL)
    }
}

