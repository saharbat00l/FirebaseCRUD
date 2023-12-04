package com.example.taskavicenna.viewmodel

import GroceryItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.taskavicenna.repository.GroceryRepository

// GroceryViewModel.kt
class GroceryViewModel : ViewModel() {

    private val repository = GroceryRepository()

    val groceryItems: LiveData<List<GroceryItem>> = repository.getGroceryItems()

    fun addGroceryItem(name: String, quantity: String = "", imageUrl: String ="") {
        repository.addGroceryItem(name, quantity, imageUrl)
    }

    fun updateGroceryItem(groceryItem: GroceryItem) {
        repository.updateGroceryItem(groceryItem)
    }

    fun deleteGroceryItem(groceryItem: GroceryItem) {
        repository.deleteGroceryItem(groceryItem)
    }


}
