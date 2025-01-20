package com.example.mygains.exercisesplan.domain.repositoryInterfaces


interface ExcercisesPlanRepositoryInterface {
    suspend fun saveDataRoutine(timeStamp:String,routineDayData: HashMap<String,Any>):Boolean
}