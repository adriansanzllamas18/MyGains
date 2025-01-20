package com.example.mygains.userinfo.domain.repositoryInterfaces

import com.example.mygains.userinfo.data.models.UserData
import com.example.mygains.userinfo.data.models.WeightRegister

interface UserInfoInterface {
    suspend fun readUserInfo(): UserData?
    suspend fun saveWeight(weightRegister: WeightRegister): WeightRegister
    suspend fun saveWeightInInfoUser(weightRegister: WeightRegister)
    suspend fun getAllWeightsByTime(startDate: String, endDate: String): List<WeightRegister>
}