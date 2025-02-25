package com.example.mygains.createroutineprocess.data.models

data class StrengthExerciseModel(
    val bestFor: MutableList<String>?= mutableListOf(),
    val calories_estimated: String? ="",
    val description: String? ="",
    val difficulty: String? ="",
    val equipment: MutableList<String>?= mutableListOf(),
    val exerciseId: String? ="",
    val image_url: String? ="",
    val mechanic: String? ="",
    val muscle_group_id: String? ="",
    val name: String? ="",
    val primary_muscle: String? ="",
    val secondary_muscles: MutableList<String>?= mutableListOf(),
    val tips: MutableList<String>?= mutableListOf(),
    val type: String? ="",
    val variations: MutableList<String>?= mutableListOf(),
    val commonMistakes: MutableList<String>?= mutableListOf()
)
