package com.example.mygains.plan.domain.usecases

import com.example.mygains.exercisesplan.data.models.RoutineDayData
import com.example.mygains.plan.data.repositoryImpl.PlanRepositoryImpl
import com.example.mygains.plan.domain.usecases.usecaseInterfaces.PlanUsecaseInterface
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PlanUseCase @Inject constructor(private var repositoryImpl: PlanRepositoryImpl): PlanUsecaseInterface {


   override suspend fun getPlanForTheDay(date:String):MutableList<RoutineDayData> {
        return repositoryImpl.getPlanForTheDay(date)
    }

}