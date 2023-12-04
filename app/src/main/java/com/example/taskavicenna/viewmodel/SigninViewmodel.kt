package com.example.taskavicenna.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

// SignInViewModel.kt
class SignInViewModel : ViewModel() {
    private val _signInResult = MutableLiveData<Boolean>()
    val signInResult: LiveData<Boolean> get() = _signInResult

    private val firebaseAuth = FirebaseAuth.getInstance()

    fun signIn(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            _signInResult.value = it.isSuccessful
        }
    }
}
