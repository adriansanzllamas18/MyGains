package com.example.mygains.userinfo.ui.states

import com.example.mygains.userinfo.data.models.UserHealthDataModel

sealed class UserProfileHealthSectionUIState {

    object Loading : UserProfileHealthSectionUIState()
    data class Succes(val userhealthdata: UserHealthDataModel): UserProfileHealthSectionUIState()
    data class Error(val error :String): UserProfileHealthSectionUIState()
    object NodataYet : UserProfileHealthSectionUIState()
}