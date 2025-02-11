package com.example.mygains.exercisesplan.domain.usecases

import com.example.mygains.exercisesplan.data.models.RoutineDayData
import com.example.mygains.exercisesplan.data.repositoryImpl.ExcercisesPlanRepositoryImpl
import com.example.mygains.exercisesplan.domain.usecases.interfaces.ExcercisesPlanUseCaseInterface
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeoutOrNull
import java.time.LocalDateTime
import javax.inject.Inject

class ExcercisesPlanUseCase @Inject constructor(private val repository: ExcercisesPlanRepositoryImpl):ExcercisesPlanUseCaseInterface {

    override suspend fun saveDataRoutine(routineDayData: RoutineDayData):Boolean{

        var timeStamp= LocalDateTime.now().toString()
        val routineDayDataToSave= hashMapOf(
            "date" to routineDayData.date,
            "exerciseType" to routineDayData.exerciseType,
            "timeOfDay" to routineDayData.timeOfDay,
            "exercises" to routineDayData.exercises
        )
        return repository.saveDataRoutine(timeStamp,routineDayDataToSave)
    }
}