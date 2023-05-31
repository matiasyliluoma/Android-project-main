package fi.tuni.userapplication


import android.content.Context
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

/**
 * @author by Matias Yliluoma
 * @param context HttpResponse needs the context
 * in order to get Toast texts incase of errors
 * to show it on UI and for Volley http requests
 */
class HttpResponse(private val context: Context) {
    /**
     * fetchAll gets called on MainActivity
     * Fetches all users from dummyjson.com
     * @param callback is needed here to provide
     * it back for main activity
     */
    fun fetchAll(callback: (UserList) -> Unit) {
        val url = "https://dummyjson.com/users"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val jsonResponse : String = response.toString()

                val userList = UserList.fromJson(jsonResponse)
                callback(userList)
            },
            { error ->
                // error handling, ui notification
                val toast = Toast.makeText(context,
                    "${error.networkResponse?.statusCode}, " +
                            "${error.message} Check your internet connection",
                    Toast.LENGTH_LONG)
                toast.show()
            }
        )

        // Volley httpclient's logic to RequestQueu
        val requestQueue = Volley.newRequestQueue(context) // Creates a RequestQueue
        requestQueue.add(jsonObjectRequest) // Adds the request to the queue
        requestQueue.start() // Starts the queue
    }
    /**
     * fetchByText gets called on MainActivity
     * Fetches wanted user from dummyjson.com
     *
     *
     * @param callback is needed here to provide
     * it back for main activity
     * @param searchText is a String from MainActivity to
     * fill the GET request's url to get one user
     *
     */
    fun fetchByText(searchText: String, callback: (UserList) -> Unit) {

        val url2 = "https://dummyjson.com/users/search?q=$searchText"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url2, null,
            { response ->
                val jsonResponse : String = response.toString()
                val userList = UserList.fromJson(jsonResponse)
                callback(userList)
            },
            { error ->
                // error handling, ui notification
                val toast = Toast.makeText(context,
                    "${error.networkResponse?.statusCode}," +
                            " ${error.message} Check your internet connection",
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