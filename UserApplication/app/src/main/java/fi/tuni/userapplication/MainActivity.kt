package fi.tuni.userapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import org.json.JSONException
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

class MainActivity : AppCompatActivity() {
    var searchUser : EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val fetchButton: Button = findViewById(R.id.fetchAll)
        val fetchOneButton: Button = findViewById(R.id.fetchOne)
        this.searchUser = findViewById(R.id.search_by_text)


        // button to test out fetching all the users of dummy json
        fetchButton.setOnClickListener() {

            try {

                //instance of  HttpResponse
                val httpResponse = HttpResponse(this)
                // Calls fetchAll func from HttpResponse
                // and sends userList
                httpResponse.fetchAll { userList ->
                    Log.d("TAG", "ENTÄS TÄMÄ $userList")

                    // The destination where the information gets
                    // sent
                    val intent = Intent(this, SecondActivity::class.java)
                    val userListJson = Gson().toJson(userList)
                    // what gets sent
                    intent.putExtra("userList", userListJson)
                    // starts the wanted activity
                    startActivity(intent)

                }


            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
// sort of useless if check currently,
// was meant to be for TextWatcher.
        fetchOneButton.setOnClickListener() {
            if (!searchUser?.text.isNullOrEmpty()) {
                val userToString = searchUser?.text.toString()
                val oneUserResponse = HttpResponse(this)
                // Calls fetchByText from
                // HttpResponse and sends the userList
                oneUserResponse.fetchByText(userToString) { userList  ->
                    // Filters the list so can be checked if
                    // the wanted string equals the one in
                    // backend. Saves it to filteredUsers
                    // Needs proper error handling if not
                    // users found with the speficig string
                    val filteredUsers = userList.users?.filter { user ->
                        user.firstName.equals(userToString, true)
                    }

                    val filteredUserList = UserList(filteredUsers)
                    // Makes the list to JSON using
                    // Gsons toJson func
                    val userListJson = Gson().toJson(filteredUserList)
                    // Sends the information
                    // to SecondActivity where the list
                    // is displayed
                    val intent = Intent(this, SecondActivity::class.java)
                    intent.putExtra("userList", userListJson)
                    startActivity(intent)
                }
            } else {

            }
        }

    }
}


