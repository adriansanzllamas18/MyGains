package com.example.mygains.extras.utils.firebase

import java.text.SimpleDateFormat
import java.util.Date
import java.util.UUID

object FirestoreIdGenerator {

    // ID único basado en timestamp y usuario
    fun generateExerciseId(userId: String): String {
        val timestamp = System.currentTimeMillis()
        return "exercise_${userId}_$timestamp"
    }

    // ID para rutinas
    fun generateRoutineId(userId: String): String {
        val dateFormat = SimpleDateFormat("yyyyMMdd_HHmmss")
        return "routine_${userId}_${dateFormat.format(Date())}"
    }

    // ID completamente aleatorio
    fun generateRandomId(): String {
        return UUID.randomUUID().toString()
    }

    // ID con prefijo y información específica
    fun generateCustomId(prefix: String, vararg details: String): String {
        val detailsString = details.joinToString("_")
        return "${prefix}_${detailsString}_${System.currentTimeMillis()}"
    }
}