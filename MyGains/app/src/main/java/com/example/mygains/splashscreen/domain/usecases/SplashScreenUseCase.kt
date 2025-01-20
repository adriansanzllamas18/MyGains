package com.example.mygains.splashscreen.domain.usecases

import com.example.mygains.splashscreen.data.repositoryImpl.SplashScreenRepositoryImpl
import com.example.mygains.splashscreen.domain.usecases.interfaces.SplashScreenUseCaseInterface
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class SplashScreenUseCase @Inject constructor(private var repositoryImpl: SplashScreenRepositoryImpl):SplashScreenUseCaseInterface {

    override fun  isAlreadyLoged(): FirebaseUser?{
        return repositoryImpl.isAlreadyLoged()
    }
}