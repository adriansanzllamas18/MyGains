package com.example.mygains.createroutineprocess.data.models

data class ExerciseWithSets(
    var exercise:StrengthExerciseModel= StrengthExerciseModel(),
    var sets:MutableList<ExerciseSet> = mutableListOf()
)
