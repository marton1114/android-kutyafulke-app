package com.example.kutyafulke.data.repositories.interfaces

import com.example.kutyafulke.data.models.Response
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {
    val currentUser: FirebaseUser?

    fun getCurrentUserId(): String

    fun signUpUserWithEmailAndPassword(email: String, password: String): Flow<Response<Boolean>>

    fun signInUserWithEmailAndPassword(email: String, password: String): Flow<Response<Boolean>>

    fun sendPasswordResetEmail(email: String): Flow<Response<Boolean>>

    fun sendEmailVerification(): Flow<Response<Boolean>>

    fun reloadUser(): Flow<Response<Boolean>>

    fun isUserSignedIn(): Boolean

    fun logoutUser()
}