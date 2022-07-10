package com.canerture.e_commerce_app.data.model

import com.google.firebase.auth.FirebaseUser

sealed class FirebaseAuthResult {
    data class Success(val firebaseUser: FirebaseUser) : FirebaseAuthResult()
    data class Error(val errorMsg: String) : FirebaseAuthResult()
}