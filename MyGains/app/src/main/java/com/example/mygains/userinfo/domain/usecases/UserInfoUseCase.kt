package com.example.mygains.userinfo.domain.usecases

import com.example.mygains.base.response.BaseResponse
import com.example.mygains.repositories.UserInfoRepository
import com.example.mygains.userinfo.data.models.WeightRegister
import com.example.mygains.userinfo.data.models.UserDataModel
import com.example.mygains.userinfo.data.models.UserHealthDataModel
import com.example.mygains.userinfo.data.models.UserNutritionDataModel
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

    suspend fun readUserNutritionGoalsData():BaseResponse<UserNutritionDataModel>{
        return userInfoRepository.readUserUserNutritionGoalsData()
    }

}