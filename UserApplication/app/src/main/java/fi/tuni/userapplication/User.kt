package fi.tuni.userapplication
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

// Parsing the wanted json data out of dummyjson backend
data class User(
    @SerializedName("id") var id: Int,
    @SerializedName("firstName") var firstName: String,
 /*   @SerializedName("lastName") var lastName: String,
    @SerializedName("age") var age: Int,
    @SerializedName("email") var email: String,
    @SerializedName("image") var image: String */
) {

    constructor(jsonResponse: String) : this(
                                    0,
                                    ""/*
                                    "",
                                    0,
                                    "",
                                    ""*/) {
        val gson = Gson()
        val user = gson.fromJson(jsonResponse, User::class.java)

        // Assign the properties from the parsed JSON to the current User instance
        this.id = user.id
        this.firstName = user.firstName
     /*   this.lastName = user.lastName
        this.age = user.age
        this.email = user.email
        this.image = user.image*/
    }
}
// Making a list of the dummyjson users
// Using the User data class
data class UserList(
    @SerializedName("users") val users: List<User>
) {
    // Parse the JSON response into a UserList object
    constructor(jsonResponse: String) : this(gson.fromJson(jsonResponse, UserList::class.java).users)
}

// Create a Gson instance
val gson = Gson()


