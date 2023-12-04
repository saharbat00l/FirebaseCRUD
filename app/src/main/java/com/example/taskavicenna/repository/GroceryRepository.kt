package com.example.taskavicenna.repository

import GroceryItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

// GroceryRepository.kt
class GroceryRepository {

    private val database = FirebaseDatabase.getInstance()

    fun getGroceryItems(): LiveData<List<GroceryItem>> {
        val liveData = MutableLiveData<List<GroceryItem>>()

        // Attach a listener to get updates from Firebase Realtime Database
        database.reference.child("grocery_items")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val items = mutableListOf<GroceryItem>()

                    for (childSnapshot in snapshot.children) {
                        val item = childSnapshot.getValue(GroceryItem::class.java)
                        item?.let {
                            item.id = childSnapshot.key.toString()
                            items.add(it)
                        }
                    }

                    liveData.value = items
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error
                }
            })

        return liveData
    }

    fun addGroceryItem(name: String, quantity: String, imageUrl: String) {
        val groceryItemRef = database.reference.child("grocery_items").push()
        val groceryItem = GroceryItem("", name, quantity, imageUrl)
        groceryItemRef.setValue(groceryItem)
    }

    fun updateGroceryItem(groceryItem: GroceryItem) {
        val groceryItemRef = database.reference.child("grocery_items").child(groceryItem.id)
        groceryItemRef.setValue(groceryItem)
    }

    fun deleteGroceryItem(groceryItem: GroceryItem) {
        val groceryItemRef = database.reference.child("grocery_items").child(groceryItem.id)
        groceryItemRef.removeValue()
    }
}
