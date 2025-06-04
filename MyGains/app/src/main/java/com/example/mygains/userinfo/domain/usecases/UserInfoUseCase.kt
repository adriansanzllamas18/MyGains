package com.example.mygains.userinfo.domain.usecases

import com.example.mygains.userinfo.data.models.WeightRegister
import com.example.mygains.userinfo.data.models.UserDataModel
import com.example.mygains.userinfo.data.repositoryImpl.UserInfoImpl
import com.example.mygains.userinfo.domain.usecases.interfaces.UserInfoUseCaseInterface
import java.util.Calendar
import javax.inject.Inject

class UserInfoUseCase @Inject constructor(private val repository: UserInfoImpl):UserInfoUseCaseInterface{


    /*suspend fun getAllWeightsByTime(lastMonth: String): MutableList<WeightRegister> {
        val uid = firebaseAuth.currentUser?.uid ?: throw IllegalStateException("UID no disponible")

        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        var startDate=""
        var endDate=""

        // Obtener el número del mes actual (sumar 1 porque Calendar.MONTH devuelve 0-11)
        val currentMonth = calendar.get(Calendar.MONTH) + 1

        if (lastMonth == "Mes") {
            // Cálculo de fechas para el mes específico
            startDate = "$currentYear-$currentMonth-01" // Fecha de inicio del mes
            val daysInMonth = calendar.apply { set(Calendar.MONTH, currentMonth.toInt() - 1); set(Calendar.YEAR, currentYear) }.getActualMaximum(Calendar.DAY_OF_MONTH)
            endDate = "$currentYear-$currentMonth-$daysInMonth" // Fecha de fin del mes
        } else if (lastMonth == "Año") {
            // Cálculo de fechas para el año específico
            startDate = "$currentYear-01-01" // Fecha de inicio del año
            endDate = "$currentYear-12-31" // Fecha de fin del año
        } else {
            // Cálculo de fechas para la semana actual
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY) // Configura el calendario al primer día de la semana (Lunes)
            startDate = calendar.time.toString().substring(0, 10) // Obtener la fecha en formato YYYY-MM-DD
            calendar.add(Calendar.DAY_OF_WEEK, 6) // Avanzar a la fecha de fin de semana (Domingo)
            endDate = calendar.time.toString().substring(0, 10) // Obtener la fecha en formato YYYY-MM-DD
        }


        // Consulta en Firebase usando el rango de fechas
        val result = firestore.collection("users")
            .document(uid)
            .collection("historicWeight")
            .whereGreaterThanOrEqualTo("date", startDate)  // Filtrar desde el inicio del rango
            .whereLessThanOrEqualTo("date", endDate)       // Filtrar hasta el último día del mes
            .get().await()

        // Transformar los documentos en una lista mutable de WeightRecord
        val weightsList = result.documents.mapNotNull { document ->
            // Aquí asumimos que el documento tiene los campos "weight" y "date"
            val weight = document.getString("weight")// Cambia a Float si lo necesitas
            val date = document.getString("date")

            // Si weight o date son null, no lo añadimos a la lista
            if (weight != null && date != null) {
                WeightRegister(weight = weight, date =  date)
            } else {
                null
            }
        }.toMutableList()

        return weightsList
    }*/
    override suspend fun readUserInfo(): UserDataModel? {
        return repository.readUserInfo()
    }

    override suspend fun saveWeight(weightRegister: WeightRegister): WeightRegister {
        return repository.saveWeight(weightRegister)
    }

    override suspend fun saveWeightInInfoUser(weightRegister: WeightRegister) {
       return repository.saveWeightInInfoUser(weightRegister)
    }

    override suspend fun getAllWeightsByTime(
       since:String
    ): List<WeightRegister> {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        var startDate=""
        var endDate=""

        // Obtener el número del mes actual (sumar 1 porque Calendar.MONTH devuelve 0-11)
        val currentMonth = calendar.get(Calendar.MONTH) + 1

        if (since == "Mes") {
            // Cálculo de fechas para el mes específico
            startDate = "$currentYear-$currentMonth-01" // Fecha de inicio del mes
            val daysInMonth = calendar.apply { set(Calendar.MONTH, currentMonth.toInt() - 1); set(Calendar.YEAR, currentYear) }.getActualMaximum(Calendar.DAY_OF_MONTH)
            endDate = "$currentYear-$currentMonth-$daysInMonth" // Fecha de fin del mes
        } else if (since == "Año") {
            // Cálculo de fechas para el año específico
            startDate = "$currentYear-01-01" // Fecha de inicio del año
            endDate = "$currentYear-12-31" // Fecha de fin del año
        } else {
            // Cálculo de fechas para la semana actual
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY) // Configura el calendario al primer día de la semana (Lunes)
            startDate = calendar.time.toString().substring(0, 10) // Obtener la fecha en formato YYYY-MM-DD
            calendar.add(Calendar.DAY_OF_WEEK, 6) // Avanzar a la fecha de fin de semana (Domingo)
            endDate = calendar.time.toString().substring(0, 10) // Obtener la fecha en formato YYYY-MM-DD
        }

       return  repository.getAllWeightsByTime(startDate, endDate)
    }
}