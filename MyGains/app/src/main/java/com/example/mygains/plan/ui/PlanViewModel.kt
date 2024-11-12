package com.example.mygains.plan.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mygains.exercisesplan.data.RoutineDayData
import com.example.mygains.plan.domain.PlanUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanViewModel @Inject constructor(var planUseCase: PlanUseCase):ViewModel(){



    private var  _routineDayList = MutableLiveData<MutableList<RoutineDayData>>()
    var _routineDayListLife: LiveData<MutableList<RoutineDayData>> =_routineDayList

    private var  _isLoading = MutableLiveData<Boolean>()
    var _isLoadingLife: LiveData<Boolean> =_isLoading

    fun getAllExcercisesForDay(date:String){
        _isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                try {
                    val result= planUseCase.getPlanForTheDay(date)
                    _routineDayList.postValue(result)
                    Log.i("dataDay", result.toString())
                    _isLoading.postValue(false)
                }catch (er:Exception){
                    _isLoading.postValue(false)
                    Log.e(er.cause.toString(),er.message.toString())
                }
            }catch (er:Exception){
                _isLoading.postValue(false)
                Log.e(er.cause.toString(),er.message.toString())
            }
        }
    }
}