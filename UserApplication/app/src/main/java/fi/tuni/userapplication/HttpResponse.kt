package fi.tuni.userapplication


import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyLog
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.annotations.SerializedName

class HttpResponse(private val context: Context) {
    fun fetchAll(callback: (UserList) -> Unit) {
// Volley httpclient to take make GET
// request on dummyjson.com on all users
        val url = "https://dummyjson.com/users"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val jsonResponse : String = response.toString()
               // var user = UserModelClass(0, "")
                val userList = UserList.fromJson(jsonResponse)
                // Access the users list
                callback(userList)
                // Logs all the users
                Log.d(VolleyLog.TAG, "response: $userList")
            },
            // For possible errors
            { error ->
                // If time will do proper UI alert if
                // no connection
                Log.d(VolleyLog.TAG,"Error: ${error.networkResponse?.statusCode}, ${error.message}")
            }
        )

        // Volley httpclient's logic to RequestQueu
        val requestQueue = Volley.newRequestQueue(context) // Creates a RequestQueue
        requestQueue.add(jsonObjectRequest) // Adds the request to the queue
        requestQueue.start() // Starts the queue
    }
    fun fetchByText(searchText: String, callback: (UserList) -> Unit) {
        val url2 = "https://dummyjson.com/users/search?q=$searchText"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url2, null,
            { response ->
                val jsonResponse : String = response.toString()
                // var user = UserModelClass(0, "")
                val userList = UserList.fromJson(jsonResponse)
                // Access the users list
                callback(userList)
                // Logs all the users
                Log.d(VolleyLog.TAG, "response: $userList")
            },
            // For possible errors
            { error ->
                // If time will do proper UI alert if
                // no connection
                Log.d(VolleyLog.TAG,"Error: ${error.networkResponse?.statusCode}, ${error.message}")
            }
        )

        // Volley httpclient's logic to RequestQueu
        val requestQueue = Volley.newRequestQueue(context) // Creates a RequestQueue
        requestQueue.add(jsonObjectRequest) // Adds the request to the queue
        requestQueue.start() // Starts the queue
    }
}