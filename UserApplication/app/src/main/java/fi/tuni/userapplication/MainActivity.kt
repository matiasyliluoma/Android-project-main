package fi.tuni.userapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import java.io.Serializable


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val fetchButton : Button = findViewById(R.id.fetchAll)



        // button to test out fetching all the users of dummy json
        fetchButton.setOnClickListener() {


            val httpResponse = HttpResponse(this)
            //  Log.d("TAG", "yksi")

            httpResponse.httpConnectAndFetchUsers { jsonResponse ->
                //    Log.d("TAG", "kaksi")
                val intent = Intent(this, SecondActivity::class.java)
                intent.putExtra("jsonResponse", jsonResponse.toString())
                startActivity(intent)
            }
        }
    }
}