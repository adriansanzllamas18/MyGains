package com.example.mygains.userinfo.domain.usecases.interfaces

import com.example.mygains.userinfo.data.models.UserData
import com.example.mygains.userinfo.data.models.WeightRegister

interface UserInfoUseCaseInterface {

    suspend fun readUserInfo(): UserData?
    suspend fun saveWeight(weightRegister: WeightRegister): WeightRegister
    suspend fun saveWeightInInfoUser(weightRegister: WeightRegister)
    suspend fun getAllWeightsByTime(since:String): List<WeightRegister>
}