package fi.tuni.userapplication


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
class SecondActivity : AppCompatActivity() {
   // private var listOfUsersView: TextView? = null
    private var userRecyclerView: RecyclerView? = null
    private var customAdapter: CustomAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

     //   listOfUsersView = findViewById(R.id.showUsersView)
        userRecyclerView = findViewById(R.id.userRecyclerView)
        userRecyclerView?.layoutManager = LinearLayoutManager(this)

        val jsonResponse = intent?.getStringExtra("userList")

        if (jsonResponse != null) {
            Log.d("TAG", "JSONI $jsonResponse")
            val userList = UserList.fromJson(jsonResponse)
            customAdapter = CustomAdapter(userList.users!!)
            userRecyclerView?.adapter = customAdapter

            Log.d("TAG", "JSONI $jsonResponse")
            // Use the userList or perform further JSON parsing here
        } else {
            //
            Log.d("TAG", "JSON is null")
        }
    }
}