package fi.tuni.userapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class SecondActivity : AppCompatActivity() {
    var listOfUsersView: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        var listOfUsersView : TextView = findViewById(R.id.showUsersView)


        val jsonResponse = intent?.getStringExtra("jsonResponse")
        if (jsonResponse != null) {
            val userList = UserList(jsonResponse)
            showList(userList.users, listOfUsersView)
            Log.d("TAG", "moro")
            // Use the userList or perform further JSON parsing here
        } /*else {
        implement check for errors as empty json
            error()
        }*/
    }

    fun showList(users : List<User>, listOfUsersView: TextView) {

        val listGotStringed = users.joinToString("\n") { user ->
            "ID: ${user.id} Firstname: ${user.firstName}"
        }
        listOfUsersView?.text = listGotStringed

    }
}