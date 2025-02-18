package com.example.mygains.dashboard.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mygains.userinfo.data.models.UserData
import com.example.mygains.userinfo.domain.usecases.UserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor(private var infoUseCase: UserInfoUseCase):ViewModel() {

    private val _UserData= MutableLiveData<UserData>()
    val userDataLive : LiveData<UserData> = _UserData



    fun getUserData(){
         viewModelScope.launch(Dispatchers.IO) {
             _UserData.postValue(infoUseCase.readUserInfo())
         }
    }

    fun getMotivationText():String{
        var motive = mutableListOf<String>("Que no decaiga.","Cada paso cuenta.","¡Sigue adelante, el esfuerzo siempre da frutos!","No importa lo difícil, ¡tú eres más fuerte que el reto!")
        return motive.random()
    }

}