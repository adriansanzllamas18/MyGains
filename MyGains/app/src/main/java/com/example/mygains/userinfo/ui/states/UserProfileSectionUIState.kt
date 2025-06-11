package com.example.mygains.userinfo.ui.states

import com.example.mygains.userinfo.data.models.UserDataModel

sealed class UserProfileSectionUIState {

     object Loading : UserProfileSectionUIState()
     data class Succes(val userData: UserDataModel): UserProfileSectionUIState()
     data class Error(val error :String): UserProfileSectionUIState()
}