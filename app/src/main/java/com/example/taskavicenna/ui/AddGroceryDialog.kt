import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.taskavicenna.databinding.DialogAddGroceryBinding

class AddGroceryDialog(context: Context, private val onAddClicked: (name: String, quantity: String) -> Unit) {
    private val binding: DialogAddGroceryBinding// Use your actual binding class
    private val dialog: AlertDialog

    init {
        val builder = AlertDialog.Builder(context)
        binding = DialogAddGroceryBinding.inflate(LayoutInflater.from(context))
        builder.setView(binding.root)
        dialog = builder.create()


        binding.buttonAdd.setOnClickListener {
            val name = binding.editTextName.text.toString()
            val quantity = binding.editTextQuantity.text.toString()

            if (name.isNotEmpty() && quantity.isNotEmpty()) {
                onAddClicked(name, quantity)
                dialog.dismiss()
            } else {
                Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun show() {
        dialog.show()
    }
}
