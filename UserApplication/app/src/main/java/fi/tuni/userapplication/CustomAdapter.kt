package fi.tuni.userapplication

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.VolleyLog
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

/**
 * @author by Matias Yliluoma
 *
 * CustomAdapter class gets extended by RecyclerView
 * This will dynamically create the amount of users displayed
 * On XML it's been made as Card view
 * Gets
 */

class CustomAdapter(private var userList: MutableList<UserModel>,
                    private val userEditClickListener: UserEditClickListener,
                    private val context: Context) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // android imagebuttons for the user cards
        val userDel : ImageButton = itemView.findViewById(R.id.delete_button)
        val userEdit : ImageButton = itemView.findViewById(R.id.edit_button)
        // elements for the user cards
        val usersID : TextView = itemView.findViewById(R.id.userID)
        val userFirstName: TextView = itemView.findViewById(R.id.userFirstNameView)
        val userLastName: TextView = itemView.findViewById(R.id.userLastNameView)
        val usersAge : TextView = itemView.findViewById(R.id.userAge)
        val usersEmail : TextView = itemView.findViewById(R.id.userEmail)
    }

    // Create new views (invoked by the layout manager)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user_layout, parent, false)

        return ViewHolder(itemView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val user = userList[position]
        viewHolder.usersID.text = user.id.toString()
        viewHolder.userFirstName.text = user.firstName
        viewHolder.userLastName.text = user.lastName
        viewHolder.usersAge.text = user.age.toString()
        viewHolder.usersEmail.text = user.email

        // click listener for delete button
        viewHolder.userDel.setOnClickListener {
            // removeAt takes parameter position
            userList.removeAt(position)
            // required notify for recyclerview adapter
            // takes layoutPosition as parameter for
            // deleting correct item and updating
            // view correctly
            notifyItemRemoved(viewHolder.layoutPosition)
            val toast = Toast.makeText(context, "User" +
                    " deleted", Toast.LENGTH_SHORT)
            toast.show()
            // calls the delete request func
            deleteUserFromServer(user)
        }

        // edit button's clicklistener
        viewHolder.userEdit.setOnClickListener {
            // custom interface, calls onClicked with "user"
            // as parameter (user is now the list on this
            // onBindViewHolder)
            userEditClickListener.onClicked(user)
        }
    }

    fun addUser(userModel : UserModel) {
        /**
         * addUser function for adding users to
         * mutable UserModelClass
         */
        val newUserPosition = userList.size
        /**
         * userList gets add() and as parameters
         * newUserPosition which is added to very
         * bottom of the list, requires also userModel
         * as parameter
         */
        userList.add(newUserPosition, userModel)
        // again required to notify when items get inserted for RecyclerView adapter
        notifyItemInserted(newUserPosition)
        val toast = Toast.makeText(context, "User added" +
                " to bottom of the list", Toast.LENGTH_SHORT)
        toast.show()
        // calls the addUserToServer with userModel as parameter
        addUserToServer(userModel)
    }

    fun editUser(userModel : UserModel) {
        /**
         * Here we update the user by
         * its index found by user.value.id
         * works both on added users and
         * existing users fetched by json
         */
        val findUserIndex = userList.withIndex()
            .first {user -> user.value.id == userModel.id}
            .index
        /**
         * the updated user gets
         * replaced on same position
         */
        userList[findUserIndex] = userModel
        // required notify for RecyclerView adapter
        notifyItemChanged(findUserIndex)
        val toast = Toast.makeText(context, "User" +
                " edited", Toast.LENGTH_SHORT)
        toast.show()
        // calls the put request
        // which only simulates and no idea
        // would it work ot not
        updateUserToServer(userModel)
    }

    // Self made interface
    // for being able to use
    // edit button on Card view
    interface UserEditClickListener {
        /**
         * @param userModel gets updated
         * onclick
         */
        fun onClicked(userModel: UserModel)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = userList.size

    /**
     * @param userModel is used here again
     * to fetch the selected users id
     * to update the server
     */
    private fun updateUserToServer(userModel : UserModel) {
        val idToUrl = userModel.id
        val url2 = "https://dummyjson.com/users/$idToUrl"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.PUT, url2, null,
            { response ->
                val jsonResponse : String = response.toString()
                val userModel = UserList.fromJson(jsonResponse)
            },

            { error -> // error handling with toast for ui
                Log.d(VolleyLog.TAG,"Error: ${error.networkResponse?.statusCode}, " +
                        "${error.message}")
                val toast = Toast.makeText(context,
                    "Didnt get connection to BACKEN:D",
                    Toast.LENGTH_LONG)
                toast.show()
            }
        )
        // Volley httpclient's logic to RequestQueu
        val requestQueue = Volley.newRequestQueue(context) // Creates a RequestQueue
        requestQueue.add(jsonObjectRequest) // Adds the request to the queue
        requestQueue.start() // Starts the queue
    }
    // post request for adding users to server
    private fun addUserToServer(userModel : UserModel) {
       // val idToUrl = userModel.id
        val url2 = "https://dummyjson.com/users/add"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url2, null,
            { response ->
                val jsonResponse : String = response.toString()
                val userList = UserList.fromJson(jsonResponse)
            },
            { error ->
                Log.d(VolleyLog.TAG,"Error: ${error.networkResponse?.statusCode}," +
                        " ${error.message}")
                val toast = Toast.makeText(context,
                    "Didnt get connection to BACKEN:D",
                    Toast.LENGTH_LONG)
                toast.show()
            }
        )
        // Volley httpclient's logic to RequestQueu
        val requestQueue = Volley.newRequestQueue(context) // Creates a RequestQueue
        requestQueue.add(jsonObjectRequest) // Adds the request to the queue
        requestQueue.start() // Starts the queue
    }
    // delete request from server
    private fun deleteUserFromServer(userModel : UserModel) {
        val idToUrl = userModel.id
        val url2 = "https://dummyjson.com/users/$idToUrl"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.DELETE, url2, null,
            { response ->
                val jsonResponse : String = response.toString()
                val userList = UserList.fromJson(jsonResponse)
            },
            { error ->
                Log.d(VolleyLog.TAG,"Error: ${error.networkResponse?.statusCode}, " +
                        "${error.message}")
                val toast = Toast.makeText(context,
                    "Didnt get connection to BACKEN:D",
                    Toast.LENGTH_LONG)
                toast.show()
            }
        )
        // Volley httpclient's logic to RequestQueu
        val requestQueue = Volley.newRequestQueue(context) // Creates a RequestQueue
        requestQueue.add(jsonObjectRequest) // Adds the request to the queue
        requestQueue.start() // Starts the queue
    }
}

