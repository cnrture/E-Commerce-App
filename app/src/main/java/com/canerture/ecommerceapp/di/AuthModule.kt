package com.canerture.ecommerceapp.di

import com.canerture.ecommerceapp.data.repository.FirebaseAuthenticator
import com.canerture.ecommerceapp.domain.repository.Authenticator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AuthModule {

    @Provides
    @Singleton
    fun provideFirebaseAuthenticator(
        firebaseAuth: FirebaseAuth, firebaseFirestore: FirebaseFirestore,
    ): Authenticator = FirebaseAuthenticator(firebaseAuth, firebaseFirestore)
}