package com.example.mygains.splashscreen.data.repositoryImpl

import com.example.mygains.splashscreen.domain.repositoryInterfaces.SplashRepositoryInterface
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class SplashScreenRepositoryImpl@Inject constructor(private var firebaseAuth: FirebaseAuth):SplashRepositoryInterface {
    override fun isAlreadyLoged(): FirebaseUser? {
        return firebaseAuth.currentUser
    }
}