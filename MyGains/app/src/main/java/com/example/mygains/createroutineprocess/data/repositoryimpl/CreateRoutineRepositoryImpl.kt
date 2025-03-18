package com.example.mygains.createroutineprocess.data.repositoryimpl

import com.example.mygains.base.response.errorresponse.BaseAuthError
import com.example.mygains.base.response.BaseResponse
import com.example.mygains.base.response.errorresponse.BaseFireStoreError
import com.example.mygains.createroutineprocess.data.models.DailyRoutine
import com.example.mygains.createroutineprocess.data.models.InfoTypeOfWorkOutModel
import com.example.mygains.createroutineprocess.data.models.StrengthExerciseModel
import com.example.mygains.createroutineprocess.data.models.TypeOfWorkOutModel
import com.example.mygains.createroutineprocess.domain.repositoryInterfaces.CreateRoutineRepositoryInterface
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout
import java.io.IOException
import javax.inject.Inject


//todo cambiar los errorres por firebasefirestrore errors

class CreateRoutineRepositoryImpl @Inject constructor(var firestore: FirebaseFirestore, var firebaseAuth: FirebaseAuth):
    CreateRoutineRepositoryInterface {

    override suspend fun getAllTrainingData(): BaseResponse<MutableList<TypeOfWorkOutModel>> {
      return try {
          val data =  firestore.collection("workouts").get().await()
          BaseResponse.Success(data = data.toObjects(TypeOfWorkOutModel::class.java))
      }catch (ex:Exception){
          BaseResponse.Error(BaseAuthError.UnknownError(ex.message))
      }
    }

    override suspend fun getAllInfoWorkOuts(workout_id: String): BaseResponse<MutableList<InfoTypeOfWorkOutModel>> {
        try {
            val result= firestore.collection("muscular_groups").whereEqualTo("workout_id", workout_id ).get().await()
            return BaseResponse.Success(result.toObjects(InfoTypeOfWorkOutModel::class.java))

        }catch (ex: IOException){
            return BaseResponse.Error(BaseAuthError.UnknownError(ex.message))
        }
    }

    override suspend fun getAllExercises(muscle_id: String): BaseResponse<MutableList<StrengthExerciseModel>> {
        try {
            val data= firestore.collection("exercises").whereEqualTo("muscle_group_id", muscle_id).get().await()
            return BaseResponse.Success(data.toObjects(StrengthExerciseModel::class.java))
        }catch (ex:Exception){
            return BaseResponse.Error(BaseAuthError.UnknownError(ex.message))
        }
    }

    override suspend fun saveRoutine(routine: DailyRoutine): BaseResponse<String> {
        val routineMap = hashMapOf(
            "id" to routine.id,
            "userId" to routine.userId,
            "date" to routine.date,
            "exercises" to routine.exercises
        )

        return try {
            withTimeout(15000) { // 15 segundos de timeout
                // Realizar la operación con el Firestore configurado para no persistir
                val result = firestore.collection("users").document(routine.userId)
                    .collection("routineHistoric")
                    .document()
                    .set(routineMap,SetOptions.merge()).await()
                BaseResponse.Success("Rutina creada correctamente.")
            }

        } catch (e: TimeoutCancellationException) {
            BaseResponse.Error(BaseFireStoreError.TimeOut)
        } catch (e: FirebaseFirestoreException) {
            when (e.code) {
                FirebaseFirestoreException.Code.UNKNOWN -> BaseResponse.Error(BaseFireStoreError.UnknownError)
                FirebaseFirestoreException.Code.NOT_FOUND -> BaseResponse.Error(BaseFireStoreError.DocumentNotFound)
                FirebaseFirestoreException.Code.UNAVAILABLE -> BaseResponse.Error(BaseFireStoreError.Unavailable)
                else -> BaseResponse.Error(BaseFireStoreError.UnknownError)
            }
        } catch (e: Exception) {
            BaseResponse.Error(BaseFireStoreError.UnknownError)
        }
    }
}