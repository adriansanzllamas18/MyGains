package com.example.mygains.dashboard.ui

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mygains.createroutineprocess.data.repositoryimpl.strength.CreateRoutineRepositoryImpl
import com.example.mygains.dashboard.data.models.HomeScreenModel
import com.example.mygains.dashboard.domain.usecases.DashBoardUseCase
import com.example.mygains.login.ui.LoginScreen
import com.example.mygains.userinfo.data.models.UserData
import com.example.mygains.userinfo.domain.usecases.UserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor(private var infoUseCase: UserInfoUseCase, private var dashBoardUseCase: DashBoardUseCase):ViewModel() {


    private val _uiState = MutableStateFlow<DashBoardUIState> (DashBoardUIState.Loading)
    val uiState :StateFlow<DashBoardUIState> = _uiState


    init {
        getUserData()
    }

    fun getUserData(){
         viewModelScope.launch(Dispatchers.IO) {
             _uiState.emit(DashBoardUIState.Succes(HomeScreenModel( userData = infoUseCase.readUserInfo()?:UserData())))
         }
    }

    fun getMotivationText():String{
        val motive = mutableListOf<String>("Que no decaiga.","Cada paso cuenta.","¡Sigue adelante, el esfuerzo siempre da frutos!","No importa lo difícil, ¡tú eres más fuerte que el reto!")
        return motive.random()
    }

}