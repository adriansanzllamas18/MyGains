package com.example.mygains.userinfo.domain.usecases.interfaces

import com.example.mygains.userinfo.data.models.UserDataModel
import com.example.mygains.userinfo.data.models.WeightRegister

interface UserInfoUseCaseInterface {

    suspend fun readUserInfo(): UserDataModel?
}