package com.example.mygains.splashscreen.domain

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class SplashScreenUseCase @Inject constructor(private var firebaseAuth: FirebaseAuth) {

    fun  isAlreadyLoged(): FirebaseUser?{
        return firebaseAuth.currentUser
    }
}