package com.example.mygains.dashboard.domain.repositoryInterfaces

import com.example.mygains.base.response.BaseResponse
import okhttp3.Response

interface DashBoardRepositroyInterface {

    suspend fun getRoutineForToday(): BaseResponse<Boolean>
}