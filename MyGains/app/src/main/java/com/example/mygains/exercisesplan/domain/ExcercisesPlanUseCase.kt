package com.example.mygains.exercisesplan.domain

import com.example.mygains.exercisesplan.data.RoutineDayData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeoutOrNull
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

class ExcercisesPlanUseCase @Inject constructor(private var firestore: FirebaseFirestore,private var firebaseAuth: FirebaseAuth) {

    suspend fun saveDataRoutine(routineDayData:RoutineDayData):Boolean{
        var isSucces = false

        var timeStamp= LocalDateTime.now().toString()
        val routineDayDataToSave= hashMapOf(
            "date" to LocalDate.now().toString(),
            "excerciseType" to routineDayData.exerciseType,
            "excercises" to routineDayData.exercises
        )


        val uid = firebaseAuth.currentUser?.uid ?: throw IllegalStateException("UID no disponible")

        try {
           val result= withTimeoutOrNull(15000L){
               firestore.collection("users")
                   .document(uid)
                   .collection("historicRoutinePlans")
                   .document(timeStamp)//poner la fecha con hora
                   .set( routineDayDataToSave).await()
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