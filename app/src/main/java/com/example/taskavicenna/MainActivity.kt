package com.example.taskavicenna

import AddGroceryDialog
import EditDeleteGroceryDialog
import GroceryAdapter
import GroceryItem
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskavicenna.ui.SigninActivity
import com.example.taskavicenna.viewmodel.GroceryViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var groceryViewModel: GroceryViewModel
    private lateinit var adapter: GroceryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkCurrentUser()

        //CRUD
        groceryViewModel = ViewModelProvider(this).get(GroceryViewModel::class.java)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        adapter = GroceryAdapter { groceryItem -> showEditDeleteDialog(groceryItem) }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        groceryViewModel.groceryItems.observe(this, { groceryItems: List<GroceryItem> ->
            adapter.submitList(groceryItems)
        })


        val addButton: Button = findViewById(R.id.addButton)
        addButton.setOnClickListener {
            showAddDialog()
        }

    }

    private fun showAddDialog() {
        val addDialog = AddGroceryDialog(this) { name, quantity ->
            val groceryItem = GroceryItem(name = name, quantity = quantity, imageUrl = "")
            groceryViewModel.addGroceryItem(groceryItem.toString())
            Toast.makeText(this, "Item added successfully", Toast.LENGTH_SHORT).show()
        }
        addDialog.show()
    }

    private fun showEditDeleteDialog(groceryItem: GroceryItem) {
        val editDeleteDialog = EditDeleteGroceryDialog(this,
            { name, quantity ->
                val updatedGroceryItem = groceryItem.copy(name = name, quantity = quantity)
                groceryViewModel.updateGroceryItem(updatedGroceryItem)
            },
            {
                groceryViewModel.deleteGroceryItem(groceryItem)
            }
        )
        editDeleteDialog.show()
    }




    // Check if the user is already signed in
    val currentUser = FirebaseAuth.getInstance().currentUser
    private fun checkCurrentUser() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null) {
            val intent = Intent(this, SigninActivity::class.java)
            startActivity(intent)
            finish()
            return
        }
    }

override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return true
}
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_logout -> {
                // Logout logic
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, SigninActivity::class.java)
                startActivity(intent)
                finish()
                return true
            }
            R.id.action_delete_account -> {
                // Delete account logic
                val user = FirebaseAuth.getInstance().currentUser

                // Check if the user is not null

                user?.let {
                    // Step 1: Delete user data from the Realtime Database (replace "users" with your actual database path)
                    val databaseReference = FirebaseDatabase.getInstance().getReference("users").child(it.uid)
                    databaseReference.removeValue()

                    // Step 2: Delete the user account from Firebase Authentication
                    it.delete().addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Account deleted", Toast.LENGTH_SHORT).show()

                            // Navigate back to the login activity after successful deletion
                            val intent = Intent(this, SigninActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, "Failed to delete account", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

}
