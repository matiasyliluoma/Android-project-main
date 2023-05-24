package fi.tuni.userapplication
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

// Parsing the wanted json data out of dummyjson backend
data class UserModelClass(
    @SerializedName("id") var id: Int,
    @SerializedName("firstName") var firstName: String,
    @SerializedName("lastName") var lastName: String,
    @SerializedName("age") var age: Int,
    @SerializedName("email") var email: String
)

// Making a list of the dummyjson users
// Using the User data class
data class UserList(
    @SerializedName("users") val users: List<UserModelClass>?
) {
    companion object {

        fun fromJson(jsonResponse: String): UserList {
            return Gson().fromJson(jsonResponse, UserList::class.java)
        }
    }
}




