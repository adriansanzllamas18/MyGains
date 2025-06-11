package com.example.mygains.dashboard.ui.states

import com.example.mygains.dashboard.data.models.HomeScreenModel

sealed class DashBoardUIState {
     object Loading : DashBoardUIState()
     data class Succes(val homeScreenModel: HomeScreenModel): DashBoardUIState()
     data class Error(val text :String): DashBoardUIState()
 }