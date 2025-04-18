package com.example.mygains.createroutineprocess.data.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class ExerciseLog(
    @DocumentId
    val id: String = "",
    val exerciseId: String = "", // Referencia al Exercise original
    val routineId:String ="",//Referencia a la rutina donde se realizo
    val name: String = "",
    val muscleGroup: String = "",
    val date: String = "",
    val sets: List<ExerciseSet> = emptyList(),
    val duration: Int = 0, // Duración en minutos
    val notes: String? = null
) {
    // Calcula el volumen total del ejercicio (peso * repeticiones)
    fun calculateTotalVolume(): Float =
        sets.sumOf { (it.weight.toFloat() * it.reps.toInt()).toDouble() }.toFloat()
}
