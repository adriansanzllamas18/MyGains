package com.example.mygains.splashscreen.domain.repositoryInterfaces

import com.google.firebase.auth.FirebaseUser

interface SplashRepositoryInterface {
    fun  isAlreadyLoged(): FirebaseUser?
}