package com.example.mygains.userinfo.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mygains.userinfo.data.models.WeightRegister
import com.example.mygains.userinfo.data.models.UserData
import com.example.mygains.userinfo.domain.usecases.UserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserInfoViewModel @Inject constructor(private var userInfoUseCase: UserInfoUseCase):ViewModel() {

    private val _user= MutableLiveData<UserData>(UserData())
    val userLive : LiveData<UserData> = _user


    private val _listWeights= MutableLiveData<MutableList<WeightRegister>>(mutableListOf())
    val listWeightsLive : LiveData<MutableList<WeightRegister>> = _listWeights

    fun readUserInfo(){
        viewModelScope.launch (Dispatchers.IO){
           _user.postValue(userInfoUseCase.readUserInfo())
        }
    }

    fun saveWeight(weightRegister: WeightRegister){
        viewModelScope.launch(Dispatchers.IO) {
           val saveResult = userInfoUseCase.saveWeight(weightRegister)

            try {
                if (saveResult.weight.isNotEmpty()&& saveResult.date.isNotEmpty()){
                    userInfoUseCase.saveWeightInInfoUser(saveResult)
                    _user.postValue(UserData(weight = saveResult.weight, lastUpdateWeight = saveResult.date))

                    getWeightsByTime("")
                }else{
                    //mostramos el error de guardado
                }
            }catch (e:Exception){
                //mostramos el error
            }
        }
    }

    fun getWeightsByTime(since: String){
        viewModelScope.launch (Dispatchers.IO){
             _listWeights.postValue(userInfoUseCase.getAllWeightsByTime(since = since).toMutableList())
        }
    }

}