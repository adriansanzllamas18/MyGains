package com.example.mygains.userinfo.domain.repositoryInterfaces

import com.example.mygains.userinfo.data.models.UserDataModel
import com.example.mygains.userinfo.data.models.WeightRegister

interface UserInfoInterface {
    suspend fun readUserInfo(): UserDataModel?
    suspend fun saveWeight(weightRegister: WeightRegister): WeightRegister
    suspend fun saveWeightInInfoUser(weightRegister: WeightRegister)
    suspend fun getAllWeightsByTime(startDate: String, endDate: String): List<WeightRegister>
}