package com.example.mygains.login.domain

import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class LoginUseCase @Inject constructor(private var authDB:FirebaseAuth ) {

      suspend fun logInWithEmailPassword(user:String, pass:String): FirebaseUser? {
       return authDB.signInWithEmailAndPassword(user, pass).await().user
    }

    suspend fun createUserWithGoogleCredentials(account: GoogleSignInAccount?): FirebaseUser? {
        val credential = GoogleAuthProvider.getCredential(account?.idToken, null)

        return try {
            authDB.signInWithCredential(credential).await().user

        } catch (e: Exception) {
            null
        }
    }
}


