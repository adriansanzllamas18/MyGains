package com.example.mygains.dashboard.domain.usecases

import com.example.mygains.base.response.BaseResponse
import com.example.mygains.repositories.UserInfoRepository
import com.example.mygains.userinfo.data.models.UserDataModel
import javax.inject.Inject

class DashBoardUseCase @Inject constructor(private var userInfoRepository: UserInfoRepository) {


    suspend fun readInfoUser():BaseResponse<UserDataModel>{
        return userInfoRepository.readUserInfo()
    }
}