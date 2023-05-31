package fi.tuni.userapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

/**
 * @author by Matias Yliluoma
 *
 * UserInfoActivity is activity and View
 * when you want to either edit user
 * or add new user
 */
class UserInfoActivity : AppCompatActivity() {
    private var idEditText : EditText? = null
    private var firstEditText : EditText? = null
    private var surEditText : EditText? = null
    private var emailEditText : EditText? = null
    private var ageEditText : EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_user_info)

        this.idEditText = findViewById(R.id.userID)
        this.firstEditText = findViewById(R.id.userFirstNameView)
        this.surEditText = findViewById(R.id.userLastNameView)
        this.emailEditText = findViewById(R.id.userEmail)
        this.ageEditText = findViewById(R.id.userAge)
        // Universal button for add and edit
        val saveNewUser : Button = findViewById(R.id.addThis)



        val editId = intent.getStringExtra("id")
        val editFirst = intent.getStringExtra("firstname")
        val editSur = intent.getStringExtra("lastname")
        val editAge = intent.getStringExtra("age")
        val editEmail = intent.getStringExtra("email")
        val isEdit = intent.getBooleanExtra("isEditMode", false)


        // setText for adding info on fields
        idEditText?.setText(editId)
        firstEditText?.setText(editFirst)
        surEditText?.setText(editSur)
        ageEditText?.setText(editAge)
        emailEditText?.setText(editEmail)
        // Click listener for sending info back to
        // SecondActivity
        saveNewUser.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("id", idEditText?.text.toString().toInt())
            intent.putExtra("firstname", firstEditText?.text.toString())
            intent.putExtra("lastname", surEditText?.text.toString())
            intent.putExtra("email", emailEditText?.text.toString())
            intent.putExtra("age", ageEditText?.text.toString().toInt())
            // the boolean flag if this activity is
            // called by Edit or Add
            intent.putExtra("isEditMode", isEdit)
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}