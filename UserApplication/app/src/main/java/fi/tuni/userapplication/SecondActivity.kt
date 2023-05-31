package fi.tuni.userapplication


import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * @author by Matias Yliluoma
 *
 * SecondActivity and second view of the application,
 * displays the fetched users by selected way
 */

class SecondActivity : AppCompatActivity() {

    private var userRecyclerView: RecyclerView? = null
    private var customAdapter: CustomAdapter? = null

    // resutlauncher for receiving data
    // from another activity (UserInfoActivity)
    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val userId = data!!.getIntExtra("id", 0)
            val userFirst = data.getStringExtra("firstname")
            val userSur = data.getStringExtra("lastname")
            val userAge = data.getIntExtra("age", 0)
            val userEmail = data.getStringExtra("email")
            val userModel = UserModel(
                userId,
                userFirst.toString(),
                userSur.toString(),
                userAge,
                userEmail.toString()
            )

            // here we receive the data and we are checking if
            // we are either updating wanted user or
            // adding a new one
            val isEditMode = data.getBooleanExtra("isEditMode", false)
            if(isEditMode) {
                customAdapter!!.editUser(userModel)
            } else {
                customAdapter!!.addUser(userModel)
            }
        }
    }
    // anonymous func which calls CustomAdapter's UserEditClickListener interface
    private val userEditClickListener = object : CustomAdapter.UserEditClickListener {
        /**
         * @param userModel is our parameter for now for our
         * UserModel. Here its used on selfmade interface
         * for updating the wanted user's infos
         */
        override fun onClicked(userModel: UserModel) {
            // all this gets to sent to UserInfoActivity
            val userIntent = Intent(this@SecondActivity,
                UserInfoActivity::class.java)
            // Forced on XML file to be numeric
            userIntent.putExtra("id", userModel.id.toString())
            userIntent.putExtra("firstname", userModel.firstName)
            userIntent.putExtra("lastname", userModel.lastName)
            userIntent.putExtra("email", userModel.email)
            // Forced on XML file to be numeric
            userIntent.putExtra("age", userModel.age.toString())
            // The editmode gets sent here as true since it's
            // being done by edit button
            userIntent.putExtra("isEditMode", true)
            resultLauncher.launch(userIntent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        // For the dynamic layout view
        userRecyclerView = findViewById(R.id.userRecyclerView)
        userRecyclerView?.layoutManager = LinearLayoutManager(this)


        // receives the data from Main Activity
        val jsonResponse = intent?.getStringExtra("userList")
        // Button for adding new users
        val addUserButton : Button = findViewById(R.id.addUser)

        // sends data to UserInfoActivity when
        // we are adding new user
        addUserButton.setOnClickListener {
            val intent = Intent(this, UserInfoActivity::class.java)
            // makes sure the isEditMode is false when
            // addUserButton is pressed
            intent.putExtra("isEditMode", false)
            resultLauncher.launch(intent)
        }


        // makes sure we are not dealing
        // empty json
        if (jsonResponse != null) {
            val userList = UserList.fromJson(jsonResponse)
            /**
             * In CustomAdapter.kt the CustomAdapter receives
             * userList and this is where we convert UserModelClass
             * to MutableList so we can actually update it,
             * add new users and delete from it.
             * Also custom interface userEditClickListener
             * gets sent so, as will context
             * so post, put and del requests
             * alongside Toasts will work as
             * error handling
             */

            customAdapter = CustomAdapter(userList.users!!.toMutableList(),
                userEditClickListener,
                this@SecondActivity)
            // to use recyclerview in our customadapter
            userRecyclerView?.adapter = customAdapter
        } else {
            /**
             * if json was empty which it most likely wont be
             * as we already handle empty strings and
             * connection issues on Main activity and HttpResponse class
             */
            val toast = Toast.makeText(this,
                "JSON response was empty",
                Toast.LENGTH_SHORT)
            toast.show()
        }

    }
}