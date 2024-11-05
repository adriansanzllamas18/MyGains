package com.example.mygains.exercisesplan.domain

import com.example.mygains.exercisesplan.data.RoutineDayData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

class ExcercisesPlanUseCase @Inject constructor(private var firestore: FirebaseFirestore,private var firebaseAuth: FirebaseAuth) {

    suspend fun saveDataRoutine(routineDayData:RoutineDayData):Boolean{
        var isSucces = false

        var timeStamp= LocalDateTime.now().toString()
        val routineDayDataToSave= hashMapOf(
            "date" to timeStamp,
            "excerciseType" to routineDayData.exerciseType,
            "excercises" to routineDayData.exercises
        )


        val uid = firebaseAuth.currentUser?.uid ?: throw IllegalStateException("UID no disponible")

        try {
           val result= firestore.collection("users")
                .document(uid)
                .collection("historicRoutinePlans")
                .document(timeStamp)//poner la fecha con hora
                .set( routineDayDataToSave).await()
               isSucces= true
            /*if (result.isSuccessful){
                routineDayData.exercisesList.forEach {exercise->

                    try {
                        var excercises= hashMapOf(
                            "exercise_name" to exercise.name,
                            "excercise_reps" to exercise.reps,
                            "excercise_series" to exercise.series
                        )

                        var result=firestore.collection("users")
                            .document(uid)
                            .collection("historicRoutinePlans")
                            .document(timeStamp)//poner la fecha con hora
                            .collection("ExcercisesOfDay")
                            .document(timeStamp)//poner fecha y hora
                            .set(excercises)

                        if (result.isSuccessful){
                            isSucces = true
                        }else{
                            isSucces=false
                        }
                    }catch (ex:Exception){

                    }
                }

            }else{
                isSucces = false
            }*/

        }catch (exc:Exception){
            isSucces= false
        }
        return isSucces
    }
}