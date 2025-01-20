package com.example.mygains.exercisesplan.data.repositoryImpl

import com.example.mygains.exercisesplan.domain.repositoryInterfaces.ExcercisesPlanRepositoryInterface
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject

class ExcercisesPlanRepositoryImpl @Inject constructor(private var firestore: FirebaseFirestore, private var firebaseAuth: FirebaseAuth):ExcercisesPlanRepositoryInterface {
    override suspend fun saveDataRoutine(timeStamp:String, routineDayData : HashMap<String,Any>): Boolean {

        val uid = firebaseAuth.currentUser?.uid ?: throw IllegalStateException("UID no disponible")
        var isSucces = false

        try {
            val result= withTimeoutOrNull(15000L){
                firestore.collection("users")
                    .document(uid)
                    .collection("historicRoutinePlans")
                    .document(timeStamp)//poner la fecha con hora
                    .set( routineDayData).await()
                isSucces= true
            }
            if (result== null){
                isSucces= false
            }
        }catch (exc:Exception){
            isSucces= false
        }
        return isSucces
    }

}