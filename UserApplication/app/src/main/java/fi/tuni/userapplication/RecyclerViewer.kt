package fi.tuni.userapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val userList: List<UserModelClass>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var usersID : TextView = itemView.findViewById(R.id.userID)
        var userFirstName: TextView = itemView.findViewById(R.id.userFirstNameView)
        var userLastName: TextView = itemView.findViewById(R.id.userLastNameView)
        var usersAge : TextView = itemView.findViewById(R.id.userAge)
        var usersEmail : TextView = itemView.findViewById(R.id.userEmail)
        init {
            // Define click listener for the ViewHolder's View
            userFirstName = itemView.findViewById(R.id.userFirstNameView)
        }
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
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = userList.size

}

