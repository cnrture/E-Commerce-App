package com.canerture.ecommerceapp.domain.repository

import com.canerture.ecommerceapp.data.model.User
import com.google.firebase.auth.AuthResult

interface Authenticator {

    suspend fun getFirebaseUserUid(): String

    suspend fun signUpWithEmailAndPassword(user: User, password: String)

    suspend fun signInWithEmailAndPassword(email: String, password: String): AuthResult

    suspend fun sendPasswordResetEmail(email: String): Void

    suspend fun isCurrentUserExist(): Boolean

    suspend fun getCurrentUser(): User

    suspend fun signOut()

}