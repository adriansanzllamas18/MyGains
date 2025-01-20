package com.example.mygains.splashscreen.domain.usecases.interfaces

import com.google.firebase.auth.FirebaseUser

interface SplashScreenUseCaseInterface {
    fun  isAlreadyLoged(): FirebaseUser?
}