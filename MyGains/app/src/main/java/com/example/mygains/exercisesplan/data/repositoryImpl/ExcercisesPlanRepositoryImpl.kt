package com.example.mygains.exercisesplan.data.repositoryImpl

import com.example.mygains.exercisesplan.data.models.RoutineDayData
import com.example.mygains.exercisesplan.domain.repositoryInterfaces.ExcercisesPlanRepositoryInterface
import com.example.mygains.routinedetail.data.RoutineDetailModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject

class ExcercisesPlanRepositoryImpl @Inject constructor(private var firestore: FirebaseFirestore, private var firebaseAuth: FirebaseAuth):ExcercisesPlanRepositoryInterface {
    override suspend fun saveDataRoutine(timeStamp:String, routineDayData : RoutineDayData): Boolean {

        val uid = firebaseAuth.currentUser?.uid ?: throw IllegalStateException("UID no disponible")
        var isSucces = false


        val routineDayDataToSave= hashMapOf(
            "date" to  routineDayData.date,
            "id" to routineDayData.setRoutineId(),
            "exerciseType" to routineDayData.exerciseType,
            "timeOfDay" to routineDayData.timeOfDay,
            "exercises" to routineDayData.exercises
        )

        val routineDetailToSave= hashMapOf(
            "date" to routineDayData.toRoutineDetailModel().date,
            "routineDayModel" to routineDayData.toRoutineDetailModel().routineDayModel
        )


        try {
            val result= withTimeoutOrNull(15000L){
                firestore.collection("users")
                    .document(uid)
                    .collection("historicRoutinePlans")
                    .document(timeStamp)//poner la fecha con hora
                    .set( routineDayDataToSave).await()

                firestore.collection("users")
                    .document(uid)
                    .collection("routineOfDay")
                    .document(timeStamp)//poner la fecha con hora
                    .set(routineDetailToSave).await()

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