package com.example.mygains.login.domain

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class LoginUseCase @Inject constructor(private var authDB:FirebaseAuth ) {

      suspend fun logInWithEmailPassword(user:String, pass:String): FirebaseUser? {
       return authDB.signInWithEmailAndPassword(user, pass).await().user
    }


}