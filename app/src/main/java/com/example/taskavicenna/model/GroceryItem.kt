import com.google.firebase.database.PropertyName

data class GroceryItem(
    @get:PropertyName("id") @set:PropertyName("id")
    var id: String = "",

    @get:PropertyName("name") @set:PropertyName("name")
    var name: String = "",

    @get:PropertyName("quantity") @set:PropertyName("quantity")
    var quantity: String = "",

    @get:PropertyName("imageUrl") @set:PropertyName("imageUrl")
    var imageUrl: String = ""
)
