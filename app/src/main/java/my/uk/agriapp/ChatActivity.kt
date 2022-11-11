package my.uk.agriapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ChatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val groupName = intent.getStringExtra("groupName")
        val groupDesc = intent.getStringExtra("groupDesc")

        if (groupName != null) {
            findViewById<TextView>(R.id.chatTitle).text = groupName
        }
    }
}