package fi.tuni.userapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyLog.TAG
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val fetchButton : Button = findViewById(R.id.fetchKaikki)


        // button to test out fetching all the users of dummy json
        fetchButton.setOnClickListener() {
            fetchUsers()
        }
    }

    fun fetchUsers() {
        httpConnect()
    }

    fun httpConnect() {
// Volley httpclient to take make GET request on dummyjson.com on all users
        val url = "https://dummyjson.com/users"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                val responseOf : String = "Response: %s".format(response.toString())
                // Logs all the users
                Log.d(TAG, "response: $responseOf")
            },
            // For possible errors
            Response.ErrorListener { error ->
                Log.d(TAG,"Error: ${error.networkResponse?.statusCode}, ${error.message}")
            }
        )

        // Volley httpclient's logic to RequestQueu
        val requestQueue = Volley.newRequestQueue(this) // Creates a RequestQueue
        requestQueue.add(jsonObjectRequest) // Adds the request to the queue
        requestQueue.start() // Starts the queue
    }
}