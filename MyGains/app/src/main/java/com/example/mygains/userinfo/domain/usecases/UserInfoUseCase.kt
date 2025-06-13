package com.example.mygains.userinfo.domain.usecases

import com.example.mygains.base.response.BaseResponse
import com.example.mygains.base.response.errorresponse.BaseFireStoreError
import com.example.mygains.repositories.UserInfoRepository
import com.example.mygains.userinfo.data.models.UserCurrentNutritionDataModel
import com.example.mygains.userinfo.data.models.WeightRegister
import com.example.mygains.userinfo.data.models.UserDataModel
import com.example.mygains.userinfo.data.models.UserHealthDataModel
import com.example.mygains.userinfo.data.models.UserNutritionDataModel
import com.example.mygains.userinfo.data.models.UserNutritionGoalsDataModel
import com.example.mygains.userinfo.data.repositoryImpl.UserInfoImpl
import com.example.mygains.userinfo.domain.usecases.interfaces.UserInfoUseCaseInterface
import kotlinx.coroutines.Job
import java.util.Calendar
import javax.inject.Inject

class UserInfoUseCase @Inject constructor(private val userInfoRepository: UserInfoRepository){


    suspend fun getUserInfo():BaseResponse<UserDataModel>{
       return userInfoRepository.readUserInfo()
    }

    suspend fun readUserHealthData():BaseResponse<UserHealthDataModel>{
        return userInfoRepository.readUserHealthData()
    }

    /*suspend fun readUserCurrentNutritionData():BaseResponse<UserNutritionDataModel>{

        return try {
            // Ejecutar primera llamada
            val goalsResponse = userInfoRepository.readUserNutritionGoalsData()
            if (goalsResponse is BaseResponse.Error) {
                return BaseResponse.Error(goalsResponse.error)
            }

            // Ejecutar segunda llamada
            val currentResponse = userInfoRepository.readUserCurrentNutritionData()
            if (currentResponse is BaseResponse.Error) {
                return BaseResponse.Error(currentResponse.error)
            }

            // Combinar datos si ambas son exitosas
            val goalsData = (goalsResponse as BaseResponse.Success).data
            val currentData = (currentResponse as BaseResponse.Success).data

            val combinedData = UserNutritionDataModel(
                userNutritionGoalsDataModel = goalsData,
                userCurrentNutritionDataModel = currentData
            )

            BaseResponse.Success(combinedData)

        } catch (ex: Exception) {
            BaseResponse.Error(BaseFireStoreError.UnknownError)
        }
    }*/


    suspend fun readusercurrent():BaseResponse<UserCurrentNutritionDataModel>{
        return userInfoRepository.readUserCurrentNutritionData()
    }

    suspend fun readusergoal():BaseResponse<UserNutritionGoalsDataModel>{
        return userInfoRepository.readUserNutritionGoalsData()
    }

}