package my.uk.agriapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import my.uk.agriapp.ChatActivity
import my.uk.agriapp.R
import my.uk.agriapp.model.ChatModel
import java.util.ArrayList

class ChatAdapter(private val context: Context?, private val chats: ArrayList<ChatModel?>?) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_chats, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val chatModel = chats?.get(position)
        // sets the text to the textview from our itemHolder class
        if (chatModel != null) {
            holder.groupName.text = chatModel.groupName
        }

        holder.itemView.setOnClickListener {
            val chatIntent = Intent(context, ChatActivity::class.java)
            chatIntent.putExtra("groupName", chatModel?.groupName)
            chatIntent.putExtra("groupDesc", chatModel?.groupDesc)
            context?.startActivity(chatIntent)
            Toast.makeText(context, chatModel?.groupDesc, Toast.LENGTH_SHORT).show()
        }

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
            return chats!!.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val groupName: TextView = itemView.findViewById(R.id.groupName)
    }
}

