package com.example.mygains.routinedetail.data

import com.example.mygains.exercisesplan.data.models.Exercises
import com.example.mygains.exercisesplan.data.models.RoutineDayData

data class RoutineDetailModel(
    val routineDayModel:RoutineDayData? = RoutineDayData(),
    val date:String? = "",
    val playlists:MutableList<String>?= mutableListOf(),
    val infoTarget:String? = "",
    val totalKcl:String? = "",
    val duration:String? = ""
)

