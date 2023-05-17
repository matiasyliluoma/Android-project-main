package fi.tuni.userapplication


import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyLog
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

class HttpResponse(private val context: Context) {
    fun httpConnectAndFetchUsers(callback: (UserList) -> Unit) {
// Volley httpclient to take make GET
// request on dummyjson.com on all users
        val url = "https://dummyjson.com/users"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                val jsonResponse : String = response.toString()
                var user = User(jsonResponse)
                var userList = UserList(jsonResponse)
                // Access the users list
                val users = userList.users
                // Logs all the users
                Log.d(VolleyLog.TAG, "response: $jsonResponse")
            },
            // For possible errors
            Response.ErrorListener { error ->
                Log.d(VolleyLog.TAG,"Error: ${error.networkResponse?.statusCode}, ${error.message}")
            }
        )

        // Volley httpclient's logic to RequestQueu
        val requestQueue = Volley.newRequestQueue(context) // Creates a RequestQueue
        requestQueue.add(jsonObjectRequest) // Adds the request to the queue
        requestQueue.start() // Starts the queue
    }
}