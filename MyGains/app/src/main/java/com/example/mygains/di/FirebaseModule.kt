package com.example.mygains.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class FirebaseModule {


    //estas dos etiquetas indican que una vez querramos utilizar firebaseauth dagger directamente con la injection te lo provee
    //la etuiqueta singleton es la itiqueta que indica que cree una unica instancia
    @Singleton
    @Provides
    fun provideFirebaseAuth():FirebaseAuth{
        return FirebaseAuth.getInstance()
    }

    @Singleton
    @Provides
    fun provideFirebaseFirestore():FirebaseFirestore{
        return Firebase.firestore
    }

    @Singleton
    @Provides
    fun privideUidCurrentUser(): String? {
        return FirebaseAuth.getInstance().uid
    }
}